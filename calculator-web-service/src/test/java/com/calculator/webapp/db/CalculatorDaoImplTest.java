package com.calculator.webapp.db;

import com.calculator.webapp.db.dao.CalculatorDaoImpl;
import com.calculator.webapp.db.dto.CalculatorResponseDTO;
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
        setInitialTableInDataBase(DatasetProvider.MULTIPLE_ENTITIES_DATASET_PATH);

        final int expectedNumberOfEntities = 6;
        List<CalculatorResponseDTO> actualItems = dao.getAllItems();

        assertThat(actualItems, is(notNullValue()));
        assertThat(actualItems.size(), is(equalTo(expectedNumberOfEntities)));
    }

    @Test
    public void getItem_populatedDataSet_expectedItem() throws Exception {
        resetStateOfDatabase();
        setInitialTableInDataBase(DatasetProvider.MULTIPLE_ENTITIES_DATASET_PATH);

        CalculatorResponseDTO actualItem = dao.getItem(6L);

        assertThat(actualItem, is(notNullValue()));
        assertThat(actualItem.getId(), is(equalTo(6L)));
    }

    @Test
    public void saveItem_emptyDataSet_tableSize1() throws Exception {
        resetStateOfDatabase();
        setInitialTableInDataBase(DatasetProvider.EMPTY_DATASET_PATH);

        ITable expectedTable = getDataSet(DatasetProvider.ONE_ENTITY_DATASET_PATH).getTable(responseTableName);

        Date currentDateTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2019-09-09 15:00:00");
        CalculatorResponseDTO entity = new CalculatorResponseDTO("1+1","2.0",currentDateTime);

        dao.saveItem(entity);


        ITable actualTable = getActualTable(responseTableName);
        Assertion.assertEquals(expectedTable, actualTable);
    }

    @Test
    public void deleteItem_oneEntityDataSet_emptyTable() throws Exception {
        resetStateOfDatabase();
        setInitialTableInDataBase(DatasetProvider.ONE_ENTITY_DATASET_PATH);

        Date currentDateTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2019-09-09 15:00:00");
        CalculatorResponseDTO entity = new CalculatorResponseDTO("1+1","2.0",currentDateTime);
        entity.setId(1);

        dao.deleteItem(entity);
        final int emptyTableSize = 0;

        ITable actualTable = getActualTable(responseTableName);
        assertThat(actualTable.getRowCount(), is(emptyTableSize));
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