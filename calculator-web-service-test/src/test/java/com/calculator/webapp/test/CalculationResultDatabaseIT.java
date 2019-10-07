package com.calculator.webapp.test;

import com.calculator.webapp.test.pageobjects.webclient.exception.CalculatorRestException;
import org.dbunit.dataset.ITable;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.*;
import org.junit.runner.RunWith;

import static junit.framework.TestCase.fail;


@RunWith(Arquillian.class)
public class CalculationResultDatabaseIT extends RestResourceIT {

    private static final String NAME_OF_TABLE = "calculator_responses";
    private static final String[] COLUMNS_TO_FILTER = new String[]{"time_of_creation"};


    @Test
    public void doGetCalculationResult_legalExpression_saveInDatabase() throws Exception {
        ITable expected = dbPage.getFilteredTableFromDataset(NAME_OF_TABLE, DatasetPaths.LEGAL_EQUATION_DATASET_PATH, COLUMNS_TO_FILTER);

        calculationResultPage.calculate("((121/(10-(-1))))-(-89)");

        ITable actual = dbPage.getFilteredTableFromDatabase(NAME_OF_TABLE, COLUMNS_TO_FILTER);
        dbPage.verifyTableEquality(expected, actual);
    }

    @Test
    public void doGetCalculationResult_illegalExpression_saveInDatabase() throws Exception {
        ITable expected = dbPage.getFilteredTableFromDataset(NAME_OF_TABLE, DatasetPaths.ILLEGAL_EQUATION_DATASET_PATH, COLUMNS_TO_FILTER);

        try {
            calculationResultPage.calculate("(-1.0/0.001");
        }
        catch(CalculatorRestException ex){
            ITable actual = dbPage.getFilteredTableFromDatabase(NAME_OF_TABLE, COLUMNS_TO_FILTER);
            dbPage.verifyTableEquality(expected, actual);
        }

        throw new Exception("No expected exception occurred");

    }
}
