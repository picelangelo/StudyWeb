package at.pichlerlehner.studyweb.persistence;

import at.pichlerlehner.studyweb.domain.Benutzer;
import org.junit.Test;


import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.tuple;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.fail;

public class BenutzerRepoTest extends AbstractJdbcRepoTest{


    @Override
    @Test
    public void testFindAll() throws PersistenceException {
        logger.info("TESTING FINDALL");
        int size1 = benutzerRepo.findAll(connection).size();
        long pk = benutzerRepo.insert(connection, testUser);
        int size2 = benutzerRepo.findAll(connection).size();
        assertThat(size1).isEqualTo(size2 - 1);
        benutzerRepo.delete(connection, pk);
        logger.info("TESTING FINDALL SUCCESSFUL\n");
    }

    @Override
    @Test
    public void testInsert() throws PersistenceException {
        logger.info("TESTING INSERT");
        long pk = benutzerRepo.insert(connection, testUser);
        Optional<Benutzer> benutzer = benutzerRepo.findById(connection, pk);
        assertThat(benutzer).isNotNull();
        benutzerRepo.delete(connection, pk);
        logger.info("TESTING INSERT SUCCESSFUL\n");
    }

    @Override
    @Test
    public void testUpdate() throws PersistenceException {
        logger.info("TESTING UPDATE");
        String newVorname = "Maxi";
        String newEmail = "new@test.com";
        long pk = benutzerRepo.insert(connection, testUser);
        Optional<Benutzer> testUser2 = benutzerRepo.findById(connection, pk);
        testUser2.ifPresent(x -> {
            x.setVorname(newVorname);
            x.setEmail(newEmail);
            try {
                benutzerRepo.update(connection, x);
            } catch (PersistenceException e) {
                e.printStackTrace();
                logger.error("TESTING UPDATE FAILED\n");
                fail();
            }
        });
        Optional<Benutzer> testUser2DB = benutzerRepo.findById(connection, pk);
        testUser2DB.ifPresent(x -> {
            assertThat(x.getVorname()).isEqualTo(newVorname);
            assertThat(x.getEmail()).isEqualTo(newEmail);
        });
        benutzerRepo.delete(connection, pk);
        if (!testUser2DB.isPresent()) {
            fail();
            logger.error("TESTING UPDATE FAILED\n");
        } else {
            logger.info("TESTING UPDATE SUCCESSFUL\n");
        }
    }

    @Override
    @Test
    public void testFindById() throws PersistenceException {
        logger.info("TESTING FINDBYID");
        long pk = benutzerRepo.insert(connection, testUser);
        Optional<Benutzer> benutzer = benutzerRepo.findById(connection, pk);
        assertThat(benutzer).isNotNull();
        assertThat(benutzer.isPresent());
        benutzerRepo.delete(connection, pk);
        logger.info("TESTING FINDBYID SUCCESSFUL\n");
    }

    @Override
    @Test
    public void testDelete() throws PersistenceException {
        logger.info("TESTING DELETE");
        long pk = benutzerRepo.insert(connection, testUser);
        int count1 = benutzerRepo.findAll(connection).size();
        benutzerRepo.delete(connection, pk);
        int count2 = benutzerRepo.findAll(connection).size();
        assertThat(count1).isEqualTo(count2 + 1);
        logger.info("TESTING DELETE SUCCESSFUL\n");
    }
}
