package com.calculator.webapp.db;

import com.calculator.webapp.db.dao.RequestDaoImpl;
import com.calculator.webapp.db.dao.exceptions.ItemDoesNotExistException;
import com.calculator.webapp.db.dto.RequestDTO;
import org.dbunit.Assertion;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.operation.DatabaseOperation;
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

public class RequestDaoImplTest {

    private static IDatabaseConnection databaseConnection;
    private EntityManager manager;
    private RequestDaoImpl requestDao;

    private static final String responseTableName = "calculation_requests";

    @BeforeClass
    public static void setUpDB() throws Exception {
        databaseConnection = new DatabaseConnection(DriverManager.getConnection(DerbyConfiguration.CONNECTION_URL,
                DerbyConfiguration.DB_USERNAME,
                DerbyConfiguration.DB_PASSWORD));
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
        setInitialTableInDataBase(DatasetPaths.MULTIPLE_REQUESTS_DATASET_PATH);

        final int expectedNumberOfEntities = 6;
        List<RequestDTO> actualItems = requestDao.getAllItems();

        assertThat(actualItems, is(notNullValue()));
        assertThat(actualItems.size(), is(equalTo(expectedNumberOfEntities)));
    }

    @Test
    public void getAllPendingCalculations_populatedDataSet() throws Exception {
        resetStateOfDatabase();
        setInitialTableInDataBase(DatasetPaths.MULTIPLE_REQUESTS_DATASET_PATH);
        final int expectedNumberOfEntities = 2;
        List<RequestDTO> pendingItems = requestDao.getAllPendingRequests();

        assertThat(pendingItems,is(notNullValue()));
        assertThat(pendingItems.size(),is(equalTo(expectedNumberOfEntities)));
    }

    @Test
    public void getItem_populatedDataSet_ItemFound() throws Exception {
        resetStateOfDatabase();
        setInitialTableInDataBase(DatasetPaths.MULTIPLE_REQUESTS_DATASET_PATH);

        RequestDTO actualItem = requestDao.getItem(6L);

        assertThat(actualItem, is(notNullValue()));
        assertThat(actualItem.getId(), is(equalTo(6L)));
    }

    @Test(expected = ItemDoesNotExistException.class)
    public void getItem_populatedDataSet_ItemNotFound() throws Exception {
        resetStateOfDatabase();
        setInitialTableInDataBase(DatasetPaths.MULTIPLE_REQUESTS_DATASET_PATH);

        requestDao.getItem(7L);
    }

    @Test
    public void saveItem_emptyDataSet_tableSize1() throws Exception {
        resetStateOfDatabase();
        setInitialTableInDataBase(DatasetPaths.NO_REQUESTS_DATASET_PATH);

        ITable expectedTable = getDataSet(DatasetPaths.ONE_REQUEST_DATASET_PATH).getTable(responseTableName);

        Date currentDateTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2019-09-09 15:00:00");
        RequestDTO entity = createCalculationRequestDTO(0,"1+1",COMPLETED.getStatusCode(),currentDateTime);

        requestDao.saveItem(entity);

        ITable actualTable = getActualTable(responseTableName);
        Assertion.assertEquals(expectedTable, actualTable);
    }

    @Test
    public void deleteItem_oneEntityDataSet_emptyTable() throws Exception {
        resetStateOfDatabase();
        setInitialTableInDataBase(DatasetPaths.ONE_REQUEST_DATASET_PATH);

        Date currentDateTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2019-09-09 15:00:00");
        RequestDTO entity = createCalculationRequestDTO(1,"1+1",COMPLETED.getStatusCode(),currentDateTime);

        requestDao.deleteItem(entity);
        final int emptyTableSize = 0;

        ITable actualTable = getActualTable(responseTableName);
        assertThat(actualTable.getRowCount(), is(emptyTableSize));
    }

    @Test
    public void update_populatedDataSet() throws Exception {
        resetStateOfDatabase();
        setInitialTableInDataBase(DatasetPaths.MULTIPLE_REQUESTS_DATASET_PATH);

        Date currentDateTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2019-09-09 15:00:01");
        RequestDTO entity = createCalculationRequestDTO(2,"1/0",COMPLETED.getStatusCode(),currentDateTime);

        requestDao.update(entity);

        ITable actualTable = getActualTable(responseTableName);
        assertThat(actualTable.getValue(1,"statusCode"),is(COMPLETED.getStatusCode()));
    }

    private RequestDTO createCalculationRequestDTO(final int id, final String expression, final int statusCode, final Date time){
        RequestDTO request = new RequestDTO(expression,time);
        request.setId(id);
        request.setStatusCode(statusCode);
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
        requestDao=new RequestDaoImpl(entityManager);
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