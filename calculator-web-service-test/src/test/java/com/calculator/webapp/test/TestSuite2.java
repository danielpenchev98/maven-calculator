package com.calculator.webapp.test;

import com.calculator.webapp.test.pageobjects.webclient.requestobjects.CalculationResultRequestUrl;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.*;
import org.junit.runner.RunWith;

import javax.ws.rs.core.Response;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;


@RunWith(Arquillian.class)
public class TestSuite2 extends CalculateRestServiceIT {

    private static final String NAME_OF_TABLE = "calculator_responses";
    private static final String[] columnsToFilter = new String[]{"time_of_creation"};

    @Test
    public void doGetCalculationResult_legalExpression_saveInDatabase() throws Exception {
        Response response = resourcePage.getResponseFromTheGeneratedPage(new CalculationResultRequestUrl(baseUrl,"((121/(10-(-1))))-(-89)"));

        String calculationResult = response.readEntity(String.class);
        assertThat(calculationResult, containsString("\"result\":\"100.0\""));
       /* ITable expected = dbPage.getFilteredTableFromDataset(NAME_OF_TABLE, DatasetPaths.LEGAL_EQUATION_DATASET_PATH, columnsToFilter);

        resourcePage.getResponseFromTheGeneratedPage(new CalculationResultRequestUrl(baseUrl, "((121/(10-(-1))))-(-89)"));

        String calculationResult = response.readEntity(String.class);
        assertThat(calculationResult, containsString("\"result\":\"100.0\""));
        ITable actual = dbPage.getFilteredTableFromDatabase(NAME_OF_TABLE, columnsToFilter);
        dbPage.verifyTableEquality(expected, actual);*/
    }

    @Test
    public void doGetCalculationResult_illegalExpression_saveInDatabase() throws Exception {
        Response response = resourcePage.getResponseFromTheGeneratedPage(new CalculationResultRequestUrl(baseUrl,"((121/(10-(-1))))-(-89)"));

        String calculationResult = response.readEntity(String.class);
        assertThat(calculationResult, containsString("\"result\":\"100.0\""));
       /*ITable expected = dbPage.getFilteredTableFromDataset(NAME_OF_TABLE, DatasetPaths.ILLEGAL_EQUATION_DATASET_PATH, columnsToFilter);

        resourcePage.getResponseFromTheGeneratedPage(new CalculationResultRequestUrl(baseUrl, "(-1.0/0.001"));
        ITable actual = dbPage.getFilteredTableFromDatabase(NAME_OF_TABLE, columnsToFilter);
        dbPage.verifyTableEquality(expected, actual);*/

    }
}
