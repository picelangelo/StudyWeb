package at.pichlerlehner.studyweb.service;

import at.pichlerlehner.studyweb.domain.Frage;
import at.pichlerlehner.studyweb.domain.Fragebogen;
import at.pichlerlehner.studyweb.persistence.FrageRepo;
import at.pichlerlehner.studyweb.persistence.PersistenceException;

import java.util.List;

public class FrageService extends AbstractService<Frage> {

    public FrageService() {
        this.repository = new FrageRepo();
    }

    public List<Frage> getFragenByFragebogen(Fragebogen fragebogen) {
        try {
            return ((FrageRepo)repository).getFragenByFragebogen(connection, fragebogen.getPrimaryKey());
        } catch (PersistenceException e) {
            e.printStackTrace();
            logger.error("An error occurred while finding questions");
            throw ServiceException.forPersistenceException(e);
        }
    }
}
