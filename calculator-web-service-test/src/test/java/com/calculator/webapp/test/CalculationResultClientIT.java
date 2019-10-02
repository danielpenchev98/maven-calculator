package com.calculator.webapp.test;



import com.calculator.webapp.test.pageobjects.webclient.CalculatorResponseDTO;
import com.calculator.webapp.test.pageobjects.webclient.CalculatorResultPojo;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ws.rs.BadRequestException;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(Arquillian.class)
public class CalculationResultClientIT extends RestResourceIT {

    @Test
    public void doGetCalculationResult_legalExpression() throws Exception {
        CalculatorResultPojo expectedResult = new CalculatorResultPojo("100.0");
        CalculatorResultPojo actualResult = calculatorPage.calculate("((121/(10-(-1))))-(-89)");

        verifyCalculationResult(expectedResult,actualResult);
    }

    @Test
    public void doGetCalculationResult_illegalExpression_missingBracket() throws Exception {
        expectedException.expect(BadRequestException.class);
        expectedException.expectMessage("Missing or misplaced brackets");

        calculatorPage.calculate("(-1.0/0.001");
    }

    @Test
    public void doGetCalculationResult_illegalExpression_sequentialComponents() throws Exception {
        expectedException.expect(BadRequestException.class);
        expectedException.expectMessage("Sequential components of the same type");

        calculatorPage.calculate("-1.0 2 + 3");
    }

    @Test
    public void doGetCalculationResult_illegalExpression_missingOperator() throws Exception {
        expectedException.expect(BadRequestException.class);
        expectedException.expectMessage("Missing operator between a number and an opening bracket or a closing bracket and a number");

        calculatorPage.calculate("1(-1.0)/2");
    }

    @Test
    public void doGetCalculationResult_illegalExpression_emptyEquation() throws Exception {
        expectedException.expect(BadRequestException.class);
        expectedException.expectMessage("Empty equation");

        calculatorPage.calculate("     ");
    }

    @Test
    public void doGetCalculationResult_illegalExpression_emptyBrackets() throws Exception {
        expectedException.expect(BadRequestException.class);
        expectedException.expectMessage("Empty brackets");

        calculatorPage.calculate("()");
    }

    @Test
    public void doGetCalculationResult_illegalExpression_equationBeginningWithOperation() throws Exception {
        expectedException.expect(BadRequestException.class);
        expectedException.expectMessage("Scope of equation ending or beginning with an operator");

        calculatorPage.calculate("*1/2+3");
    }

    @Test
    public void doGetCalculationResult_illegalExpression_divisionByZero() throws Exception {
        expectedException.expect(BadRequestException.class);
        expectedException.expectMessage("Division by zero");

        calculatorPage.calculate("1/0");
    }

    @Test
    public void doGetCalculationResult_illegalExpression_unsupportedComponent() throws Exception {
        expectedException.expect(BadRequestException.class);
        expectedException.expectMessage("Unsupported component :#");

        calculatorPage.calculate("1#3");
    }

    @Test
    public void doGetCalculationHistory_requestWholeHistory() throws Exception {
        dbPage.setInitialTableInDataBase(DatasetPaths.CALCULATION_HISTORY_DATASET_PATH);
        final int HISTORY_RECORDS_COUNT = 6;

        List<CalculatorResponseDTO> history = calculatorPage.getCalculationHistory();

        assertThat(history.size(),is(HISTORY_RECORDS_COUNT));
    }


    private void verifyCalculationResult(final CalculatorResultPojo expectedResult,final CalculatorResultPojo actualResult) {
        assertThat(actualResult.getResult(),is(expectedResult.getResult()));
    }

}
