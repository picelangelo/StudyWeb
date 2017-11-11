package at.pichlerlehner.studyweb.service;

import at.pichlerlehner.studyweb.domain.Antwort;
import at.pichlerlehner.studyweb.persistence.AntwortRepo;

public class AntwortService extends AbstractService<Antwort> {
    public AntwortService() {
        this.repository = new AntwortRepo();
    }

}
