package at.pichlerlehner.studyweb.service;

import at.pichlerlehner.studyweb.domain.Model;
import at.pichlerlehner.studyweb.foundation.Ensurer;
import at.pichlerlehner.studyweb.persistence.AbstractJdbcRepo;
import at.pichlerlehner.studyweb.persistence.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

abstract class AbstractService<DOMAIN extends Model<DOMAIN,Long>> {
    protected Connection connection;
    protected AbstractJdbcRepo<DOMAIN> repository;
    final Logger logger = LoggerFactory.getLogger(getClass());

    AbstractService() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/studywebdb?user=root&useSSL=false");
            connection.setAutoCommit(false);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw ServiceException.forException(e);
        }
    }

    public long saveEntity(DOMAIN entity) {
        entity = Ensurer.ensureNotNull(entity);
        long result;
        try {
            result = repository.save(connection, entity);
        } catch (PersistenceException e) {
            e.printStackTrace();
            logger.error("An error occurred while saving entity");
            throw ServiceException.forPersistenceException(e);
        }
        return result;
    }

    public void saveAll(List<DOMAIN> domains) {
        for (DOMAIN entity : domains) {
            this.saveEntity(entity);
        }
    }

    public long createEntity(DOMAIN entity) {
        entity = Ensurer.ensureNotNull(entity);
        long result;
        try {
            result = repository.insert(connection, entity);
        } catch (PersistenceException e) {
            e.printStackTrace();
            logger.error("An error occurred while creating entity");
            throw ServiceException.forPersistenceException(e);
        }
        return result;
    }

    public long updateEntity(DOMAIN entity) {
        entity = Ensurer.ensureNotNull(entity);
        long result;
        try {
            result = repository.update(connection, entity);
        } catch (PersistenceException e) {
            e.printStackTrace();
            logger.error("An error occurred while updating entity");
            throw ServiceException.forPersistenceException(e);
        }
        return result;
    }

    public int deleteEntity(long id) {
        id = Ensurer.ensureNotNegative(id);
        int result;
        try {
            result = repository.delete(connection, id);
        } catch (PersistenceException e) {
            e.printStackTrace();
            logger.error("An error occurred while deleting entity");
            throw ServiceException.forPersistenceException(e);
        }
        return result;
    }

    public int deleteEntity(DOMAIN entity) {
        entity = Ensurer.ensureNotNull(entity);
        int result;
        try {
            result = repository.delete(connection, entity);
        } catch (PersistenceException e) {
            e.printStackTrace();
            logger.error("An error occurred while updating entity");
            throw ServiceException.forPersistenceException(e);
        }
        return result;
    }

    public void deleteAll(List<DOMAIN> domainList) {
        for (DOMAIN domain : domainList) {
            deleteEntity(domain);
        }
    }

    public Optional<DOMAIN> findEntityById(long id) {
        Optional<DOMAIN> entity;
        try {
            entity = repository.findById(connection, id);
        } catch (PersistenceException e) {
            e.printStackTrace();
            logger.error("An error occurred while finding entity");
            throw ServiceException.forPersistenceException(e);
        }
        return entity;
    }

    public List<DOMAIN> findAll() {
        List<DOMAIN> domainList;
        try {
            domainList = repository.findAll(connection);
        } catch (PersistenceException e) {
            e.printStackTrace();
            logger.error("An error occurred while finding every entity");
            throw ServiceException.forPersistenceException(e);
        }
        return domainList;
    }


}
