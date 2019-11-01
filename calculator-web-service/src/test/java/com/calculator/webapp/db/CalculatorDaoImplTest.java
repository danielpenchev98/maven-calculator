package com.calculator.webapp.db;

import com.calculator.webapp.db.dao.CalculatorDaoImpl;
import com.calculator.webapp.db.dao.exceptions.ItemDoesNotExistException;
import com.calculator.webapp.db.dto.CalculationRequestDTO;
import org.dbunit.Assertion;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.operation.DatabaseOperation;
import org.dbunit.util.TableFormatter;
import org.dbunit.util.fileloader.FlatXmlDataFileLoader;
import org.junit.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.calculator.webapp.db.dto.requeststatus.RequestStatus.COMPLETED;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.IsNull.notNullValue;

public class CalculatorDaoImplTest {

    private static IDatabaseConnection databaseConnection;
    private EntityManager manager;
    private CalculatorDaoImpl dao;

    private static final String responseTableName = "calculator_responses";

    @BeforeClass
    public static void setUpDB() throws Exception {
        databaseConnection = new DatabaseConnection(DriverManager.getConnection(DerbyConfiguration.CONNECTION_URL,
                DerbyConfiguration.DB_USERNAME,
                DerbyConfiguration.DB_PASSWORD));
        allowEmptyValuesInColumns(databaseConnection);
    }

    private static void allowEmptyValuesInColumns(final IDatabaseConnection databaseConnection){
        DatabaseConfig config = databaseConnection.getConfig();
        config.setProperty(DatabaseConfig.FEATURE_ALLOW_EMPTY_FIELDS,true);
    }

    @AfterClass
    public static void tearDownDB() throws Exception {
        databaseConnection.close();
    }

    @Before
    public void setUp() {
        setUpEntityManager();
        setUpDao(manager);
    }

    @After
    public void tearDown()
    {
        manager.close();
    }

    @Test
    public void getAllItems_populatedDataSet_expectedSize() throws Exception {
        resetStateOfDatabase();
        setInitialTableInDataBase(DatasetPaths.MULTIPLE_ENTITIES_DATASET_PATH);

        final int expectedNumberOfEntities = 6;
        List<CalculationRequestDTO> actualItems = dao.getAllItems();

        assertThat(actualItems, is(notNullValue()));
        assertThat(actualItems.size(), is(equalTo(expectedNumberOfEntities)));
    }

    @Test
    public void getAllPendingCalculations_populatedDataSet() throws Exception {
        resetStateOfDatabase();
        setInitialTableInDataBase(DatasetPaths.MULTIPLE_ENTITIES_DATASET_PATH);
        final int expectedNumberOfEntities = 2;
        List<CalculationRequestDTO> pendingItems = dao.getAllPendingCalculations();

        assertThat(pendingItems,is(notNullValue()));
        assertThat(pendingItems.size(),is(equalTo(expectedNumberOfEntities)));
    }

    @Test
    public void getItem_populatedDataSet_ItemFound() throws Exception {
        resetStateOfDatabase();
        setInitialTableInDataBase(DatasetPaths.MULTIPLE_ENTITIES_DATASET_PATH);

        CalculationRequestDTO actualItem = dao.getItem(6L);

        assertThat(actualItem, is(notNullValue()));
        assertThat(actualItem.getId(), is(equalTo(6L)));
    }

    @Test(expected = ItemDoesNotExistException.class)
    public void getItem_populatedDataSet_ItemNotFound() throws Exception {
        resetStateOfDatabase();
        setInitialTableInDataBase(DatasetPaths.MULTIPLE_ENTITIES_DATASET_PATH);

        dao.getItem(7L);
    }



    @Test
    public void saveItem_emptyDataSet_tableSize1() throws Exception {
        resetStateOfDatabase();
        setInitialTableInDataBase(DatasetPaths.EMPTY_DATASET_PATH);

        ITable expectedTable = getDataSet(DatasetPaths.ONE_ENTITY_DATASET_PATH).getTable(responseTableName);

        Date currentDateTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2019-09-09 15:00:00");
        CalculationRequestDTO entity = createCalculationRequestDTO(0,"1+1",2.0,null,COMPLETED.getStatusCode(),currentDateTime);

        dao.saveItem(entity);

        ITable actualTable = getActualTable(responseTableName);
        Assertion.assertEquals(expectedTable, actualTable);
    }

    @Test
    public void deleteItem_oneEntityDataSet_emptyTable() throws Exception {
        resetStateOfDatabase();
        setInitialTableInDataBase(DatasetPaths.ONE_ENTITY_DATASET_PATH);

        Date currentDateTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2019-09-09 15:00:00");
        CalculationRequestDTO entity = createCalculationRequestDTO(1,"1+1",2.0,null,COMPLETED.getStatusCode(),currentDateTime);

        dao.deleteItem(entity);
        final int emptyTableSize = 0;

        ITable actualTable = getActualTable(responseTableName);
        assertThat(actualTable.getRowCount(), is(emptyTableSize));
    }

    @Test
    public void update_populatedDataSet() throws Exception {
        resetStateOfDatabase();
        setInitialTableInDataBase(DatasetPaths.MULTIPLE_ENTITIES_DATASET_PATH);

        Date currentDateTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2019-09-09 15:00:01");
        CalculationRequestDTO entity = createCalculationRequestDTO(2,"1/0",0.0,"Division by zero",COMPLETED.getStatusCode(),currentDateTime);

        dao.update(entity);

        ITable actualTable = getActualTable(responseTableName);
        assertThat(actualTable.getValue(1,"errorMsg"),is("Division by zero"));
    }

    private CalculationRequestDTO createCalculationRequestDTO(final int id,final String equation,final double result,final String errorMsg,final int statusCode,final Date time){
        CalculationRequestDTO request = new CalculationRequestDTO(equation,time);
        request.setId(id);
        request.setStatusCode(statusCode);
        request.setCalculationResult(result);
        request.setErrorMsg(errorMsg);
        return request;
    }


    private void resetStateOfDatabase() throws SQLException {
        PreparedStatement statement = databaseConnection.getConnection().prepareStatement(DerbyConfiguration.RESTART_IDENTITY_COUNTER);
        statement.executeUpdate();
    }

    private void setUpEntityManager() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("test-unit");
        manager = factory.createEntityManager();
    }

    private void setUpDao(final EntityManager entityManager) {
        dao=new CalculatorDaoImpl(entityManager);
    }

    private void setInitialTableInDataBase(final String pathToDataSet) throws Exception {
        IDataSet initialDataSet = getDataSet(pathToDataSet);
        uploadDataSet(initialDataSet);
    }

    private IDataSet getDataSet(final String path) {
        return new FlatXmlDataFileLoader().load(path);
    }

    private void uploadDataSet(final IDataSet wantedDataSet) throws Exception {
        DatabaseOperation.CLEAN_INSERT.execute(databaseConnection, wantedDataSet);
    }

    private ITable getActualTable(final String tableName) throws Exception {
        return databaseConnection.createDataSet().getTable(tableName);
    }

}