package com.calculator.webapp.test;


import com.calculator.webapp.restresponses.CalculationResult;
import com.calculator.webapp.test.pageobjects.webclient.exception.CalculatorRestException;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(Arquillian.class)
public class CalculationResultClientIT extends RestResourceIT {

    @Test
    public void doGetCalculationResult_legalExpression() throws Exception {
        CalculationResult expectedResult = new CalculationResult(100.0);
        CalculationResult actualResult = calculationResultPage.calculate("((121/(10-(-1))))-(-89)");

        verifyCalculationResult(expectedResult,actualResult);
    }

    @Test
    public void doGetCalculationResult_illegalExpression_missingBracket() throws Exception {
        expectedException.expect(CalculatorRestException.class);
        expectedException.expectMessage("Missing or misplaced brackets");

        calculationResultPage.calculate("(-1.0/0.001");
    }

    @Test
    public void doGetCalculationResult_illegalExpression_sequentialComponents() throws Exception {
        expectedException.expect(CalculatorRestException.class);
        expectedException.expectMessage("Sequential components of the same type");

        calculationResultPage.calculate("-1.0 2 + 3");
    }

    @Test
    public void doGetCalculationResult_illegalExpression_missingOperator() throws Exception {
        expectedException.expect(CalculatorRestException.class);
        expectedException.expectMessage("Missing operator between a number and an opening bracket or a closing bracket and a number");

        calculationResultPage.calculate("1(-1.0)/2");
    }

    @Test
    public void doGetCalculationResult_illegalExpression_emptyExpression() throws Exception {
        expectedException.expect(CalculatorRestException.class);
        expectedException.expectMessage("Empty expression");

        calculationResultPage.calculate("     ");
    }

    @Test
    public void doGetCalculationResult_illegalExpression_emptyBrackets() throws Exception {
        expectedException.expect(CalculatorRestException.class);
        expectedException.expectMessage("Empty brackets");

        calculationResultPage.calculate("()");
    }

    @Test
    public void doGetCalculationResult_illegalExpression_expressionBeginningWithOperation() throws Exception {
        expectedException.expect(CalculatorRestException.class);
        expectedException.expectMessage("Scope of expression ending or beginning with an operator");

        calculationResultPage.calculate("*1/2+3");
    }

    @Test
    public void doGetCalculationResult_illegalExpression_divisionByZero() throws Exception {
        expectedException.expect(CalculatorRestException.class);
        expectedException.expectMessage("Division by zero");

        calculationResultPage.calculate("1/0");
    }

    @Test
    public void doGetCalculationResult_illegalExpression_unsupportedComponent() throws Exception {
        expectedException.expect(CalculatorRestException.class);
        expectedException.expectMessage("Unsupported component :#");

        calculationResultPage.calculate("1#3");
    }

    private void verifyCalculationResult(final CalculationResult  expectedResult,final CalculationResult actualResult) {
        assertThat(actualResult.getResult(),is(expectedResult.getResult()));
    }


}
