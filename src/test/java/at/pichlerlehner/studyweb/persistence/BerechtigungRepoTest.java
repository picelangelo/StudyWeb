package at.pichlerlehner.studyweb.persistence;

import at.pichlerlehner.studyweb.domain.Berechtigung;
import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

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
    public void testUpdate() throws PersistenceException {
        
    }

    @Override
    public void testFindById() throws PersistenceException {

    }

    @Override
    public void testDelete() throws PersistenceException {

    }
}
