package at.pichlerlehner.studyweb.service;

import at.pichlerlehner.studyweb.domain.Benutzer;
import at.pichlerlehner.studyweb.foundation.Ensurer;
import at.pichlerlehner.studyweb.persistence.BenutzerRepo;
import at.pichlerlehner.studyweb.persistence.PersistenceException;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class BenutzerService extends AbstractService<Benutzer> {
    public BenutzerService() {
        this.repository = new BenutzerRepo();
    }

    public Optional<Benutzer> authorize(String email, String passwordSha256) {
        email = Ensurer.ensureNotBlank(email);
        passwordSha256 = Ensurer.ensureNotBlank(passwordSha256);
        Optional<Benutzer> user;
        try {
            user = ((BenutzerRepo)repository).findUserByEmailAndHashedPassword(connection, email, passwordSha256);
        } catch (PersistenceException e) {
            e.printStackTrace();
            logger.error("An error occurred while finding user");
            throw ServiceException.forPersistenceException(e);
        }
        return user;
    }

    public List<Benutzer> findByEmail() {
        return null;
    }


}
