package at.pichlerlehner.studyweb.persistence;
import at.pichlerlehner.studyweb.domain.Model;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public interface JdbcRepository<DOMAIN extends Model<DOMAIN, PRIMARY_KEY>, PRIMARY_KEY extends Number> {
    long save(Connection con, DOMAIN entity) throws PersistenceException;

    int delete(Connection con, PRIMARY_KEY id) throws PersistenceException;

    default int delete(Connection con, DOMAIN entity) throws PersistenceException {
        return (entity.isNew()) ? 1 : delete(con, entity.getPrimaryKey());
    };

    Optional<DOMAIN> findById(Connection con, PRIMARY_KEY id) throws
            PersistenceException;

    List<DOMAIN> findAll(Connection con) throws PersistenceException;
}
