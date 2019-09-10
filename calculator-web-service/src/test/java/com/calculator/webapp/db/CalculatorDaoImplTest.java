package com.calculator.webapp.db;


import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.dbunit.util.fileloader.FlatXmlDataFileLoader;
import org.junit.*;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CalculatorDaoImplTest {

    @PersistenceContext(name="test-unit")
    private EntityManager manager;

    @Inject
    private CalculatorDaoImpl dao;
    private static IDatabaseTester databaseTester;

    @BeforeClass
    public static void setUpDB() throws Exception {
        //Connection connection = DriverManager.getConnection("jdbc:derby:memory://127.0.0.1:1527/calculator;create=true","root","root");
        databaseTester = new JdbcDatabaseTester("org.apache.derby.jdbc.EmbeddedDriver",
                "jdbc:derby:memory://127.0.0.1:1527/calculator;create=true","root","root");
    }

    @AfterClass
    public static void tearDownDB() throws Exception {
        databaseTester.getConnection().close();
    }

    @Before
    public void setUp() throws Exception
    {
        //dao=new CalculatorDaoImpl(manager);

        //IDataSet populatedDataSet=new FlatXmlDataSetBuilder().build(getClass().getResourceAsStream("/datasets/populatedDataSet.xml"));
        IDataSet populatedDataSet = new FlatXmlDataFileLoader().load("/datasets/populatedDataSet.xml");
        databaseTester.setDataSet(populatedDataSet);
        DatabaseOperation.CLEAN_INSERT.execute(databaseTester.getConnection(),populatedDataSet);
    }

    @Test
    public void getAllItems_populatedDataSet_() {
        //IDataSet expectedDataSet = new FlatXmlDataFileLoader().load("populatedDataSet.xml");
        List<CalculatorResponseDTO> actualItems = dao.getAllItems();
        assertEquals(2,actualItems.size());
    }

    @Ignore
    @Test
    public void getItem() {
        assert(true);
    }

    @Ignore
    @Test
    public void saveItem() {
        //CalculatorResponseDTO toSaveEntity = new CalculatorResponseDTO(true,"10/2","5.0");
        //CalculatorDaoImpl
    }

    @Ignore
    @Test
    public void deleteItem() {
    }
}