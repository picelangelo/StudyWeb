package at.pichlerlehner.studyweb.persistence;

import at.pichlerlehner.studyweb.domain.Antwort;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class AntwortRepo extends AbstractJdbcRepo<Antwort> {
    //Namen
    private String table_name = "Antwort";

    @Override
    protected List<Antwort> parseResultSet(Connection con, ResultSet resultSet) throws PersistenceException {
        return null;
    }

    @Override
    protected long insert(Connection con, Antwort entity) throws PersistenceException {
        return 0;
    }

    @Override
    protected long update(Connection con, Antwort entity) throws PersistenceException {
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
