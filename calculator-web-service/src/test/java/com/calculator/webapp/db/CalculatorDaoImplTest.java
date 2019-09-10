package com.calculator.webapp.db;

import org.dbunit.Assertion;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.operation.DatabaseOperation;
import org.dbunit.util.fileloader.FlatXmlDataFileLoader;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.IsNull.notNullValue;

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

    @Test
    public void saveItem_emptyDataSet_tableSize1() throws Exception {
        IDataSet initialDataSet = getDataSet("/datasets/oneEntityDataSet.xml");
        uploadDataSet(initialDataSet);
        ITable expectedTable=getDataSet("/datasets/oneEntityDataSet.xml").getTable("calculator_responses");

        CalculatorResponseDTO response = new CalculatorResponseDTO();
        response.setLegitimacy(true);
        response.setEquation("1+1");
        response.setResponseMsg("2.0");
        Date currentDateTime=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2019-09-09 15:00:00");
        response.setDateOfCreation(currentDateTime);

        System.out.println(response.getId());
        dao.saveItem(response);

        ITable actualTable=getActualTable("calculator_responses");
        Assertion.assertEquals(expectedTable,actualTable);
    }

    @Ignore
    @Test
    public void deleteItem() {

    }

    private ITable getActualTable(final String tableName) throws DataSetException {
        return databaseTester.getDataSet().getTable(tableName);
    }

    private IDataSet getDataSet(final String path)
    {
        return new FlatXmlDataFileLoader().load(path);
    }

    private void uploadDataSet(final IDataSet wantedDataSet) throws Exception {
        databaseTester.setDataSet(wantedDataSet);
        DatabaseOperation.CLEAN_INSERT.execute(databaseTester.getConnection(),wantedDataSet);
    }
}