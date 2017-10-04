package at.pichlerlehner.studyweb.persistence;

import at.pichlerlehner.studyweb.domain.Benutzer;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class BenutzerModel extends AbstractJdbcRepo<Benutzer> {
    @Override
    protected int insert(Connection con, Benutzer entity) throws PersistenceException {
        return 0;
    }

    @Override
    protected int update(Connection con, Benutzer entity) throws PersistenceException {
        return 0;
    }

    @Override
    protected void storeDeleteByIdStmt(PreparedStatement deleteByIdStmt) {

    }

    @Override
    protected PreparedStatement getDeleteByIdStmt() {
        return null;
    }

    @Override
    protected String getTableName() {
        return null;
    }

    @Override
    protected String getPrimaryKeyColumnName() {
        return null;
    }
}
