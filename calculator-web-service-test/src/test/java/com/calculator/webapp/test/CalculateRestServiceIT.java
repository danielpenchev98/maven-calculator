package com.calculator.webapp.test;

/*import com.calculator.webapp.test.pageobjects.dbclient.DatabasePage;
import com.calculator.webapp.test.pageobjects.webclient.ResourcePage;
import com.calculator.webapp.test.pageobjects.webclient.requestobjects.CalculationHistoryRequestUrl;
import com.calculator.webapp.test.pageobjects.webclient.requestobjects.CalculationResultRequestUrl;
import org.dbunit.dataset.ITable;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.importer.ZipImporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.json.JSONArray;
import org.junit.*;
import org.junit.runner.RunWith;


import javax.ws.rs.core.Response;
import java.io.File;
import java.net.URL;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(Arquillian.class)
public class CalculateRestServiceIT {

    protected static DatabasePage dbPage;
    protected ResourcePage resourcePage;
    @ArquillianResource
    protected URL baseUrl;


    @Deployment(testable = false)
    public static WebArchive createTestArchive(){
        WebArchive archive = ShrinkWrap.create(ZipImporter.class, "calculator.war")
                .importFrom(new File("target" + File.separator + "lib" + File.separator + "calculator-web-service-1.0-SNAPSHOT.war"))
                .as(WebArchive.class)
                .addAsResource("arquillian.xml")
                .addAsManifestResource("context.xml","context.xml");
        return archive;
    }

    @BeforeClass
    public static void setUpDB() throws Exception {
        dbPage = new DatabasePage();
    }

    @AfterClass
    public static void tearDownDB() throws Exception {
      dbPage.closeDbConnection();
    }

    @Before
    public void setUp() throws Exception {
        dbPage.resetStateOfDatabase();
        dbPage.setInitialTableInDataBase(DatasetPaths.EMPTY_DATASET_PATH);
    }


    private static final String NAME_OF_TABLE = "calculator_responses";
    private static final String[] columnsToFilter = new String[]{"time_of_creation"};

    @Test
    public void doGetCalculationResult_legalExpression_saveInDatabase() throws Exception {
        ITable expected = dbPage.getFilteredTableFromDataset(NAME_OF_TABLE, DatasetPaths.LEGAL_EQUATION_DATASET_PATH, columnsToFilter);

        resourcePage.getResponseFromTheGeneratedPage(new CalculationResultRequestUrl(baseUrl, "((121/(10-(-1))))-(-89)"));

        ITable actual = dbPage.getFilteredTableFromDatabase(NAME_OF_TABLE, columnsToFilter);
        dbPage.verifyTableEquality(expected, actual);
    }

    @Test
    public void doGetCalculationResult_illegalExpression_saveInDatabase() throws Exception {
        ITable expected = dbPage.getFilteredTableFromDataset(NAME_OF_TABLE, DatasetPaths.ILLEGAL_EQUATION_DATASET_PATH, columnsToFilter);

        resourcePage.getResponseFromTheGeneratedPage(new CalculationResultRequestUrl(baseUrl, "(-1.0/0.001"));
        ITable actual = dbPage.getFilteredTableFromDatabase(NAME_OF_TABLE, columnsToFilter);
        dbPage.verifyTableEquality(expected, actual);

    }
    private static final int BAD_REQUEST = Response.Status.BAD_REQUEST.getStatusCode();
    private static final int OK = Response.Status.OK.getStatusCode();

    @Test
    public void doGetCalculationResult_legalExpression() throws Exception {
        Response response = resourcePage.getResponseFromTheGeneratedPage(new CalculationResultRequestUrl(baseUrl,"((121/(10-(-1))))-(-89)"));

        verifyResponseCode(response, OK);
        String calculationResult = response.readEntity(String.class);
        assertThat(calculationResult, containsString("\"result\":\"100.0\""));
    }

    @Test
    public void doGetCalculationResult_illegalExpression_missingBracket() throws Exception {
        Response response = resourcePage.getResponseFromTheGeneratedPage(new CalculationResultRequestUrl(baseUrl,"(-1.0/0.001"));

        verifyResponseCode(response, BAD_REQUEST);
        verifyCalculationErrorMessage(response, 400, "Missing or misplaced brackets");
    }

    @Test
    public void doGetCalculationResult_illegalExpression_sequentialComponents() throws Exception {
        Response response = resourcePage.getResponseFromTheGeneratedPage(new CalculationResultRequestUrl(baseUrl,"-1.0 2 + 3"));

        verifyResponseCode(response, BAD_REQUEST);
        verifyCalculationErrorMessage(response, 400, "Sequential components of the same type");
    }

    @Test
    public void doGetCalculationResult_illegalExpression_missingOperator() throws Exception {
        Response response = resourcePage.getResponseFromTheGeneratedPage(new CalculationResultRequestUrl(baseUrl,"1(-1.0)/2"));

        verifyResponseCode(response, BAD_REQUEST);
        verifyCalculationErrorMessage(response, 400, "Missing operator between a number and an opening bracket or a closing bracket and a number");
    }

    @Test
    public void doGetCalculationResult_illegalExpression_emptyEquation() throws Exception {
        Response response = resourcePage.getResponseFromTheGeneratedPage(new CalculationResultRequestUrl(baseUrl,"     "));

        verifyResponseCode(response, BAD_REQUEST);
        verifyCalculationErrorMessage(response, 400, "Empty equation");
    }

    @Test
    public void doGetCalculationResult_illegalExpression_emptyBrackets() throws Exception {
        Response response = resourcePage.getResponseFromTheGeneratedPage(new CalculationResultRequestUrl(baseUrl,"()"));

        verifyResponseCode(response, BAD_REQUEST);
        verifyCalculationErrorMessage(response, 400, "Empty brackets");
    }

    @Test
    public void doGetCalculationResult_illegalExpression_equationBeginningWithOperation() throws Exception {
        Response response = resourcePage.getResponseFromTheGeneratedPage(new CalculationResultRequestUrl(baseUrl,"*1/2+3"));

        verifyResponseCode(response, BAD_REQUEST);
        verifyCalculationErrorMessage(response, 400, "Scope of equation ending or beginning with an operator");
    }

    @Test
    public void doGetCalculationResult_illegalExpression_divisionByZero() throws Exception {
        Response response = resourcePage.getResponseFromTheGeneratedPage(new CalculationResultRequestUrl(baseUrl,"1/0"));

        verifyResponseCode(response, BAD_REQUEST);
        verifyCalculationErrorMessage(response, 400, "Division by zero");
    }

    @Test
    public void doGetCalculationResult_illegalExpression_unsupportedComponent() throws Exception {
        Response response = resourcePage.getResponseFromTheGeneratedPage(new CalculationResultRequestUrl(baseUrl,"1#3"));

        verifyResponseCode(response, BAD_REQUEST);
        verifyCalculationErrorMessage(response, 400, "Unsupported component :#");
    }

    @Ignore
    @Test
    public void doGetCalculationHistory_requestWholeHistory() throws Exception {
        dbPage.setInitialTableInDataBase(DatasetPaths.CALCULATION_HISTORY_DATASET_PATH);
        final int HISTORY_RECORDS_COUNT = 6;

        Response response = resourcePage.getResponseFromTheGeneratedPage(new CalculationHistoryRequestUrl(baseUrl));

        verifyResponseCode(response,OK);

        String responseBody = response.readEntity(String.class);
        JSONArray historyRecords = new JSONArray(responseBody);
        verifyNumberOfRecords(historyRecords,HISTORY_RECORDS_COUNT);

    }

    private void verifyNumberOfRecords(final JSONArray recordHistory, final int recordsCount) {
        assertThat(recordHistory.length(),is(recordsCount));
    }

    private void verifyResponseCode(final Response response, final int expectedCode) {
        assertThat(response.getStatus(), equalTo(expectedCode));
    }


    private void verifyCalculationErrorMessage(final Response response, final int expectedErrorCode, final String expectedMessage) {
        String actual=response.readEntity(String.class);

        assertThat(actual, containsString("\"errorCode\":" + expectedErrorCode));
        assertThat(actual, containsString("\"message\":\"" + expectedMessage + "\""));
    }
}*/


import com.calculator.webapp.test.pageobjects.dbclient.DatabasePage;
import com.calculator.webapp.test.pageobjects.webclient.ResourcePage;
import com.calculator.webapp.test.pageobjects.webclient.requestobjects.CalculationHistoryRequestUrl;
import com.calculator.webapp.test.pageobjects.webclient.requestobjects.CalculationResultRequestUrl;
import org.dbunit.dataset.ITable;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.importer.ZipImporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.json.JSONArray;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ws.rs.core.Response;
import java.io.File;
import java.net.URL;


public class CalculateRestServiceIT {


    protected static DatabasePage dbPage;

    @ArquillianResource
    protected URL baseUrl;

    protected ResourcePage resourcePage;

    @Deployment(testable = false)
    public static WebArchive createTestArchive() {
        WebArchive archive = ShrinkWrap.create(ZipImporter.class, "calculator.war")
                .importFrom(new File("target" + File.separator + "lib" + File.separator + "calculator-web-service-1.0-SNAPSHOT.war"))
                .as(WebArchive.class)
                .addAsResource("arquillian.xml")
                .addAsManifestResource("context.xml", "context.xml");
        return archive;
    }

    @BeforeClass
    public static void setUpDB() throws Exception {
        dbPage = new DatabasePage();
    }

    @AfterClass
    public static void tearDownDB() throws Exception {
        dbPage.closeDbConnection();
    }

    @Before
    public void setUp() throws Exception{
        resourcePage = new ResourcePage();
        dbPage.resetStateOfDatabase();
        dbPage.setInitialTableInDataBase(DatasetPaths.EMPTY_DATASET_PATH);
    }


}


