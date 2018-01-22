package at.pichlerlehner.studyweb.service;

import at.pichlerlehner.studyweb.domain.Benutzer;
import at.pichlerlehner.studyweb.domain.Berechtigung;
import at.pichlerlehner.studyweb.domain.Fragebogen;
import at.pichlerlehner.studyweb.persistence.BerechtigungRepo;
import at.pichlerlehner.studyweb.persistence.PersistenceException;

import java.util.List;

public class BerechtigungService extends AbstractService<Berechtigung> {
    public BerechtigungService() {
        this.repository = new BerechtigungRepo();
    }


    public List<Benutzer> findUserAccessToQuiz(Fragebogen fragebogen) {
        try {
            return ((BerechtigungRepo)repository).getFrageboegenByBenutzer(connection, fragebogen.getPrimaryKey());
        } catch (PersistenceException e) {
            e.printStackTrace();
            logger.error("An error occurred while finding quizzes");
            throw ServiceException.forPersistenceException(e);
        }
    }
    public List<Berechtigung> findByUserAndQuiz(Fragebogen fragebogen, Benutzer benutzer) {
        try {
            return ((BerechtigungRepo)repository).getByFrageboegenAndBenutzer(connection, fragebogen.getPrimaryKey(), benutzer.getPrimaryKey());
        } catch (PersistenceException e) {
            e.printStackTrace();
            logger.error("An error occurred while finding quizzes");
            throw ServiceException.forPersistenceException(e);
        }
    }
}
