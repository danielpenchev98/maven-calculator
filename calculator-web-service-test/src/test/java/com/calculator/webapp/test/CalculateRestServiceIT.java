package com.calculator.webapp.test;

import com.calculator.webapp.test.pageobjects.dbclient.DatabasePage;
import com.calculator.webapp.test.pageobjects.webclient.ResourcePage;
import org.dbunit.dataset.ITable;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.importer.ZipImporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ws.rs.core.Response;
import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.dbunit.Assertion.assertEquals;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(Arquillian.class)
public class CalculateRestServiceIT {

    private static final int BAD_REQUEST = Response.Status.BAD_REQUEST.getStatusCode();
    private static final int OK = Response.Status.OK.getStatusCode();

    private static final String emptyDatasetPath = "/datasets/emptyDataset.xml";
    private static final String validEquationEntityDatasetPath = "/datasets/validEquationEntityDataset.xml";
    private static final String divisionByZeroEntityDatasetPath = "/datasets/divisionByZeroEntityDataset.xml";
    private static final String emptyBracketsEntityDatasetPath = "/datasets/emptyBracketsEntityDataset.xml";
    private static final String emptyEquationEntityDatasetPath = "/datasets/emptyEquationEntityDataset.xml";
    private static final String equationBeginningWithOperatorEntityDatasetPath = "/datasets/equationBeginningWithOperatorEntityDataset.xml";
    private static final String missingBracketEntityDatasetPath = "/datasets/missingBracketEntityDataset.xml";
    private static final String missingOperatorEntityDatasetPath = "/datasets/missingOperatorEntityDataset.xml";
    private static final String sequentialComponentsEntityDatasetPath = "/datasets/sequentialComponentsEntityDataset.xml";
    private static final String unsupportedComponentEntityDatasetPath = "/datasets/unsupportedComponentEntityDataset.xml";
    private static final String calculationHistoryDatasetPath = "/datasets/calculationHistoryDataset.xml";
    private static final String NAME_OF_TABLE = "calculator_responses";

    private static DatabasePage dbPage;

    private static final String connectionUrl="jdbc:derby:memory:calculator;create=true";
    private static final String DBUsername="";
    private static final String DBPassword="";
    private static final String[] columnsToFilter = new String[]{"time_of_creation"};


    @ArquillianResource
    private URL url;

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
        dbPage = new DatabasePage(connectionUrl,DBUsername,DBPassword);
    }

    @AfterClass
    public static void tearDownDB() throws Exception {
       dbPage.closeDbConnection();
    }

    @Before
    public void setUp() {
        resourcePage = new ResourcePage(url);
    }

    @Test
    public void doGetCalculationResult_LegalExpression() throws Exception {
        dbPage.resetStateOfDatabase();
        dbPage.setInitialTableInDataBase(emptyDatasetPath);

        ITable expected = dbPage.getFilteredTableFromDataset(NAME_OF_TABLE,validEquationEntityDatasetPath,columnsToFilter);

        Response response = resourcePage.getResponseFromTheGeneratedPage("((121/(10-(-1))))-(-89)");
        verifyResponseCode(response, OK);

        String calculationResult = response.readEntity(String.class);
        assertThat(calculationResult, containsString("\"result\":\"100.0\""));

        ITable actual = dbPage.getFilteredTableFromDatabase(NAME_OF_TABLE,columnsToFilter);
        dbPage.verifyTableEquality(expected,actual);
    }

    @Test
    public void doGetCalculationResult_IllegalExpression_MissingBracket() throws Exception {
        dbPage.resetStateOfDatabase();
        dbPage.setInitialTableInDataBase(emptyDatasetPath);

        ITable expected = dbPage.getFilteredTableFromDataset(NAME_OF_TABLE,missingBracketEntityDatasetPath,columnsToFilter);

        Response response = resourcePage.getResponseFromTheGeneratedPage("(-1.0/0.001");
        verifyResponseCode(response, BAD_REQUEST);
        verifyCalculationErrorMessage(response, 400, "Missing or misplaced brackets");

        ITable actual = dbPage.getFilteredTableFromDatabase(NAME_OF_TABLE,columnsToFilter);
        dbPage.verifyTableEquality(expected,actual);
    }

    @Test
    public void doGetCalculationResult_IllegalExpression_SequentialComponents() throws Exception {
        dbPage.resetStateOfDatabase();
        dbPage.setInitialTableInDataBase(emptyDatasetPath);

        ITable expected = dbPage.getFilteredTableFromDataset(NAME_OF_TABLE,sequentialComponentsEntityDatasetPath,columnsToFilter);

        Response response = resourcePage.getResponseFromTheGeneratedPage("-1.0 2 + 3");

        verifyResponseCode(response, BAD_REQUEST);
        verifyCalculationErrorMessage(response, 400, "Sequential components of the same type");

        ITable actual = dbPage.getFilteredTableFromDatabase(NAME_OF_TABLE,columnsToFilter);
        dbPage.verifyTableEquality(expected,actual);
    }

    @Test
    public void doGetCalculationResult_IllegalExpression_MissingOperator() throws Exception {
        dbPage.resetStateOfDatabase();
        dbPage.setInitialTableInDataBase(emptyDatasetPath);

        ITable expected = dbPage.getFilteredTableFromDataset(NAME_OF_TABLE,missingOperatorEntityDatasetPath,columnsToFilter);

        Response response = resourcePage.getResponseFromTheGeneratedPage("1(-1.0)/2");

        verifyResponseCode(response, BAD_REQUEST);
        verifyCalculationErrorMessage(response, 400, "Missing operator between a number and an opening bracket or a closing bracket and a number");

        ITable actual = dbPage.getFilteredTableFromDatabase(NAME_OF_TABLE,columnsToFilter);
        dbPage.verifyTableEquality(expected,actual);
    }

    @Test
    public void doGetCalculationResult_IllegalExpression_EmptyEquation() throws Exception {
        dbPage.resetStateOfDatabase();
        dbPage.setInitialTableInDataBase(emptyDatasetPath);

        ITable expected = dbPage.getFilteredTableFromDataset(NAME_OF_TABLE,emptyEquationEntityDatasetPath,columnsToFilter);

        Response response = resourcePage.getResponseFromTheGeneratedPage("     ");

        verifyResponseCode(response, BAD_REQUEST);
        verifyCalculationErrorMessage(response, 400, "Empty equation");

        ITable actual = dbPage.getFilteredTableFromDatabase(NAME_OF_TABLE,columnsToFilter);
        dbPage.verifyTableEquality(expected,actual);
    }

    @Test
    public void doGetCalculationResult_IllegalExpression_EmptyBrackets() throws Exception {
        dbPage.resetStateOfDatabase();
        dbPage.setInitialTableInDataBase(emptyDatasetPath);

        ITable expected = dbPage.getFilteredTableFromDataset(NAME_OF_TABLE,emptyBracketsEntityDatasetPath,columnsToFilter);

        Response response = resourcePage.getResponseFromTheGeneratedPage("()");

        verifyResponseCode(response, BAD_REQUEST);
        verifyCalculationErrorMessage(response, 400, "Empty brackets");

        ITable actual = dbPage.getFilteredTableFromDatabase(NAME_OF_TABLE,columnsToFilter);
        dbPage.verifyTableEquality(expected,actual);
    }

    @Test
    public void doGetCalculationResult_IllegalExpression_EquationBeginningWithOperation() throws Exception {
        dbPage.resetStateOfDatabase();
        dbPage.setInitialTableInDataBase(emptyDatasetPath);

        ITable expected = dbPage.getFilteredTableFromDataset(NAME_OF_TABLE,equationBeginningWithOperatorEntityDatasetPath,columnsToFilter);

        Response response = resourcePage.getResponseFromTheGeneratedPage("*1/2+3");

        verifyResponseCode(response, BAD_REQUEST);
        verifyCalculationErrorMessage(response, 400, "Scope of equation ending or beginning with an operator");

        ITable actual = dbPage.getFilteredTableFromDatabase(NAME_OF_TABLE,columnsToFilter);
        dbPage.verifyTableEquality(expected,actual);
    }

    @Test
    public void doGetCalculationResult_IllegalExpression_DivisionByZero() throws Exception {
        dbPage.resetStateOfDatabase();
        dbPage.setInitialTableInDataBase(emptyDatasetPath);

        ITable expected = dbPage.getFilteredTableFromDataset(NAME_OF_TABLE,divisionByZeroEntityDatasetPath,columnsToFilter);

        Response response = resourcePage.getResponseFromTheGeneratedPage("1/0");

        verifyResponseCode(response, BAD_REQUEST);
        verifyCalculationErrorMessage(response, 400, "Division by zero");

        ITable actual = dbPage.getFilteredTableFromDatabase(NAME_OF_TABLE,columnsToFilter);
        dbPage.verifyTableEquality(expected,actual);
    }

    @Test
    public void doGetCalculationResult_IllegalExpression_UnsupportedComponent() throws Exception {
        dbPage.resetStateOfDatabase();
        dbPage.setInitialTableInDataBase(emptyDatasetPath);

        ITable expected = dbPage.getFilteredTableFromDataset(NAME_OF_TABLE,unsupportedComponentEntityDatasetPath,columnsToFilter);

        Response response = resourcePage.getResponseFromTheGeneratedPage("1#3");

        verifyResponseCode(response, BAD_REQUEST);
        verifyCalculationErrorMessage(response, 400, "Unsupported component :#");

        ITable actual = dbPage.getFilteredTableFromDatabase(NAME_OF_TABLE,columnsToFilter);
        dbPage.verifyTableEquality(expected,actual);
    }

    @Test
    public void doGetCalculationHistory_RequestWholeHistory() throws Exception {
        dbPage.resetStateOfDatabase();
        dbPage.setInitialTableInDataBase(calculationHistoryDatasetPath);


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

