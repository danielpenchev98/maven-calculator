package com.calculator.webapp.test;

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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

@RunWith(Arquillian.class)
public class CalculateRestServiceIT {

    private static final int BAD_REQUEST = Response.Status.BAD_REQUEST.getStatusCode();
    private static final int OK = Response.Status.OK.getStatusCode();


    private static final String NAME_OF_TABLE = "calculator_responses";
    private static final String[] columnsToFilter = new String[]{"time_of_creation"};

    private static DatabasePage dbPage;

    @ArquillianResource
    private URL baseUrl;

    private ResourcePage resourcePage;

    @Deployment(testable = false)
    public static WebArchive createTestArchive() {
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
    public void setUp() {
        resourcePage = new ResourcePage();
    }

    @Test
    public void doGetCalculationResult_LegalExpression() throws Exception {
        dbPage.resetStateOfDatabase();
        dbPage.setInitialTableInDataBase(DatasetPaths.EMPTY_DATASET_PATH);

        ITable expected = dbPage.getFilteredTableFromDataset(NAME_OF_TABLE,DatasetPaths.VALID_EQUATION_DATASET_PATH,columnsToFilter);

        Response response = resourcePage.getResponseFromTheGeneratedPage(new CalculationResultRequestUrl(baseUrl,"((121/(10-(-1))))-(-89)"));
        verifyResponseCode(response, OK);

        String calculationResult = response.readEntity(String.class);
        assertThat(calculationResult, containsString("\"result\":\"100.0\""));

        ITable actual = dbPage.getFilteredTableFromDatabase(NAME_OF_TABLE,columnsToFilter);
        dbPage.verifyTableEquality(expected,actual);
    }

    @Test
    public void doGetCalculationResult_IllegalExpression_MissingBracket() throws Exception {
        dbPage.resetStateOfDatabase();
        dbPage.setInitialTableInDataBase(DatasetPaths.EMPTY_DATASET_PATH);

        ITable expected = dbPage.getFilteredTableFromDataset(NAME_OF_TABLE,DatasetPaths.MISSING_BRACKET_DATASET_PATH,columnsToFilter);

        Response response = resourcePage.getResponseFromTheGeneratedPage(new CalculationResultRequestUrl(baseUrl,"(-1.0/0.001"));
        verifyResponseCode(response, BAD_REQUEST);
        verifyCalculationErrorMessage(response, 400, "Missing or misplaced brackets");

        ITable actual = dbPage.getFilteredTableFromDatabase(NAME_OF_TABLE,columnsToFilter);
        dbPage.verifyTableEquality(expected,actual);
    }

    @Test
    public void doGetCalculationResult_IllegalExpression_SequentialComponents() throws Exception {
        dbPage.resetStateOfDatabase();
        dbPage.setInitialTableInDataBase(DatasetPaths.EMPTY_DATASET_PATH);

        ITable expected = dbPage.getFilteredTableFromDataset(NAME_OF_TABLE,DatasetPaths.SEQUENTIAL_COMPONENTS_DATASET_PATH,columnsToFilter);

        Response response = resourcePage.getResponseFromTheGeneratedPage(new CalculationResultRequestUrl(baseUrl,"-1.0 2 + 3"));

        verifyResponseCode(response, BAD_REQUEST);
        verifyCalculationErrorMessage(response, 400, "Sequential components of the same type");

        ITable actual = dbPage.getFilteredTableFromDatabase(NAME_OF_TABLE,columnsToFilter);
        dbPage.verifyTableEquality(expected,actual);
    }

    @Test
    public void doGetCalculationResult_IllegalExpression_MissingOperator() throws Exception {
        dbPage.resetStateOfDatabase();
        dbPage.setInitialTableInDataBase(DatasetPaths.EMPTY_DATASET_PATH);

        ITable expected = dbPage.getFilteredTableFromDataset(NAME_OF_TABLE,DatasetPaths.MISSING_OPERATOR_DATASET_PATH,columnsToFilter);

        Response response = resourcePage.getResponseFromTheGeneratedPage(new CalculationResultRequestUrl(baseUrl,"1(-1.0)/2"));

        verifyResponseCode(response, BAD_REQUEST);
        verifyCalculationErrorMessage(response, 400, "Missing operator between a number and an opening bracket or a closing bracket and a number");

        ITable actual = dbPage.getFilteredTableFromDatabase(NAME_OF_TABLE,columnsToFilter);
        dbPage.verifyTableEquality(expected,actual);
    }

    @Test
    public void doGetCalculationResult_IllegalExpression_EmptyEquation() throws Exception {
        dbPage.resetStateOfDatabase();
        dbPage.setInitialTableInDataBase(DatasetPaths.EMPTY_DATASET_PATH);

        ITable expected = dbPage.getFilteredTableFromDataset(NAME_OF_TABLE,DatasetPaths.EMPTY_EQUATION_DATASET_PATH,columnsToFilter);

        Response response = resourcePage.getResponseFromTheGeneratedPage(new CalculationResultRequestUrl(baseUrl,"     "));

        verifyResponseCode(response, BAD_REQUEST);
        verifyCalculationErrorMessage(response, 400, "Empty equation");

        ITable actual = dbPage.getFilteredTableFromDatabase(NAME_OF_TABLE,columnsToFilter);
        dbPage.verifyTableEquality(expected,actual);
    }

    @Test
    public void doGetCalculationResult_IllegalExpression_EmptyBrackets() throws Exception {
        dbPage.resetStateOfDatabase();
        dbPage.setInitialTableInDataBase(DatasetPaths.EMPTY_DATASET_PATH);

        ITable expected = dbPage.getFilteredTableFromDataset(NAME_OF_TABLE,DatasetPaths.EMPTY_BRACKETS_DATASET_PATH,columnsToFilter);

        Response response = resourcePage.getResponseFromTheGeneratedPage(new CalculationResultRequestUrl(baseUrl,"()"));

        verifyResponseCode(response, BAD_REQUEST);
        verifyCalculationErrorMessage(response, 400, "Empty brackets");

        ITable actual = dbPage.getFilteredTableFromDatabase(NAME_OF_TABLE,columnsToFilter);
        dbPage.verifyTableEquality(expected,actual);
    }

    @Test
    public void doGetCalculationResult_IllegalExpression_EquationBeginningWithOperation() throws Exception {
        dbPage.resetStateOfDatabase();
        dbPage.setInitialTableInDataBase(DatasetPaths.EMPTY_DATASET_PATH);

        ITable expected = dbPage.getFilteredTableFromDataset(NAME_OF_TABLE,DatasetPaths.EQUATION_BEGINNING_WITH_OPERATOR_DATASET_PATH,columnsToFilter);

        Response response = resourcePage.getResponseFromTheGeneratedPage(new CalculationResultRequestUrl(baseUrl,"*1/2+3"));

        verifyResponseCode(response, BAD_REQUEST);
        verifyCalculationErrorMessage(response, 400, "Scope of equation ending or beginning with an operator");

        ITable actual = dbPage.getFilteredTableFromDatabase(NAME_OF_TABLE,columnsToFilter);
        dbPage.verifyTableEquality(expected,actual);
    }

    @Test
    public void doGetCalculationResult_IllegalExpression_DivisionByZero() throws Exception {
        dbPage.resetStateOfDatabase();
        dbPage.setInitialTableInDataBase(DatasetPaths.EMPTY_DATASET_PATH);

        ITable expected = dbPage.getFilteredTableFromDataset(NAME_OF_TABLE,DatasetPaths.DIVISION_BY_ZERO_DATASET_PATH,columnsToFilter);

        Response response = resourcePage.getResponseFromTheGeneratedPage(new CalculationResultRequestUrl(baseUrl,"1/0"));

        verifyResponseCode(response, BAD_REQUEST);
        verifyCalculationErrorMessage(response, 400, "Division by zero");

        ITable actual = dbPage.getFilteredTableFromDatabase(NAME_OF_TABLE,columnsToFilter);
        dbPage.verifyTableEquality(expected,actual);
    }

    @Test
    public void doGetCalculationResult_IllegalExpression_UnsupportedComponent() throws Exception {
        dbPage.resetStateOfDatabase();
        dbPage.setInitialTableInDataBase(DatasetPaths.EMPTY_DATASET_PATH);

        ITable expected = dbPage.getFilteredTableFromDataset(NAME_OF_TABLE,DatasetPaths.UNSUPPORTED_COMPONENT_ENTITY_DATASET_PATH,columnsToFilter);

        Response response = resourcePage.getResponseFromTheGeneratedPage(new CalculationResultRequestUrl(baseUrl,"1#3"));

        verifyResponseCode(response, BAD_REQUEST);
        verifyCalculationErrorMessage(response, 400, "Unsupported component :#");

        ITable actual = dbPage.getFilteredTableFromDatabase(NAME_OF_TABLE,columnsToFilter);
        dbPage.verifyTableEquality(expected,actual);
    }

    @Test
    public void doGetCalculationHistory_RequestWholeHistory() throws Exception {
        dbPage.resetStateOfDatabase();
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

}

