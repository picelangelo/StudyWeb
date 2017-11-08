package at.pichlerlehner.studyweb.service;

import at.pichlerlehner.studyweb.domain.Benutzer;
import at.pichlerlehner.studyweb.foundation.Ensurer;
import at.pichlerlehner.studyweb.persistence.BenutzerRepo;
import at.pichlerlehner.studyweb.persistence.PersistenceException;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class BenutzerService extends AbstractService<Benutzer>{

    public BenutzerService() {
        repository = new BenutzerRepo();
    }

    public List<Benutzer> findByEmail(){
        return null;
    }

}
