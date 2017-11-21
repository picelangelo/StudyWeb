package at.pichlerlehner.studyweb.service;

import at.pichlerlehner.studyweb.domain.Fragebogen;
import at.pichlerlehner.studyweb.persistence.FragebogenRepo;

public class FragebogenService extends AbstractService<Fragebogen> {
    public FragebogenService() {
        this.repository = new FragebogenRepo();
    }
}
