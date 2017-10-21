package at.pichlerlehner.studyweb.persistence;

import at.pichlerlehner.studyweb.domain.Benutzer;
import at.pichlerlehner.studyweb.domain.Frage;
import at.pichlerlehner.studyweb.domain.Fragebogen;
import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.fail;

public class FragebogenRepoTest extends AbstractJdbcRepoTest {
    @Override
    @Test
    public void testFindAll() throws PersistenceException {
        logger.info("TESTING FINDALL");
        long pk1 = fragebogenRepo.insert(connection, testFragebogen);
        int size1 = fragebogenRepo.findAll(connection).size();
        long pk2 = fragebogenRepo.insert(connection, testFragebogen);
        int size2 = fragebogenRepo.findAll(connection).size();
        assertThat(size1).isEqualTo(size2 - 1);
        fragebogenRepo.delete(connection, pk1);
        fragebogenRepo.delete(connection, pk2);
        logger.info("TESTING FIND ALL FRAGEBOEGEN SUCCESSFUL\n");
    }

    @Override
    @Test
    public void testInsert() throws PersistenceException {
        logger.info("TESTING INSERT");
        long pk = fragebogenRepo.insert(connection, testFragebogen);
        Optional<Fragebogen> berechtigung = fragebogenRepo.findById(connection, pk);
        assertThat(berechtigung).isNotNull();
        fragebogenRepo.delete(connection, pk);
        logger.info("TESTING INSERT SUCCESSFUL\n");
    }

    @Override
    @Test
    public void testUpdate() throws PersistenceException {
        logger.info("TESTING UPDATE");
        Benutzer newUser = testUser2;
        long pk = fragebogenRepo.insert(connection, testFragebogen);
        Optional<Fragebogen> testFragebogen = fragebogenRepo.findById(connection, pk);
        testFragebogen.ifPresent(x -> {
            x.setErsteller(newUser);
            try {
                fragebogenRepo.update(connection, x);
            } catch (PersistenceException e) {
                e.printStackTrace();
                logger.error("TESTING UPDATE FRAGEBOGEN FAILED\n");
                fail();
            }
        });
        Optional<Fragebogen> testFragebogen2DB = fragebogenRepo.findById(connection, pk);
        testFragebogen2DB.ifPresent(x -> {
            assertThat(x.getErsteller().getEmail()).isEqualTo(newUser.getEmail());
        });
        berechtigungRepo.delete(connection, pk);
        if (!testFragebogen2DB.isPresent()) {
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
        long pk = fragebogenRepo.insert(connection, testFragebogen);
        Optional<Fragebogen> benutzer = fragebogenRepo.findById(connection, pk);
        assertThat(benutzer).isNotNull();
        assertThat(benutzer.isPresent());
        fragebogenRepo.delete(connection, pk);
        logger.info("TESTING FINDBYID SUCCESSFUL\n");
    }

    @Override
    @Test
    public void testDelete() throws PersistenceException {
        logger.info("TESTING DELETE");
        long pk = fragebogenRepo.insert(connection, testFragebogen);
        int count1 = fragebogenRepo.findAll(connection).size();
        fragebogenRepo.delete(connection, pk);
        int count2 = fragebogenRepo.findAll(connection).size();
        assertThat(count1).isEqualTo(count2 + 1);
        logger.info("TESTING DELETE FRAGEBOGEN SUCCESSFUL\n");
    }
}
