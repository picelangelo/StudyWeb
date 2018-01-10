package at.pichlerlehner.studyweb.service;

import at.pichlerlehner.studyweb.domain.Benutzer;
import at.pichlerlehner.studyweb.domain.Fragebogen;
import at.pichlerlehner.studyweb.persistence.FragebogenRepo;
import at.pichlerlehner.studyweb.persistence.PersistenceException;
import java.util.List;

public class FragebogenService extends AbstractService<Fragebogen> {

    public FragebogenService() {
        repository = new FragebogenRepo();
    }

    public List<Fragebogen> findQuizByUserAccess(Benutzer benutzer) {

        try {
            List<Fragebogen> fragebogenList = ((FragebogenRepo)repository).getFragebogenByCreator(connection, benutzer.getPrimaryKey());
            fragebogenList.addAll(((FragebogenRepo)repository).getByUserAccess(connection, benutzer.getPrimaryKey()));
            return fragebogenList;
        } catch (PersistenceException e) {
            e.printStackTrace();
            logger.error("An error occurred while finding quizzes");
            throw ServiceException.forPersistenceException(e);
        }
    }

}
