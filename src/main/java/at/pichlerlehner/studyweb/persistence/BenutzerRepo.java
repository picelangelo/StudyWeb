package at.pichlerlehner.studyweb.persistence;

import at.pichlerlehner.studyweb.domain.Benutzer;
import at.pichlerlehner.studyweb.domain.Berechtigung;
import at.pichlerlehner.studyweb.domain.Frage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BenutzerRepo extends AbstractJdbcRepo<Benutzer> {

    //Spaltennamen
    private String table_name = "User";
    private String b_email = "Email";
    private String b_password = "Password";
    private String b_vorname = "Vorname";
    private String b_nachname = "Nachname";

    private PreparedStatement deleteByIdStatement;



    @Override
    protected long insert(Connection con, Benutzer entity) throws PersistenceException {
        String query = String.format("INSERT INTO %s(%s,%s,%s,%s,%s) VALUES (?,?,?,?,?)", table_name, vers, b_email,
                b_password, b_vorname, b_nachname);
        Long version = entity.getVersion();
        String mail = entity.getEmail();
        String pass = entity.getPassword();
        String vorn = entity.getVorname();
        String nachn = entity.getNachname();

        try {
            PreparedStatement preparedStatement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, version);
            preparedStatement.setString(2, mail);
            preparedStatement.setString(3, pass);
            preparedStatement.setString(4, vorn);
            preparedStatement.setString(5, nachn);
            int affectedRow = preparedStatement.executeUpdate();
            if (affectedRow == 0) {
                String message = "creating user failed, no rows affected.";
                logger.error(message);
                throw new PersistenceException(message);
            }
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet == null) {
                String message = "creating user failed, couldn't generate primary key.";
                logger.error(message);
                throw new PersistenceException(message);
            }
            Long generatedKey = null;
            if (resultSet.next()) {
                generatedKey = resultSet.getLong(1);
                logger.info("primary key was generated with {}", generatedKey);
            }
            if (generatedKey == null) {
                String message = "creating user failed, couldn't generate primary key.";
                logger.error(message);
                throw new PersistenceException(message);
            }
            con.commit();
            return generatedKey;
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("inserting user failed.");
            throw PersistenceException.forSqlException(e);
        }
    }

    @Override
    protected long update(Connection con, Benutzer entity) throws PersistenceException {
        String query = String.format("UPDATE %s SET %s=?, %s=?, %s=?, %s=?, %s=?", table_name, vers, b_email,
                b_password, b_vorname, b_nachname);


        PreparedStatement preparedStatement = null;
        try {
            //Optimistic Locking
            long version_db = getVersion(con, entity.getPrimaryKey());
            if (version_db != entity.getVersion()) {
                throw new PersistenceException("user has recently been updated");
            } else {
                entity.setVersion(entity.getVersion() + 1);
            }

            preparedStatement = con.prepareStatement(query);
            preparedStatement.setLong(1, entity.getVersion());
            preparedStatement.setString(2, entity.getEmail());
            preparedStatement.setString(3, entity.getPassword());
            preparedStatement.setString(4, entity.getVorname());
            preparedStatement.setString(5, entity.getNachname());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new PersistenceException("updating failed, no rows affected");
            }

            con.commit();
            logger.info("successfully updated user with primary key {}", entity.getPrimaryKey());
            return entity.getPrimaryKey();

        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("updating user failed.");
            throw PersistenceException.forSqlException(e);
        }
    }


    @Override
    protected List<Benutzer> parseResultSet(Connection con, ResultSet resultSet) throws PersistenceException {
        List<Benutzer> benutzerList = new ArrayList<>();
        FragebogenRepo fragebogenRepo = new FragebogenRepo();
        BerechtigungRepo berechtigungRepo = new BerechtigungRepo();
        try {
            while (resultSet.next()) {
                Benutzer benutzer = new Benutzer();
                long key = resultSet.getLong(primary_key);
                long ver = resultSet.getLong(vers);
                String ema = resultSet.getString(b_email);
                String pas = resultSet.getString(b_password);
                String vor = resultSet.getString(b_vorname);
                String nac = resultSet.getString(b_nachname);

                benutzer.setPrimaryKey(key);
                benutzer.setVersion(ver);
                benutzer.setEmail(ema);
                benutzer.setPassword(pas);
                benutzer.setVorname(vor);
                benutzer.setNachname(nac);
                benutzer.setFrageboegenErstellt(fragebogenRepo.getFragebogenByCreator(con, key));
                benutzer.setBerechtigungsList(berechtigungRepo.getBerechtigungByUser(con, key));
                benutzerList.add(benutzer);
            }

            return benutzerList;
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("parsing user failed");
            throw PersistenceException.forSqlException(e);
        }
    }

    @Override
    protected void storeDeleteByIdStmt(PreparedStatement deleteByIdStmt) {
        this.deleteByIdStatement = deleteByIdStmt;
    }

    @Override
    protected PreparedStatement getDeleteByIdStmt() {
        return deleteByIdStatement;
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
