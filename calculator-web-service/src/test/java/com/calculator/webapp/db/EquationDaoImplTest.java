package com.calculator.webapp.db;

import com.calculator.webapp.db.dao.EquationDaoImpl;
import com.calculator.webapp.db.dao.exceptions.ItemDoesNotExistException;
import com.calculator.webapp.db.dto.CalculationRequestDTO;
import com.calculator.webapp.db.dto.CalculatorResponseDTO;
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
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNull.notNullValue;

@Ignore
public class EquationDaoImplTest {

    private static IDatabaseConnection databaseConnection;
    private EntityManager manager;
    private EquationDaoImpl equationDao;

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
        setInitialTableInDataBase(DatasetPaths.MULTIPLE_RESPONSES_DATASET_PATH);

        final int expectedNumberOfEntities = 5;
        List<CalculatorResponseDTO> actualItems = equationDao.getAllItems();

        assertThat(actualItems, is(notNullValue()));
        assertThat(actualItems.size(), is(equalTo(expectedNumberOfEntities)));
    }

    @Test
    public void getItem_populatedDataSet_ItemFound() throws Exception {
        setInitialTableInDataBase(DatasetPaths.MULTIPLE_RESPONSES_DATASET_PATH);

        CalculatorResponseDTO actualItem = equationDao.getItem("1+1");

        assertThat(actualItem, is(notNullValue()));
        assertThat(actualItem.getCalculationResult(), is(equalTo(2.0)));
    }

    @Test(expected = ItemDoesNotExistException.class)
    public void getItem_populatedDataSet_ItemNotFound() throws Exception {
        setInitialTableInDataBase(DatasetPaths.MULTIPLE_RESPONSES_DATASET_PATH);

        equationDao.getItem("1..0");
    }

    @Test
    public void saveItem_emptyDataSet_tableSize1() throws Exception {
        setInitialTableInDataBase(DatasetPaths.EMPTY_RESPONSE_DATASET_PATH);

        ITable expectedTable = getDataSet(DatasetPaths.ONE_RESPONSE_DATASET_PATH).getTable(responseTableName);

        CalculatorResponseDTO entity =createCalculationRequestDTO("1+1",2.0,null);

        equationDao.saveItem(entity);

        ITable actualTable = getActualTable(responseTableName);
        Assertion.assertEquals(expectedTable, actualTable);
    }

    @Test
    public void deleteItem_oneEntityDataSet_emptyTable() throws Exception {
        setInitialTableInDataBase(DatasetPaths.ONE_RESPONSE_DATASET_PATH);

        CalculatorResponseDTO entity = createCalculationRequestDTO("1+1",2.0,null);

        equationDao.deleteItem(entity);
        final int emptyTableSize = 0;

        ITable actualTable = getActualTable(responseTableName);
        assertThat(actualTable.getRowCount(), is(emptyTableSize));
    }

    @Test
    public void update_populatedDataSet() throws Exception {
        setInitialTableInDataBase(DatasetPaths.MULTIPLE_RESPONSES_DATASET_PATH);

        CalculatorResponseDTO entity = createCalculationRequestDTO("1/0",0.0,"Division by zero");

        equationDao.update(entity);

        ITable actualTable = getActualTable(responseTableName);
        System.out.println(new TableFormatter().format(actualTable));
        assertThat(actualTable.getValue(3,"errorMsg"),is("Division by zero"));
    }

    private CalculatorResponseDTO createCalculationRequestDTO(final String equation,final double calculationResult,final String errorMsg){
        CalculatorResponseDTO request = new CalculatorResponseDTO(equation);
        request.setCalculationResult(calculationResult);
        request.setErrorMsg(errorMsg);
        return request;
    }

    private void setUpEntityManager() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("test-unit");
        manager = factory.createEntityManager();
    }

    private void setUpDao(final EntityManager entityManager) {
        equationDao=new EquationDaoImpl(entityManager);
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
