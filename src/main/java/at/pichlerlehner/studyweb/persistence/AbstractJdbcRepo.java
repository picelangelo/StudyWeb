package at.pichlerlehner.studyweb.persistence;

import at.pichlerlehner.studyweb.domain.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractJdbcRepo<DOMAIN extends Model<DOMAIN, Long>> implements JdbcRepository<DOMAIN, Long>{

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public int save(Connection con, DOMAIN entity) throws PersistenceException {
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
        return 0;
    }

    @Override
    public List<DOMAIN> findAll(Connection con) throws PersistenceException {
        return null;
    }

    public Optional<DOMAIN> findById(Connection con, java.lang.Long id) throws PersistenceException {
        return null;
    }

    public int delete(Connection con, java.lang.Long id) throws PersistenceException {
        PreparedStatement deleteByIdStmt = getDeleteByIdStmt();
        try {

            if (deleteByIdStmt == null) {

                String deleteByIdSQL = String.format("DELETE FROM %s WHERE %s = ?",
                        getTableName(), getPrimaryKeyColumnName());

                logger.debug("crafted deleteByIdStmt: '{}'", deleteByIdSQL);

                deleteByIdStmt = con.prepareStatement(deleteByIdSQL);
                storeDeleteByIdStmt(deleteByIdStmt);
            }

            // null check is obsolete now
            if (id == null) {
                deleteByIdStmt.setNull(1, Types.BIGINT);
            } else {
                deleteByIdStmt.setLong(1, id);
            }

            return deleteByIdStmt.executeUpdate();
        } catch (SQLException sqlEx) {
            throw PersistenceException.forSqlException(sqlEx);
        }
    }


    protected abstract int insert(Connection con, DOMAIN entity) throws PersistenceException;

    protected abstract int update(Connection con, DOMAIN entity) throws PersistenceException;

    protected abstract void storeDeleteByIdStmt(PreparedStatement deleteByIdStmt);

    protected abstract PreparedStatement getDeleteByIdStmt();

    protected abstract String getTableName();

    protected abstract String getPrimaryKeyColumnName();
}
