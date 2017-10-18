package at.pichlerlehner.studyweb.persistence;

import at.pichlerlehner.studyweb.domain.Frage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class FrageModel extends AbstractJdbcRepo<Frage> {
    @Override
    protected List<Frage> parseResultSet(Connection con, ResultSet resultSet) throws PersistenceException {
        return null;
    }

    @Override
    protected long insert(Connection con, Frage entity) throws PersistenceException {
        return 0;
    }

    @Override
    protected long update(Connection con, Frage entity) throws PersistenceException {
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
