package com.calculator.webapp.test;

import com.calculator.webapp.test.pageobjects.webclient.exception.CalculatorRestException;
import org.dbunit.dataset.ITable;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.*;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class CalculationResultDatabaseIT extends RestResourceIT {

    private static final String EXPRESSIONS_TABLE_NAME = "calculator_expressions";
    private static final String REQUESTS_TABLE_NAME = "calculation_requests";

    private static final String[] COLUMNS_TO_FILTER = new String[]{"time_of_creation"};

    @Test
    public void doGetCalculationResult_legalExpression_saveInDatabase() throws Exception {
        ITable expectedExpressionsTable = dbPage.getTableFromDataset(EXPRESSIONS_TABLE_NAME, DatasetPaths.LEGAL_DATASET_PATH);
        ITable expectedRequestsTable = dbPage.getFilteredTableFromDataset(REQUESTS_TABLE_NAME,DatasetPaths.LEGAL_DATASET_PATH,COLUMNS_TO_FILTER);

        calculationResultPage.calculate("((121/(10-(-1))))-(-89)");

        ITable actualExpressionsTable = dbPage.getTableFromDatabase(EXPRESSIONS_TABLE_NAME);
        ITable actualRequestsTable = dbPage.getFilteredTableFromDatabase(REQUESTS_TABLE_NAME,COLUMNS_TO_FILTER);

        dbPage.verifyTableEquality(expectedExpressionsTable, actualExpressionsTable);
        dbPage.verifyTableEquality(expectedRequestsTable,actualRequestsTable);
    }

    @Test
    public void doGetCalculationResult_illegalExpression_saveInDatabase() throws Exception {
        ITable expectedExpressionsTable = dbPage.getTableFromDataset(EXPRESSIONS_TABLE_NAME, DatasetPaths.ILLEGAL_DATASET_PATH);
        ITable expectedRequestsTable = dbPage.getFilteredTableFromDataset(REQUESTS_TABLE_NAME,DatasetPaths.ILLEGAL_DATASET_PATH,COLUMNS_TO_FILTER);
        expectedException.expect(CalculatorRestException.class);

        try {
            calculationResultPage.calculate("(-1.0/0.001");
        }
        finally {
            ITable actualExpressionsTable = dbPage.getTableFromDatabase(EXPRESSIONS_TABLE_NAME);
            ITable actualRequestsTable = dbPage.getFilteredTableFromDatabase(REQUESTS_TABLE_NAME,COLUMNS_TO_FILTER);

            dbPage.verifyTableEquality(expectedExpressionsTable, actualExpressionsTable);
            dbPage.verifyTableEquality(expectedRequestsTable,actualRequestsTable);
        }

    }
}
