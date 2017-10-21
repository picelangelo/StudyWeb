package at.pichlerlehner.studyweb.persistence;

import at.pichlerlehner.studyweb.domain.Berechtigung;
import at.pichlerlehner.studyweb.domain.Frage;
import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.fail;

public class FrageRepoTest extends AbstractJdbcRepoTest {
    @Override
    @Test
    public void testFindAll() throws PersistenceException {
        logger.info("TESTING FINDALL");
        long pk1 = frageRepo.insert(connection, testFrage);
        int size1 = frageRepo.findAll(connection).size();
        long pk2 = frageRepo.insert(connection, testFrage);
        int size2 = frageRepo.findAll(connection).size();
        assertThat(size1).isEqualTo(size2 - 1);
        frageRepo.delete(connection, pk1);
        frageRepo.delete(connection, pk2);
        logger.info("TESTING FIND ALL FRAGEN SUCCESSFUL\n");
    }

    @Override
    @Test
    public void testInsert() throws PersistenceException {
        logger.info("TESTING INSERT");
        long pk = frageRepo.insert(connection, testFrage);
        Optional<Frage> frage = frageRepo.findById(connection, pk);
        assertThat(frage).isNotNull();
        frageRepo.delete(connection, pk);
        logger.info("TESTING INSERT SUCCESSFUL\n");
    }

    @Override
    @Test
    public void testUpdate() throws PersistenceException {
        logger.info("TESTING UPDATE");
        String newFrage = "Was ist meine Lieblingsfarbe?";
        long pk = frageRepo.insert(connection, testFrage);
        Optional<Frage> testFrage = frageRepo.findById(connection, pk);
        testFrage.ifPresent(x -> {
            x.setFrage(newFrage);
            try {
                frageRepo.update(connection, x);
            } catch (PersistenceException e) {
                e.printStackTrace();
                logger.error("TESTING UPDATE FRAGEN FAILED\n");
                fail();
            }
        });
        Optional<Frage> testFrage2DB = frageRepo.findById(connection, pk);
        testFrage2DB.ifPresent(x -> {
            assertThat(x.getFrage()).isEqualTo(newFrage);
        });
        frageRepo.delete(connection, pk);
        if (!testFrage2DB.isPresent()) {
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
        long pk = frageRepo.insert(connection, testFrage);
        Optional<Frage> benutzer = frageRepo.findById(connection, pk);
        assertThat(benutzer).isNotNull();
        assertThat(benutzer.isPresent());
        frageRepo.delete(connection, pk);
        logger.info("TESTING FINDBYID SUCCESSFUL\n");
    }

    @Override
    @Test
    public void testDelete() throws PersistenceException {
        logger.info("TESTING DELETE");
        long pk = frageRepo.insert(connection, testFrage);
        int count1 = frageRepo.findAll(connection).size();
        frageRepo.delete(connection, pk);
        int count2 = frageRepo.findAll(connection).size();
        assertThat(count1).isEqualTo(count2 + 1);
        logger.info("TESTING DELETE FRAGE SUCCESSFUL\n");
    }
}
