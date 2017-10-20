package at.pichlerlehner.studyweb.persistence;

import at.pichlerlehner.studyweb.domain.Benutzer;
import org.junit.After;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class AbstractJdbcRepoTest {
    final Benutzer testUser = new Benutzer("test@test.com",
            "03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4",
            "Max", "Mustermann");

    Connection connection;
    protected Statement statement;
    final Logger logger = LoggerFactory.getLogger(getClass());
    final BenutzerRepo benutzerRepo = new BenutzerRepo();
    @Before
    public void setUp() throws PersistenceException {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost/studywebdb?user=root&useSSL=false");
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("couldn't connect to mysql");
            throw PersistenceException.forSqlException(e);
        }

    }

    @After
    public void tearDown() throws PersistenceException {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("couldn't close connection");
            throw PersistenceException.forSqlException(e);
        }
    }

    public abstract void testFindAll() throws PersistenceException;

    public abstract void testInsert() throws PersistenceException;

    public abstract void testUpdate() throws PersistenceException;

    public abstract void testFindById() throws PersistenceException;

    public abstract void testDelete() throws PersistenceException;

}
