package at.pichlerlehner.studyweb.service;

import at.pichlerlehner.studyweb.persistence.PersistenceException;

import java.sql.SQLException;

public class ServiceException extends RuntimeException {
    public ServiceException(String s) {
        super(s);
    }

    public ServiceException() {
        super("Service Exception.");
    }

    public static ServiceException forPersistenceException(PersistenceException exception) {
        throw new ServiceException(exception.getMessage());
    }

    public static ServiceException forException(Exception exception) {
        throw new ServiceException(exception.getMessage());
    }


}
