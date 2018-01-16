package at.pichlerlehner.studyweb.service;

import at.pichlerlehner.studyweb.domain.Benutzer;
import at.pichlerlehner.studyweb.domain.Frage;
import at.pichlerlehner.studyweb.domain.Fragebogen;
import at.pichlerlehner.studyweb.persistence.FragebogenRepo;
import at.pichlerlehner.studyweb.persistence.PersistenceException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FragebogenService extends AbstractService<Fragebogen> {

    public FragebogenService() {
        repository = new FragebogenRepo();
    }

    public List<Fragebogen> findQuizByUserAccess(Benutzer benutzer) {
        try {
            List<Fragebogen> fragebogenList = ((FragebogenRepo) repository).getFragebogenByCreator(connection, benutzer.getPrimaryKey());
            fragebogenList.addAll(((FragebogenRepo) repository).getByUserAccess(connection, benutzer.getPrimaryKey()));
            return fragebogenList;
        } catch (PersistenceException e) {
            e.printStackTrace();
            logger.error("An error occurred while finding quizzes");
            throw ServiceException.forPersistenceException(e);
        }
    }

    public List<Fragebogen> findQuizByUserWriteAccess(Benutzer benutzer) {
        try {
            List<Fragebogen> fragebogenList = ((FragebogenRepo) repository).getFragebogenByCreator(connection, benutzer.getPrimaryKey());
            fragebogenList.addAll(((FragebogenRepo) repository).getByUserWriteAccess(connection, benutzer.getPrimaryKey()));
            return fragebogenList;
        } catch (PersistenceException e) {
            e.printStackTrace();
            logger.error("An error occurred while finding quizzes");
            throw ServiceException.forPersistenceException(e);
        }
    }

    public List<Fragebogen> findQuizbByCreator(Benutzer benutzer) {
        try {
            return ((FragebogenRepo) repository).getFragebogenByCreator(connection, benutzer.getPrimaryKey());
        } catch (PersistenceException e) {
            e.printStackTrace();
            logger.error("An error occurred while finding quizzes");
            throw ServiceException.forPersistenceException(e);
        }
    }

    public Boolean userHasAccess(Fragebogen fragebogen, Benutzer benutzer) {
        List<Fragebogen> fragebogenList = findQuizByUserAccess(benutzer);
        fragebogenList = fragebogenList.stream().filter(x -> x.getPrimaryKey().equals(fragebogen.getPrimaryKey())).collect(Collectors.toList());
        return !fragebogenList.isEmpty();
    }

    public Boolean userHasWriteAccess(Fragebogen fragebogen, Benutzer benutzer) {
        List<Fragebogen> fragebogenList = findQuizByUserWriteAccess(benutzer);
        fragebogenList = fragebogenList.stream().filter(x -> x.getPrimaryKey().equals(fragebogen.getPrimaryKey())).collect(Collectors.toList());
        return !fragebogenList.isEmpty();
    }

    public double getPercentageByUserAndQuiz(Benutzer benutzer, Fragebogen fragebogen) {
        FrageService frageService = new FrageService();
        BeantwortetService beantwortetService = new BeantwortetService();
        List<Frage> frageList = frageService.getFragenByFragebogen(fragebogen);
        double sum = 0;
        for (Frage frage : frageList) {
            sum += beantwortetService.getPercentageOfUserAndQuestion(benutzer, frage);
        }
        return sum / frageList.size();
    }
}
