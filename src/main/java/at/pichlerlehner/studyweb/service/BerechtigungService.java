package at.pichlerlehner.studyweb.service;

import at.pichlerlehner.studyweb.domain.Berechtigung;
import at.pichlerlehner.studyweb.persistence.BerechtigungRepo;

public class BerechtigungService extends AbstractService<Berechtigung> {
    public BerechtigungService() {
        this.repository = new BerechtigungRepo();
    }
}
