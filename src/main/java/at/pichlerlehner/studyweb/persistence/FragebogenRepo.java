package at.pichlerlehner.studyweb.persistence;

import at.pichlerlehner.studyweb.domain.Benutzer;
import at.pichlerlehner.studyweb.domain.Berechtigung;
import at.pichlerlehner.studyweb.domain.Fragebogen;
import at.pichlerlehner.studyweb.foundation.Ensurer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FragebogenRepo extends AbstractJdbcRepo<Fragebogen> {

    //Namen
    private String table_name = "Fragebogen";
    private String fb_creator = "User_Id";
    private String fb_bezeichnung = "Bezeichnung";

    public FragebogenRepo() {}

    @Override
    public long insert(Connection con, Fragebogen entity) throws PersistenceException {
        String query = String.format("INSERT INTO %s(%s,%s,%s) VALUES(?,?,?)", table_name, vers, fb_bezeichnung, fb_creator);
        Long version = entity.getVersion();
        String bezeichnung = entity.getBezeichnung();
        Benutzer creator = entity.getErsteller();
        BenutzerRepo benutzerRepo = new BenutzerRepo();
        try {
            PreparedStatement preparedStatement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, version);
            preparedStatement.setString(2,Ensurer.ensureNotBlank(bezeichnung));
            if (creator.isNew()) {
                long pk = benutzerRepo.insert(con, creator);
                creator.setPrimaryKey(pk);
            }
            preparedStatement.setLong(3, creator.getPrimaryKey());
            int affectedRow = preparedStatement.executeUpdate();
            if (affectedRow == 0) {
                String message = "creating Fragebogen failed, no rows affected.";
                logger.error(message);
                throw new PersistenceException(message);
            }
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet == null) {
                String message = "creating Fragebogen failed, couldn't generate primary key.";
                logger.error(message);
                throw new PersistenceException(message);
            }
            Long generatedKey = null;
            if (resultSet.next()) {
                generatedKey = resultSet.getLong(1);
                logger.info("primary key was generated with {}", generatedKey);
            }
            if (generatedKey == null) {
                String message = "creating Fragebogen failed, couldn't generate primary key.";
                logger.error(message);
                throw new PersistenceException(message);
            }
            con.commit();
            return generatedKey;
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("couldn't insert Fragebogen");
            throw PersistenceException.forSqlException(e);
        }
    }

    @Override
    public long update(Connection con, Fragebogen entity) throws PersistenceException {
        String query = String.format("UPDATE %s SET %s=?, %s=?, %s=? where %s=?", table_name, vers, fb_bezeichnung, fb_creator, primary_key);
        BenutzerRepo benutzerRepo = new BenutzerRepo();
        try {
            //Optimistic Locking
            long version_db = getVersion(con, entity.getPrimaryKey());
            if (version_db != entity.getVersion()) {
                logger.error("Version conflict");
                throw new PersistenceException("Fragebogen has recently been updated");
            } else {
                entity.setVersion(entity.getVersion() + 1);
            }
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setLong(1, entity.getVersion());
            preparedStatement.setString(2, entity.getBezeichnung());
            if (entity.getErsteller().isNew()) {
                long pk = benutzerRepo.insert(con, entity.getErsteller());
                entity.getErsteller().setPrimaryKey(pk);
            }
            preparedStatement.setLong(3, entity.getErsteller().getPrimaryKey());
            preparedStatement.setLong(4, entity.getPrimaryKey());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new PersistenceException("updating failed, no rows affected");
            }
            con.commit();
            logger.info("successfully updated Fragebogen with primary key {}", entity.getPrimaryKey());
            return entity.getPrimaryKey();

        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("updating Fragebogen failed.");
            throw PersistenceException.forSqlException(e);
        }
    }


    public List<Fragebogen> getFragebogenByCreator(Connection con, Long userId) throws PersistenceException{
        return getElementByLongColumn(con, fb_creator, userId);
    }

    @Override
    protected List<Fragebogen> parseResultSet(Connection con, ResultSet resultSet) throws PersistenceException {
        List<Fragebogen> fragebogenList = new ArrayList<>();
        BenutzerRepo benutzerRepo = new BenutzerRepo();
        try {
            while (resultSet.next()) {
                Fragebogen fragebogen = new Fragebogen();
                long key = resultSet.getLong(primary_key);
                long ver = resultSet.getLong(vers);
                String bezeichnung = resultSet.getString(fb_bezeichnung);
                Optional<Benutzer> erstellerO = benutzerRepo.findById(con, resultSet.getLong(fb_creator));
                fragebogen.setPrimaryKey(key);
                fragebogen.setVersion(ver);
                fragebogen.setBezeichnung(bezeichnung);
                erstellerO.ifPresent(fragebogen::setErsteller);
                fragebogenList.add(fragebogen);
            }

            return fragebogenList;
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("parsing Fragebogen failed");
            throw PersistenceException.forSqlException(e);
        }
    }

    public List<Fragebogen> getByUserAccess(Connection con, Long benutzerId) throws PersistenceException {
        BerechtigungRepo berechtigungRepo = new BerechtigungRepo();
        List<Berechtigung> berechtigungList = berechtigungRepo.getBerechtigungByUser(con, benutzerId);
        List<Fragebogen> fragebogenList = new ArrayList<>();
        for (Berechtigung berechtigung : berechtigungList) {
            fragebogenList.add(berechtigung.getFragebogen());
        }
        return fragebogenList;
    }

    public List<Fragebogen> getByUserWriteAccess(Connection con, Long benutzerId) throws PersistenceException {
        BerechtigungRepo berechtigungRepo = new BerechtigungRepo();
        List<Berechtigung> berechtigungList = berechtigungRepo.getBerechtigungByUser(con, benutzerId).stream().filter(Berechtigung::isDarfBearbeiten).collect(Collectors.toList());
        List<Fragebogen> fragebogenList = new ArrayList<>();
        for (Berechtigung berechtigung : berechtigungList) {
            fragebogenList.add(berechtigung.getFragebogen());
        }
        return fragebogenList;
    }

    @Override
    protected String getTableName() {
        return table_name;
    }

    @Override
    protected String getPrimaryKeyColumnName() {
        return primary_key;
    }
}
