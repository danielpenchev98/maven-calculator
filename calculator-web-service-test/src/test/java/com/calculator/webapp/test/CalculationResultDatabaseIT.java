package com.calculator.webapp.test;

import com.calculator.webapp.test.pageobjects.webclient.exception.CalculatorRestException;
import org.dbunit.dataset.ITable;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.*;
import org.junit.runner.RunWith;

import javax.ws.rs.BadRequestException;


@RunWith(Arquillian.class)
public class CalculationResultDatabaseIT extends RestResourceIT {

    private static final String NAME_OF_TABLE = "calculator_responses";
    private static final String[] columnsToFilter = new String[]{"time_of_creation"};


    @Test
    public void doGetCalculationResult_legalExpression_saveInDatabase() throws Exception {
        ITable expected = dbPage.getFilteredTableFromDataset(NAME_OF_TABLE, DatasetPaths.LEGAL_EQUATION_DATASET_PATH, columnsToFilter);

        calculatorPage.calculate("((121/(10-(-1))))-(-89)");

        ITable actual = dbPage.getFilteredTableFromDatabase(NAME_OF_TABLE, columnsToFilter);
        dbPage.verifyTableEquality(expected, actual);
    }

    @Test
    public void doGetCalculationResult_illegalExpression_saveInDatabase() throws Exception {
        expectedException.expect(CalculatorRestException.class);

        ITable expected = dbPage.getFilteredTableFromDataset(NAME_OF_TABLE, DatasetPaths.ILLEGAL_EQUATION_DATASET_PATH, columnsToFilter);

        calculatorPage.calculate("(-1.0/0.001");

        ITable actual = dbPage.getFilteredTableFromDatabase(NAME_OF_TABLE, columnsToFilter);
        dbPage.verifyTableEquality(expected, actual);

    }

}
