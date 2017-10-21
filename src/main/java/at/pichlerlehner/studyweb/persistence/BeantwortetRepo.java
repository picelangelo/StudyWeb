package at.pichlerlehner.studyweb.persistence;

import at.pichlerlehner.studyweb.domain.Beantwortet;
import at.pichlerlehner.studyweb.domain.Benutzer;
import at.pichlerlehner.studyweb.domain.Frage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BeantwortetRepo extends AbstractJdbcRepo<Beantwortet> {

    //Namen
    private String table_name = "Beantwortet";
    private String ba_frage = "Frage_Id";
    private String ba_user = "User_Id";
    private String ba_richtig = "Anzahl_richtig";
    private String ba_falsch = "Anzahl_falsch";


    @Override
    protected long insert(Connection con, Beantwortet entity) throws PersistenceException {
        String query = String.format("INSERT INTO %s(%s,%s,%s,%s,%s ) VALUES(?,?,?,?,?)", table_name, vers, ba_frage,
                ba_user, ba_richtig, ba_falsch);
        Long version = entity.getVersion();
        Frage frage = entity.getFrage();
        Benutzer user = entity.getBenutzer();
        int anzCorr = entity.getAnzahlRichtig();
        int anzWrong = entity.getAnzahlFalsch();
        FrageRepo frageRepo = new FrageRepo();
        BenutzerRepo benutzerRepo = new BenutzerRepo();
        try {
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setLong(1, version);
            if (frage.isNew()) {
                long pk = frageRepo.insert(con, frage);
                frage.setPrimaryKey(pk);
            }
            preparedStatement.setLong(2, frage.getPrimaryKey());
            if (user.isNew()) {
                long pk = benutzerRepo.insert(con, user);
                user.setPrimaryKey(pk);
            }
            preparedStatement.setLong(3, user.getPrimaryKey());
            preparedStatement.setInt(4, anzCorr);
            preparedStatement.setInt(5, anzWrong);

            int affectedRow = preparedStatement.executeUpdate();
            if (affectedRow == 0) {
                String message = "creating Beantwortet failed, no rows affected.";
                logger.error(message);
                throw new PersistenceException(message);
            }
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet == null) {
                String message = "creating Beantwortet failed, couldn't generate primary key.";
                logger.error(message);
                throw new PersistenceException(message);
            }
            Long generatedKey = null;
            if (resultSet.next()) {
                generatedKey = resultSet.getLong(1);
                logger.info("primary key was generated with {}", generatedKey);
            }
            if (generatedKey == null) {
                String message = "creating Beantwortet failed, couldn't generate primary key.";
                logger.error(message);
                throw new PersistenceException(message);
            }
            con.commit();
            return generatedKey;
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("couldn't insert beantwortet");
            throw PersistenceException.forSqlException(e);
        }
    }

    @Override
    protected long update(Connection con, Beantwortet entity) throws PersistenceException {
        String query = String.format("UPDATE %s SET %s=?,%s=?,%s=?,%s=?,%s=? WHERE %s=?", table_name,vers, ba_frage,
                ba_user, ba_richtig, ba_falsch, primary_key);
        FrageRepo frageRepo = new FrageRepo();
        BenutzerRepo benutzerRepo = new BenutzerRepo();
        try {
            long version_db = getVersion(con, entity.getPrimaryKey());
            if (version_db != entity.getVersion()) {
                logger.error("Version conflict");
                throw new PersistenceException("user has recently been updated");
            } else {
                entity.setVersion(entity.getVersion() + 1);
            }
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setLong(1, entity.getVersion());
            if (entity.getFrage().isNew()) {
                long pk = frageRepo.insert(con, entity.getFrage());
                entity.getFrage().setPrimaryKey(pk);
            }
            preparedStatement.setLong(2, entity.getFrage().getPrimaryKey());
            if (entity.getBenutzer().isNew()) {
                long pk = benutzerRepo.insert(con, entity.getBenutzer());
                entity.getBenutzer().setPrimaryKey(pk);
            }
            preparedStatement.setLong(3, entity.getBenutzer().getPrimaryKey());
            preparedStatement.setInt(4, entity.getAnzahlRichtig());
            preparedStatement.setInt(5, entity.getAnzahlFalsch());
            preparedStatement.setLong(6, entity.getPrimaryKey());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new PersistenceException("updating failed, no rows affected");
            }
            con.commit();
            logger.info("successfully updated 'beantwortet' with primary key {}", entity.getPrimaryKey());
            return entity.getPrimaryKey();
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("updating 'beantwortet' failed.");
            throw PersistenceException.forSqlException(e);
        }
    }


    @Override
    protected List<Beantwortet> parseResultSet(Connection con, ResultSet resultSet) throws PersistenceException {
        List<Beantwortet> beantwortetList = new ArrayList<>();
        FrageRepo frageRepo = new FrageRepo();
        BenutzerRepo benutzerRepo = new BenutzerRepo();
        try {
            while (resultSet.next()) {
                Beantwortet beantwortet = new Beantwortet();
                Long key = resultSet.getLong(primary_key);
                Long ver = resultSet.getLong(vers);
                Optional<Frage> frage = frageRepo.findById(con, resultSet.getLong(ba_frage));
                Optional<Benutzer> user = benutzerRepo.findById(con, resultSet.getLong(ba_user));
                int anzRichtig = resultSet.getInt(ba_richtig);
                int anzFalsch = resultSet.getInt(ba_falsch);

                beantwortet.setPrimaryKey(key);
                beantwortet.setVersion(ver);
                frage.ifPresent(beantwortet::setFrage);
                user.ifPresent(beantwortet::setBenutzer);
                beantwortet.setAnzahlRichtig(anzRichtig);
                beantwortet.setAnzahlFalsch(anzFalsch);

                beantwortetList.add(beantwortet);
            }
            return beantwortetList;
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("parsing beantwortet failed");
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
}
