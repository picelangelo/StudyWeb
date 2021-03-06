package at.pichlerlehner.studyweb.persistence;

import at.pichlerlehner.studyweb.domain.Benutzer;
import at.pichlerlehner.studyweb.domain.Berechtigung;
import at.pichlerlehner.studyweb.domain.Fragebogen;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BerechtigungRepo extends AbstractJdbcRepo<Berechtigung> {
    //Namen
    private String table_name = "Berechtigt";
    private String b_user = "User_Id";
    private String b_fragebogen = "Fragebogen_Id";
    private String b_rechte = "Darf_bearbeiten";

    public BerechtigungRepo() {}

    @Override
    public long insert(Connection con, Berechtigung entity) throws PersistenceException {
        String query = String.format("INSERT INTO %s(%s,%s,%s,%s) VALUES(?,?,?,?)", table_name, vers,b_user, b_fragebogen, b_rechte);
        Long version = entity.getVersion();
        Benutzer user = entity.getBenutzer();
        Fragebogen fragebogen = entity.getFragebogen();
        boolean bearbeiten = entity.isDarfBearbeiten();
        BenutzerRepo benutzerRepo = new BenutzerRepo();
        FragebogenRepo fragebogenRepo = new FragebogenRepo();
        try {
            PreparedStatement preparedStatement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, version);
            if (user.isNew()) {
                long pk = benutzerRepo.insert(con, user);
                user.setPrimaryKey(pk);
            }
            preparedStatement.setLong(2, user.getPrimaryKey());
            if (fragebogen.isNew()) {
                long pk = fragebogenRepo.insert(con, fragebogen);
                fragebogen.setPrimaryKey(pk);
            }
            preparedStatement.setLong(3, fragebogen.getPrimaryKey());
            preparedStatement.setBoolean(4, bearbeiten);

            int affectedRow = preparedStatement.executeUpdate();
            if (affectedRow == 0) {
                String message = "creating Berechtigung failed, no rows affected.";
                logger.error(message);
                throw new PersistenceException(message);
            }
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet == null) {
                String message = "creating Berechtigung failed, couldn't generate primary key.";
                logger.error(message);
                throw new PersistenceException(message);
            }
            Long generatedKey = null;
            if (resultSet.next()) {
                generatedKey = resultSet.getLong(1);
                logger.info("primary key was generated with {}", generatedKey);
            }
            if (generatedKey == null) {
                String message = "creating Berechtigung failed, couldn't generate primary key.";
                logger.error(message);
                throw new PersistenceException(message);
            }
            con.commit();
            return generatedKey;

        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("couldn't insert Berechtigung");
            throw PersistenceException.forSqlException(e);
        }
    }

    @Override
    public long update(Connection con, Berechtigung entity) throws PersistenceException {
        String query = String.format("UPDATE %s SET %s=?,%s=?,%s=?,%s=? WHERE %s=?", table_name, vers, b_fragebogen, b_user, b_rechte, primary_key);
        BenutzerRepo benutzerRepo = new BenutzerRepo();
        FragebogenRepo fragebogenRepo = new FragebogenRepo();
        try {
            long version_db = getVersion(con, entity.getPrimaryKey());
            if (version_db != entity.getVersion()) {
                logger.error("Version conflict");
                throw new PersistenceException("Berechtigung has recently been updated");
            } else {
                entity.setVersion(entity.getVersion() + 1);
            }

            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setLong(1, entity.getVersion());
            if (entity.getFragebogen().isNew()) {
                long pk = fragebogenRepo.insert(con, entity.getFragebogen());
                entity.getFragebogen().setPrimaryKey(pk);
            }
            preparedStatement.setLong(2, entity.getFragebogen().getPrimaryKey());
            if (entity.getBenutzer().isNew()) {
                long pk = benutzerRepo.insert(con, entity.getBenutzer());
                entity.getBenutzer().setPrimaryKey(pk);
            }
            preparedStatement.setLong(3, entity.getBenutzer().getPrimaryKey());
            preparedStatement.setBoolean(4, entity.isDarfBearbeiten());
            preparedStatement.setLong(5, entity.getPrimaryKey());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new PersistenceException("updating failed, no rows affected");
            }
            con.commit();
            logger.info("successfully updated Berechtigung with primary key {}", entity.getPrimaryKey());
            return entity.getPrimaryKey();
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("updating Berechtigung failed.");
            throw PersistenceException.forSqlException(e);
        }
    }

    @Override
    protected String getTableName() {
        return table_name;
    }

    @Override
    protected String getPrimaryKeyColumnName() {
        return primary_key;
    }

    @Override
    protected List<Berechtigung> parseResultSet(Connection con, ResultSet resultSet) throws PersistenceException {
        List<Berechtigung> berechtigungList = new ArrayList<>();

        BenutzerRepo benutzerRepo = new BenutzerRepo();
        FragebogenRepo fragebogenRepo = new FragebogenRepo();

        try {
            while (resultSet.next()) {
                Berechtigung berechtigung = new Berechtigung();
                long key = resultSet.getLong(primary_key);
                long ver = resultSet.getLong(vers);
                Optional<Benutzer> benutzer = benutzerRepo.findById(con, resultSet.getLong(b_user));
                Optional<Fragebogen> fragebogen = fragebogenRepo.findById(con, resultSet.getLong(b_fragebogen));
                boolean bearbeiten = resultSet.getBoolean(b_rechte);

                berechtigung.setPrimaryKey(key);
                berechtigung.setVersion(ver);
                benutzer.ifPresent(berechtigung::setBenutzer);
                fragebogen.ifPresent(berechtigung::setFragebogen);
                berechtigung.setDarfBearbeiten(bearbeiten);

                berechtigungList.add(berechtigung);
            }
            return berechtigungList;
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("parsing Berechtigung failed");
            throw PersistenceException.forSqlException(e);
        }
    }

    public List<Berechtigung> getBerechtigungByUser(Connection con, Long userId) throws PersistenceException{
        return getElementByLongColumn(con, b_user, userId);
    }

    public List<Benutzer> getFrageboegenByBenutzer(Connection con, Long quizId) throws PersistenceException {
        List<Benutzer> benutzerList = new ArrayList<>();
        getElementByLongColumn(con, b_fragebogen, quizId).forEach(x -> benutzerList.add(x.getBenutzer()));
        return benutzerList;
    }

    public List<Berechtigung> getByFrageboegenAndBenutzer(Connection con, Long quizId, Long userId) throws PersistenceException {
        List<Berechtigung> berechtigungList;
        berechtigungList = getElementByLongColumn(con, b_fragebogen, quizId).stream().filter(x -> x.getBenutzer().getPrimaryKey().equals(userId)).collect(Collectors.toList());
        return berechtigungList;
    }

}
