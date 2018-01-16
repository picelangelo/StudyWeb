package at.pichlerlehner.studyweb.persistence;

import at.pichlerlehner.studyweb.domain.Berechtigung;
import org.junit.Test;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.fail;

public class BerechtigungRepoTest extends AbstractJdbcRepoTest {
    @Override
    @Test
    public void testFindAll() throws PersistenceException {
        logger.info("TESTING FINDALL");
        long pk1 = berechtigungRepo.insert(connection, testBerechtigung);
        int size1 = berechtigungRepo.findAll(connection).size();
        long pk2 = berechtigungRepo.insert(connection, testBerechtigung);
        int size2 = berechtigungRepo.findAll(connection).size();
        assertThat(size1).isEqualTo(size2 - 1);
        berechtigungRepo.delete(connection, pk1);
        berechtigungRepo.delete(connection, pk2);
        logger.info("TESTING FIND ALL BERECHTIGUNGEN SUCCESSFUL\n");
    }

    @Override
    @Test
    public void testInsert() throws PersistenceException {
        logger.info("TESTING INSERT");
        long pk = berechtigungRepo.insert(connection, testBerechtigung);
        Optional<Berechtigung> berechtigung = berechtigungRepo.findById(connection, pk);
        assertThat(berechtigung).isNotNull();
        berechtigungRepo.delete(connection, pk);
        logger.info("TESTING INSERT SUCCESSFUL\n");
    }

    @Override
    @Test
    public void testUpdate() throws PersistenceException {
        logger.info("TESTING UPDATE");
        boolean newRight = false;
        long pk = berechtigungRepo.insert(connection, testBerechtigung);
        Optional<Berechtigung> testBerechtigung = berechtigungRepo.findById(connection, pk);
        testBerechtigung.ifPresent(x -> {
            x.setDarfBearbeiten(newRight);
            try {
                berechtigungRepo.update(connection, x);
            } catch (PersistenceException e) {
                e.printStackTrace();
                logger.error("TESTING UPDATE BERECHTIGUNG FAILED\n");
                fail();
            }
        });
        Optional<Berechtigung> testBerechtigung2DB = berechtigungRepo.findById(connection, pk);
        testBerechtigung2DB.ifPresent(x -> {
            assertThat(x.isDarfBearbeiten()).isEqualTo(newRight);
        });
        berechtigungRepo.delete(connection, pk);
        if (!testBerechtigung2DB.isPresent()) {
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
        long pk = berechtigungRepo.insert(connection, testBerechtigung);
        Optional<Berechtigung> benutzer = berechtigungRepo.findById(connection, pk);
        assertThat(benutzer).isNotNull();
        assertThat(benutzer.isPresent());
        berechtigungRepo.delete(connection, pk);
        logger.info("TESTING FINDBYID SUCCESSFUL\n");
    }

    @Override
    @Test
    public void testDelete() throws PersistenceException {
        logger.info("TESTING DELETE");
        long pk = berechtigungRepo.insert(connection, testBerechtigung);
        int count1 = berechtigungRepo.findAll(connection).size();
        berechtigungRepo.delete(connection, pk);
        int count2 = berechtigungRepo.findAll(connection).size();
        assertThat(count1).isEqualTo(count2 + 1);
        logger.info("TESTING DELETE BERECHTIGUNG SUCCESSFUL\n");
    }
}
