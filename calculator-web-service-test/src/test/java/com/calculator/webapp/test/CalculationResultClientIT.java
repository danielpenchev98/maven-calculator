package com.calculator.webapp.test;

import com.calculator.webapp.db.dto.CalculatorResponseDTO;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(Arquillian.class)
public class CalculationResultClientIT extends RestResourceIT {

    @Test
    public void doGetCalculationResult_legalExpression() throws Exception {
        testRestCalculation("((121/(10-(-1))))-(-89)","100.0");
    }

    @Test
    public void doGetCalculationResult_illegalExpression_missingBracket() throws Exception {
        testRestCalculation("(-1.0/0.001","Missing or misplaced brackets");
    }

    @Test
    public void doGetCalculationResult_illegalExpression_sequentialComponents() throws Exception {
        testRestCalculation("-1.0 2 + 3","Sequential components of the same type");
    }

    @Test
    public void doGetCalculationResult_illegalExpression_missingOperator() throws Exception {
        testRestCalculation("1(-1.0)/2","Missing operator between a number and an opening bracket or a closing bracket and a number");
    }

    @Test
    public void doGetCalculationResult_illegalExpression_emptyEquation() throws Exception {
        testRestCalculation("     ","Empty equation");
    }

    @Test
    public void doGetCalculationResult_illegalExpression_emptyBrackets() throws Exception {
        testRestCalculation("()","Empty brackets");
    }

    @Test
    public void doGetCalculationResult_illegalExpression_equationBeginningWithOperation() throws Exception {
        testRestCalculation("*1/2+3","Scope of equation ending or beginning with an operator");
    }

    @Test
    public void doGetCalculationResult_illegalExpression_divisionByZero() throws Exception {
        testRestCalculation("1/0","Division by zero");
    }

    @Test
    public void doGetCalculationResult_illegalExpression_unsupportedComponent() throws Exception {
       testRestCalculation("1#3","Unsupported component :#");
    }

    private void testRestCalculation(final String equation,final String responseMsg) throws Exception {
        CalculatorResponseDTO expectedResult = new CalculatorResponseDTO(equation,responseMsg,new Date());
        CalculatorResponseDTO actualResult = calculationResultPage.calculate(equation);

        verifyCalculationResult(expectedResult,actualResult);
    }

    @Test
    public void doGetCalculationHistory_requestWholeHistory() throws Exception {
        dbPage.setInitialTableInDataBase(DatasetPaths.CALCULATION_HISTORY_DATASET_PATH);
        final int HISTORY_RECORDS_COUNT = 6;

        List<CalculatorResponseDTO> history = calculationHistoryPage.getCalculationHistory();

        assertThat(history.size(),is(HISTORY_RECORDS_COUNT));
    }


    private void verifyCalculationResult(final CalculatorResponseDTO expectedResult,final CalculatorResponseDTO actualResult) {
        assertThat(actualResult.getEquation(),is(expectedResult.getEquation()));
        assertThat(actualResult.getResponseMsg(),is(expectedResult.getResponseMsg()));
    }

}
