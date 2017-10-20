package at.pichlerlehner.studyweb.persistence;

import at.pichlerlehner.studyweb.domain.Antwort;

import java.sql.*;
import java.util.List;

public class AntwortRepo extends AbstractJdbcRepo<Antwort> {


    //Namen
    private String table_name = "Antwort";
    private String a_frage = "Frage_Id";
    private String a_antwort = "Antwort";
    private String a_correct = "IsCorrect";

    @Override
    protected List<Antwort> parseResultSet(Connection con, ResultSet resultSet) throws PersistenceException {
        return null;
    }

    @Override
    protected long insert(Connection con, Antwort entity) throws PersistenceException {
        String query = String.format("INSERT INTO %s(%s,%s,%s,%s) VALUES(?,?,?,?)", table_name, vers, a_frage, a_antwort, a_correct);
        Long version = entity.getVersion();
        Long frage = entity.getFrage().getPrimaryKey();
        String antwort = entity.getAntwort();
        boolean correct = entity.isCorrect();
        try {
            PreparedStatement preparedStatement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1,version);
            preparedStatement.setLong(2, frage);
            preparedStatement.setString(3, antwort);
            preparedStatement.setBoolean(4, correct);

            int affectedRow = preparedStatement.executeUpdate();
            if (affectedRow == 0) {
                String message = "creating Antwort failed, no rows affected.";
                logger.error(message);
                throw new PersistenceException(message);
            }
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet == null) {
                String message = "creating Antwort failed, couldn't generate primary key.";
                logger.error(message);
                throw new PersistenceException(message);
            }
            Long generatedKey = null;
            if (resultSet.next()) {
                generatedKey = resultSet.getLong(1);
                logger.info("primary key was generated with {}", generatedKey);
            }
            if (generatedKey == null) {
                String message = "creating Antwort failed, couldn't generate primary key.";
                logger.error(message);
                throw new PersistenceException(message);
            }
            con.commit();
            return generatedKey;
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("couldn't insert Antwort");
            throw PersistenceException.forSqlException(e);
        }
    }

    @Override
    protected long update(Connection con, Antwort entity) throws PersistenceException {
        String query = String.format("UPDATE %s SET %s=?, %s=?, %s=?, %s=? WHERE %s=?", table_name, vers, a_frage, a_antwort, a_correct, primary_key);
        try {
            long version_db = getVersion(con, entity.getPrimaryKey());
            if (version_db != entity.getVersion()) {
                logger.error("Version conflict");
                throw new PersistenceException("antwort has already been updated");
            } else {
                entity.setVersion(entity.getVersion() + 1);
            }
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setLong(1, entity.getVersion());
            preparedStatement.setLong(2, entity.getFrage().getPrimaryKey());
            preparedStatement.setString(3, entity.getAntwort());
            preparedStatement.setBoolean(4, entity.isCorrect());
            preparedStatement.setLong(5, entity.getPrimaryKey());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new PersistenceException("updating failed, no rows affected");
            }
            con.commit();
            logger.info("successfully updated Antwort with primary key {}", entity.getPrimaryKey());
            return entity.getPrimaryKey();

        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("updating Fragebogen failed.");
            throw PersistenceException.forSqlException(e);
        }
    }

    @Override
    protected String getTableName() {
        return this.table_name;
    }

    @Override
    protected String getPrimaryKeyColumnName() {
        return primary_key;
    }
}
