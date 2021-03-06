package at.pichlerlehner.studyweb.persistence;

import at.pichlerlehner.studyweb.domain.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public abstract class AbstractJdbcRepo<DOMAIN extends Model<DOMAIN, Long>> implements JdbcRepository<DOMAIN, Long>{


    //Namen
    String primary_key = "Id";
    String vers = "Version";
    final Logger logger = LoggerFactory.getLogger(getClass());

    AbstractJdbcRepo(){}

    @Override
    public long save(Connection con, DOMAIN entity) throws PersistenceException {
        try {
            if (entity.isNew())
                return insert(con, entity);
            else
                return update(con, entity);

        } catch (Exception ex) {
            throw PersistenceException.forException(ex);
        }
    }

    @Override
    public int delete(Connection con, DOMAIN entity) throws PersistenceException {
        return delete(con, entity.getPrimaryKey());
    }

    @Override
    public List<DOMAIN> findAll(Connection con) throws PersistenceException {
        String query = String.format("SELECT * FROM %s", getTableName());
        PreparedStatement preparedStatement;
        List<DOMAIN> result;
        try {
            preparedStatement = con.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            result = parseResultSet(con, resultSet);
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("finding everything failed");
            throw PersistenceException.forSqlException(e);
        }
    }

    public Optional<DOMAIN> findById(Connection con, java.lang.Long id) throws PersistenceException {
        String query = String.format("SELECT * FROM %s WHERE %s=?", getTableName(), getPrimaryKeyColumnName());
        PreparedStatement preparedStatement;
        List<DOMAIN> result;
        try {
            preparedStatement = con.prepareStatement(query);

            if (id == null) {
                preparedStatement.setNull(1, Types.BIGINT);
            } else {
                preparedStatement.setLong(1, id);
            }

            ResultSet resultSet = preparedStatement.executeQuery();
            result = parseResultSet(con, resultSet);
            try {
                return Optional.ofNullable(result.get(0));
            } catch (IndexOutOfBoundsException e) {
                return Optional.empty();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw PersistenceException.forSqlException(e);
        }
    }

    public int delete(Connection con, Long id) throws PersistenceException {
        PreparedStatement preparedStatement;
        try {

            String deleteByIdSQL = String.format("DELETE FROM %s WHERE %s = ?",
                    getTableName(), getPrimaryKeyColumnName());

            logger.debug("crafted preparedStatement: '{}'", deleteByIdSQL);

            preparedStatement = con.prepareStatement(deleteByIdSQL);


            if (id == null) {
                preparedStatement.setNull(1, Types.BIGINT);
            } else {
                preparedStatement.setLong(1, id);
            }

            int l =  preparedStatement.executeUpdate();
            con.commit();
            return l;
        } catch (SQLException sqlEx) {
            throw PersistenceException.forSqlException(sqlEx);
        }
    }

    public long getVersion(Connection con, Long id) throws PersistenceException {
        String query = String.format("SELECT %s FROM %s where %s = ?", vers, getTableName(), getPrimaryKeyColumnName());
        PreparedStatement preparedStatement;
        try {
            preparedStatement = con.prepareStatement(query);
            if (id == null) {
                preparedStatement.setNull(1, Types.BIGINT);
            } else {
                preparedStatement.setLong(1, id);
            }
            ResultSet resultSet = preparedStatement.executeQuery();
            Long version = null;
            while (resultSet.next()) {
                version = resultSet.getLong(vers);
            }
            if (version == null) {
                throw new PersistenceException("updating user failed.");
            } else {
                return version;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("updating user failed.");
            throw PersistenceException.forSqlException(e);
        }
    }

    protected List<DOMAIN> getElementByPreparedStmt(Connection con, PreparedStatement preparedStatement) throws PersistenceException {
        try {
            ResultSet resultSet = preparedStatement.executeQuery();
            return parseResultSet(con, resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("Couldn't return element by preparedStatement");
            throw PersistenceException.forSqlException(e);
        }
    }


    protected List<DOMAIN> getElementByLongColumn(Connection con,  String columnName, Long longValue) throws PersistenceException{
        String query = String.format("SELECT * FROM %s WHERE %s = ?", getTableName(), columnName);
        PreparedStatement preparedStatement;
        try {
            preparedStatement = con.prepareStatement(query);
            if (longValue == null) {
                preparedStatement.setNull(1, Types.BIGINT);
            } else {
                preparedStatement.setLong(1, longValue);
            }
            ResultSet resultSet = preparedStatement.executeQuery();
            return parseResultSet(con, resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error(String.format("Couldn't return element by %s", columnName));
            throw PersistenceException.forSqlException(e);
        }
    }


    protected abstract List<DOMAIN> parseResultSet(Connection con, ResultSet resultSet) throws PersistenceException;

    public abstract long insert(Connection con, DOMAIN entity) throws PersistenceException;

    public abstract long update(Connection con, DOMAIN entity) throws PersistenceException;

    protected abstract String getTableName();

    protected abstract String getPrimaryKeyColumnName();

}
