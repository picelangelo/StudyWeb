package at.pichlerlehner.studyweb.service;

import at.pichlerlehner.studyweb.domain.Beantwortet;
import at.pichlerlehner.studyweb.domain.Benutzer;
import at.pichlerlehner.studyweb.domain.Frage;
import at.pichlerlehner.studyweb.persistence.BeantwortetRepo;
import at.pichlerlehner.studyweb.persistence.PersistenceException;

import java.util.Optional;

public class BeantwortetService extends AbstractService<Beantwortet>{
    public BeantwortetService() {
        this.repository = new BeantwortetRepo();
    }

    public Optional<Beantwortet> newBeantwortet(Benutzer benutzer, Frage frage, boolean richtig) {
        try {
            Optional<Beantwortet> beantwortetOptional = ((BeantwortetRepo)repository).getBeantwortetByUserAndQuestion(connection, benutzer.getPrimaryKey(), frage.getPrimaryKey());
            Beantwortet beantwortet = beantwortetOptional.orElse(new Beantwortet(benutzer, 0, 0, frage));
            if (richtig) {
                beantwortet.setAnzahlRichtig(beantwortet.getAnzahlRichtig()+1);
            } else {
                beantwortet.setAnzahlFalsch(beantwortet.getAnzahlFalsch()+1);
            }
            Long pk = repository.save(connection, beantwortet);
            return findEntityById(pk);
        } catch (PersistenceException e) {
            e.printStackTrace();
            logger.error("An error occurred while finding Beantwortet");
            throw ServiceException.forPersistenceException(e);
        }
    }
}
