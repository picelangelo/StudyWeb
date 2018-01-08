package at.pichlerlehner.studyweb.service;

import at.pichlerlehner.studyweb.domain.Benutzer;
import at.pichlerlehner.studyweb.domain.Fragebogen;
import at.pichlerlehner.studyweb.domain.Rechte;
import at.pichlerlehner.studyweb.persistence.BerechtigungRepo;
import at.pichlerlehner.studyweb.persistence.FragebogenRepo;
import at.pichlerlehner.studyweb.persistence.PersistenceException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class FragebogenService extends AbstractService<Fragebogen> {
    private FragebogenRepo repository;

    public FragebogenService() {
        this.repository = new FragebogenRepo();
    }

    public List<Fragebogen> findQuizByUserAccess(Benutzer benutzer) {
        BerechtigungService berechtigungService = new BerechtigungService();

        try {
            List<Fragebogen> fragebogenList = repository.getFragebogenByCreator(connection, benutzer.getPrimaryKey());
            fragebogenList.addAll(repository.getByUserAccess(connection, benutzer.getPrimaryKey()));



            return fragebogenList;
        } catch (PersistenceException e) {
            e.printStackTrace();
            logger.error("An error occurred while finding quizzes");
            throw ServiceException.forPersistenceException(e);
        }
    }
}
