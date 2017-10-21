package at.pichlerlehner.studyweb.persistence;

import at.pichlerlehner.studyweb.domain.Antwort;
import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.fail;

public class AntwortRepoTest extends AbstractJdbcRepoTest{
    @Override
    @Test
    public void testFindAll() throws PersistenceException {
        logger.info("TESTING FINDALL");
        long pk1 = antwortRepo.insert(connection, testAntwort);
        int size1 = antwortRepo.findAll(connection).size();
        long pk2 = antwortRepo.insert(connection, testAntwort);
        int size2 = antwortRepo.findAll(connection).size();
        assertThat(size1).isEqualTo(size2 - 1);
        antwortRepo.delete(connection, pk1);
        antwortRepo.delete(connection, pk2);
        logger.info("TESTING FIND ALL ANTWORTEN SUCCESSFUL\n");
    }

    @Override
    @Test
    public void testInsert() throws PersistenceException {
        logger.info("TESTING INSERT");
        long pk = antwortRepo.insert(connection, testAntwort);
        Optional<Antwort> antwort = antwortRepo.findById(connection, pk);
        assertThat(antwort).isNotNull();
        antwortRepo.delete(connection, pk);
        logger.info("TESTING INSERT SUCCESSFUL\n");
    }

    @Override
    @Test
    public void testUpdate() throws PersistenceException {
        logger.info("TESTING UPDATE");
        String newAntwort = "POS";
        long pk = antwortRepo.insert(connection, testAntwort);
        Optional<Antwort> testAntwort2 = antwortRepo.findById(connection, pk);
        testAntwort2.ifPresent(x -> {
            x.setAntwort(newAntwort);
            try {
                antwortRepo.update(connection, x);
            } catch (PersistenceException e) {
                e.printStackTrace();
                logger.error("TESTING UPDATE ANTWORT FAILED\n");
                fail();
            }
        });
        Optional<Antwort> testAntwort2DB = antwortRepo.findById(connection, pk);
        testAntwort2DB.ifPresent(x -> {
            assertThat(x.getAntwort()).isEqualTo(newAntwort);
        });
        antwortRepo.delete(connection, pk);
        if (!testAntwort2.isPresent()) {
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
        long pk = antwortRepo.insert(connection, testAntwort);
        Optional<Antwort> antwort = antwortRepo.findById(connection, pk);
        assertThat(antwort).isNotNull();
        assertThat(antwort.isPresent());
        antwortRepo.delete(connection, pk);
        logger.info("TESTING FINDBYID SUCCESSFUL\n");
    }

    @Override
    @Test
    public void testDelete() throws PersistenceException {
        logger.info("TESTING DELETE");
        long pk = antwortRepo.insert(connection, testAntwort);
        int count1 = antwortRepo.findAll(connection).size();
        antwortRepo.delete(connection, pk);
        int count2 = antwortRepo.findAll(connection).size();
        assertThat(count1).isEqualTo(count2 + 1);
        logger.info("TESTING DELETE ANTWORT SUCCESSFUL\n");
    }
}
