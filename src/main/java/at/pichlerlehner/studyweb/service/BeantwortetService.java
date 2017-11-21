package at.pichlerlehner.studyweb.service;

import at.pichlerlehner.studyweb.domain.Beantwortet;
import at.pichlerlehner.studyweb.persistence.BeantwortetRepo;

public class BeantwortetService extends AbstractService<Beantwortet>{
    public BeantwortetService() {
        this.repository = new BeantwortetRepo();
    }
}
