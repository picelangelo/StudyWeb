package at.pichlerlehner.studyweb.persistence;

import java.sql.SQLException;

public class PersistenceException extends Exception {
    public PersistenceException() {
        super("Persistence Exception");
    }

    public PersistenceException(String s) {
        super(s);
    }

    public static PersistenceException forSqlException(SQLException e) throws PersistenceException {
        throw new PersistenceException("persistence sql exception");
    }

    public static PersistenceException forException(Exception e) throws PersistenceException {
        throw new PersistenceException(e.toString());
    }
}
