package at.pichlerlehner.studyweb.service;

import at.pichlerlehner.studyweb.domain.Antwort;
import at.pichlerlehner.studyweb.domain.Frage;
import at.pichlerlehner.studyweb.domain.Fragebogen;
import at.pichlerlehner.studyweb.persistence.AntwortRepo;
import at.pichlerlehner.studyweb.persistence.FrageRepo;
import at.pichlerlehner.studyweb.persistence.PersistenceException;

import java.util.List;

public class AntwortService extends AbstractService<Antwort> {
    public AntwortService() {
        this.repository = new AntwortRepo();
    }

    public List<Antwort> getAntwortenByFrage(Frage frage) {
        try {
            return ((AntwortRepo)repository).getAntwortenByFrage(connection, frage.getPrimaryKey());
        } catch (PersistenceException e) {
            e.printStackTrace();
            logger.error("An error occurred while finding answers");
            throw ServiceException.forPersistenceException(e);
        }
    }

    public List<Antwort> getAntwortenByFragebogen(Fragebogen fragebogen) {
        try {
            return ((AntwortRepo)repository).getAntwortenByFragebogen(connection, fragebogen.getPrimaryKey());
        } catch (PersistenceException e) {
            e.printStackTrace();
            logger.error("An error occurred while finding answers");
            throw ServiceException.forPersistenceException(e);
        }
    }
}
