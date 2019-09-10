package com.calculator.webapp.db;

import org.dbunit.DatabaseUnitException;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.operation.DatabaseOperation;
import org.dbunit.util.fileloader.FlatXmlDataFileLoader;
import org.hamcrest.Matchers;
import org.junit.*;

import javax.persistence.*;
import java.text.ParseException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class CalculatorDaoImplTest {

    private static EntityManager manager;
    private final CalculatorDaoImpl dao = new CalculatorDaoImpl(manager);
    private static IDatabaseTester databaseTester;

    @BeforeClass
    public static void setUpDB() throws Exception {
        databaseTester = new JdbcDatabaseTester("org.apache.derby.jdbc.EmbeddedDriver",
                "jdbc:derby:memory://127.0.0.1:1527/calculator;create=true","root","root");
        EntityManagerFactory factory= Persistence.createEntityManagerFactory("test-unit");
         manager=factory.createEntityManager();
    }

    @AfterClass
    public static void tearDownDB() throws Exception {
        manager.close();
        databaseTester.getConnection().close();
    }

    @Test
    public void getAllItems_populatedDataSet_expectedSize() throws Exception {
        IDataSet initialDataSet = getDataSet("/datasets/populatedDataSet.xml");
        uploadDataSet(initialDataSet);

        final int expectedNumberOfEntities = 6;
        List<CalculatorResponseDTO> actualItems = dao.getAllItems();

        assertThat(actualItems.size(),is(equalTo(expectedNumberOfEntities)));
    }


    //should all properties be tested?
    @Test
    public void getItem_populatedDataSet_expectedItem() throws Exception {
        IDataSet initialDataSet = getDataSet("/datasets/populatedDataSet.xml");
        uploadDataSet(initialDataSet);

        CalculatorResponseDTO actualItem=dao.getItem(6L);

        assertThat(actualItem,is(notNullValue()));
        assertThat(actualItem.getId(),is(equalTo(6L)));
    }

    @Ignore
    @Test
    public void saveItem_emptyDataSet_tableSize1() {
        CalculatorResponseDTO response = new CalculatorResponseDTO();
        response.setLegitimacy(false);
        response.setEquation("1");
        response.setResponseMsg("1.0");

        dao.saveItem(response);
    }

    @Ignore
    @Test
    public void deleteItem() {

    }

    IDataSet getDataSet(final String path)
    {
        return new FlatXmlDataFileLoader().load(path);
    }

    void uploadDataSet(final IDataSet wantedDataSet) throws Exception {
        databaseTester.setDataSet(wantedDataSet);
        DatabaseOperation.CLEAN_INSERT.execute(databaseTester.getConnection(),wantedDataSet);
    }
}