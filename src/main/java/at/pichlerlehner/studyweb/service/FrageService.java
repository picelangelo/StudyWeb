package at.pichlerlehner.studyweb.service;

import at.pichlerlehner.studyweb.domain.Frage;
import at.pichlerlehner.studyweb.persistence.FrageRepo;

public class FrageService extends AbstractService<Frage> {

    public FrageService() {
        this.repository = new FrageRepo();
    }
}
