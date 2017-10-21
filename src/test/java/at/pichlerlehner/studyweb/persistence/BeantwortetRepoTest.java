package at.pichlerlehner.studyweb.persistence;

import at.pichlerlehner.studyweb.domain.Antwort;
import at.pichlerlehner.studyweb.domain.Beantwortet;
import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.fail;

public class BeantwortetRepoTest extends AbstractJdbcRepoTest{
    @Override
    @Test
    public void testFindAll() throws PersistenceException {
        logger.info("TESTING FINDALL");
        long pk1 = beantwortetRepo.insert(connection, testBeantwortet);
        int size1 = beantwortetRepo.findAll(connection).size();
        long pk2 = beantwortetRepo.insert(connection, testBeantwortet);
        int size2 = beantwortetRepo.findAll(connection).size();
        assertThat(size1).isEqualTo(size2 - 1);
        beantwortetRepo.delete(connection, pk1);
        beantwortetRepo.delete(connection, pk2);
        logger.info("TESTING FIND ALL BEANTWORTET SUCCESSFUL\n");
    }

    @Override
    @Test
    public void testInsert() throws PersistenceException {
        logger.info("TESTING INSERT");
        long pk = beantwortetRepo.insert(connection, testBeantwortet);
        Optional<Beantwortet> beantwortet = beantwortetRepo.findById(connection, pk);
        assertThat(beantwortet).isNotNull();
        beantwortetRepo.delete(connection, pk);
        logger.info("TESTING INSERT SUCCESSFUL\n");
    }

    @Override
    @Test
    public void testUpdate() throws PersistenceException {
        logger.info("TESTING UPDATE");
        int newRichtig = 10;
        long pk = beantwortetRepo.insert(connection, testBeantwortet);
        Optional<Beantwortet> testBeantwortet2 = beantwortetRepo.findById(connection, pk);
        testBeantwortet2.ifPresent(x -> {
            x.setAnzahlRichtig(newRichtig);
            try {
                beantwortetRepo.update(connection, x);
            } catch (PersistenceException e) {
                e.printStackTrace();
                logger.error("TESTING UPDATE BEANTWORTET FAILED\n");
                fail();
            }
        });
        Optional<Beantwortet> testBeantwortet2DB = beantwortetRepo.findById(connection, pk);
        testBeantwortet2DB.ifPresent(x -> {
            assertThat(x.getAnzahlRichtig()).isEqualTo(newRichtig);
        });
        antwortRepo.delete(connection, pk);
        if (!testBeantwortet2DB.isPresent()) {
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
        long pk = beantwortetRepo.insert(connection, testBeantwortet);
        Optional<Beantwortet> beantwortet = beantwortetRepo.findById(connection, pk);
        assertThat(beantwortet).isNotNull();
        assertThat(beantwortet.isPresent());
        beantwortetRepo.delete(connection, pk);
        logger.info("TESTING FINDBYID SUCCESSFUL\n");
    }

    @Override
    @Test
    public void testDelete() throws PersistenceException {
        logger.info("TESTING DELETE");
        long pk = beantwortetRepo.insert(connection, testBeantwortet);
        int count1 = beantwortetRepo.findAll(connection).size();
        beantwortetRepo.delete(connection, pk);
        int count2 = beantwortetRepo.findAll(connection).size();
        assertThat(count1).isEqualTo(count2 + 1);
        logger.info("TESTING DELETE BEANTWORTET SUCCESSFUL\n");
    }
}
