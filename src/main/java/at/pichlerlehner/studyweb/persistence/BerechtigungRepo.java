package at.pichlerlehner.studyweb.persistence;

import at.pichlerlehner.studyweb.domain.Berechtigung;
import at.pichlerlehner.studyweb.domain.Fragebogen;
import com.sun.jndi.ldap.Ber;

import java.sql.*;
import java.util.List;

public class BerechtigungRepo extends AbstractJdbcRepo<Berechtigung> {
    //Namen
    private String table_name = "Berechtigt";
    private String b_user = "User_Id";
    private String b_fragebogen = "Fragebogen_Id";
    private String b_rechte = "Darf_bearbeiten";

    @Override
    protected long insert(Connection con, Berechtigung entity) throws PersistenceException {
        return 0;
    }

    @Override
    protected long update(Connection con, Berechtigung entity) throws PersistenceException {
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

    @Override
    protected List<Berechtigung> parseResultSet(Connection con, ResultSet resultSet) throws PersistenceException {
        return null;
    }

    public List<Berechtigung> getBerechtigungByUser(Connection con, Long userId) throws PersistenceException{
        return getElementByLongColumn(con, userId, b_user);
    }
}
