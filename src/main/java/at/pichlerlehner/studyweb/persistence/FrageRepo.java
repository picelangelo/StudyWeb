package at.pichlerlehner.studyweb.persistence;

import at.pichlerlehner.studyweb.domain.Frage;
import at.pichlerlehner.studyweb.domain.Fragebogen;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FrageRepo extends AbstractJdbcRepo<Frage> {

    //Namen
    private String table_name = "Frage";
    private String f_fragebogen = "Fragebogen_Id";
    private String f_frage = "Frage";
    private String f_mulChoice = "IsMultipleChoice";

    @Override
    public long insert(Connection con, Frage entity) throws PersistenceException {
        String query = String.format("INSERT INTO %s (%s,%s,%s,%s) VALUES (?,?,?,?)", table_name, vers, f_fragebogen, f_frage, f_mulChoice);

        long version = entity.getVersion();
        Fragebogen fragebogen = entity.getFragebogen();
        String frage = entity.getFrage();
        boolean mulChoice = entity.isMultipleChoice();
        FragebogenRepo fragebogenRepo = new FragebogenRepo();
        try {
            PreparedStatement preparedStatement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, version);
            if (fragebogen.isNew()) {
                long pk = fragebogenRepo.insert(con, fragebogen);
                fragebogen.setPrimaryKey(pk);
            }
            preparedStatement.setLong(2, fragebogen.getPrimaryKey());
            preparedStatement.setString(3, frage);
            preparedStatement.setBoolean(4, mulChoice);

            int affectedRow = preparedStatement.executeUpdate();
            if (affectedRow == 0) {
                String message = "creating Frage failed, no rows affected.";
                logger.error(message);
                throw new PersistenceException(message);
            }
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet == null) {
                String message = "creating Frage failed, couldn't generate primary key.";
                logger.error(message);
                throw new PersistenceException(message);
            }
            Long generatedKey = null;
            if (resultSet.next()) {
                generatedKey = resultSet.getLong(1);
                logger.info("primary key was generated with {}", generatedKey);
            }
            if (generatedKey == null) {
                String message = "creating Frage failed, couldn't generate primary key.";
                logger.error(message);
                throw new PersistenceException(message);
            }
            con.commit();
            return generatedKey;

        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("couldn't insert Frage");
            throw PersistenceException.forSqlException(e);
        }
    }

    @Override
    public long update(Connection con, Frage entity) throws PersistenceException {
        String query = String.format("UPDATE %s SET %s=?,%s=?,%s=?,%s=? WHERE %s=?", table_name, vers,f_fragebogen, f_frage, f_mulChoice, primary_key);
        FragebogenRepo fragebogenRepo = new FragebogenRepo();
        try {
            long version_db = getVersion(con, entity.getPrimaryKey());
            if (version_db != entity.getVersion()) {
                logger.error("Version conflict");
                throw new PersistenceException("Frage has recently been updated");
            } else {
                entity.setVersion(entity.getVersion() + 1);
            }

            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setLong(1, entity.getVersion());
            if (entity.getFragebogen().isNew()) {
                long pk = fragebogenRepo.insert(con, entity.getFragebogen());
                entity.getFragebogen().setPrimaryKey(pk);
            }
            preparedStatement.setLong(2, entity.getFragebogen().getPrimaryKey());
            preparedStatement.setString(3, entity.getFrage());
            preparedStatement.setBoolean(4, entity.isMultipleChoice());
            preparedStatement.setLong(5, entity.getPrimaryKey());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new PersistenceException("updating failed, no rows affected");
            }
            con.commit();
            logger.info("successfully updated Berechtigung with primary key {}", entity.getPrimaryKey());
            return entity.getPrimaryKey();
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("updating Frage failed.");
            throw PersistenceException.forSqlException(e);
        }
    }

    @Override
    protected String getTableName() {
        return table_name;
    }

    @Override
    protected String getPrimaryKeyColumnName() {
        return primary_key;
    }

    @Override
    protected List<Frage> parseResultSet(Connection con, ResultSet resultSet) throws PersistenceException {
        List<Frage> frageList = new ArrayList<>();
        FragebogenRepo fragebogenRepo = new FragebogenRepo();
        try {
            while (resultSet.next()) {
                Frage frage = new Frage();

                long key = resultSet.getLong(primary_key);
                long ver = resultSet.getLong(vers);
                Optional<Fragebogen> fragebogen = fragebogenRepo.findById(con, resultSet.getLong(f_fragebogen));
                String frageS = resultSet.getString(f_frage);
                boolean mulC = resultSet.getBoolean(f_mulChoice);

                frage.setPrimaryKey(key);
                frage.setVersion(ver);
                fragebogen.ifPresent(frage::setFragebogen);
                frage.setFrage(frageS);
                frage.setMultipleChoice(mulC);

                frageList.add(frage);
            }
            return frageList;
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("parsing Berechtigung failed");
            throw PersistenceException.forSqlException(e);
        }
    }

    public List<Frage> getFragenByFragebogen(Connection connection, Long fragebogen) throws PersistenceException {
        return getElementByLongColumn(connection, f_fragebogen, fragebogen);
    }
}
