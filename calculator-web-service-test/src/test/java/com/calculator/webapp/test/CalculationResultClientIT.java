package com.calculator.webapp.test;

import org.jboss.arquillian.junit.Arquillian;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ws.rs.core.Response;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(Arquillian.class)
public class CalculationResultClientIT extends RestResourceIT {

    private static final int BAD_REQUEST = Response.Status.BAD_REQUEST.getStatusCode();
    private static final int OK = Response.Status.OK.getStatusCode();

    @Test
    public void doGetCalculationResult_legalExpression() throws Exception {
        Response response = calculatorPage.calculate("((121/(10-(-1))))-(-89)");

        verifyResponseCode(response, OK);
        verifySuccessfulResponseBody(response,"100.0");
    }

    @Test
    public void doGetCalculationResult_illegalExpression_missingBracket() throws Exception {
        Response response = calculatorPage.calculate("(-1.0/0.001");

        verifyResponseCode(response, BAD_REQUEST);
        verifyErrorResponseBody(response, 400, "Missing or misplaced brackets");
    }

    @Test
    public void doGetCalculationResult_illegalExpression_sequentialComponents() throws Exception {
        Response response = calculatorPage.calculate("-1.0 2 + 3");

        verifyResponseCode(response, BAD_REQUEST);
        verifyErrorResponseBody(response, 400, "Sequential components of the same type");
    }

    @Test
    public void doGetCalculationResult_illegalExpression_missingOperator() throws Exception {
        Response response = calculatorPage.calculate("1(-1.0)/2");

        verifyResponseCode(response, BAD_REQUEST);
        verifyErrorResponseBody(response, 400, "Missing operator between a number and an opening bracket or a closing bracket and a number");
    }

    @Test
    public void doGetCalculationResult_illegalExpression_emptyEquation() throws Exception {
        Response response = calculatorPage.calculate("     ");

        verifyResponseCode(response, BAD_REQUEST);
        verifyErrorResponseBody(response, 400, "Empty equation");
    }

    @Test
    public void doGetCalculationResult_illegalExpression_emptyBrackets() throws Exception {
        Response response = calculatorPage.calculate("()");

        verifyResponseCode(response, BAD_REQUEST);
        verifyErrorResponseBody(response, 400, "Empty brackets");
    }

    @Test
    public void doGetCalculationResult_illegalExpression_equationBeginningWithOperation() throws Exception {
        Response response = calculatorPage.calculate("*1/2+3");

        verifyResponseCode(response, BAD_REQUEST);
        verifyErrorResponseBody(response, 400, "Scope of equation ending or beginning with an operator");
    }

    @Test
    public void doGetCalculationResult_illegalExpression_divisionByZero() throws Exception {
        Response response = calculatorPage.calculate("1/0");

        verifyResponseCode(response, BAD_REQUEST);
        verifyErrorResponseBody(response, 400, "Division by zero");
    }

    @Test
    public void doGetCalculationResult_illegalExpression_unsupportedComponent() throws Exception {
        Response response = calculatorPage.calculate("1#3");
        verifyResponseCode(response, BAD_REQUEST);
        verifyErrorResponseBody(response, 400, "Unsupported component :#");
    }

    @Test
    public void doGetCalculationHistory_requestWholeHistory() throws Exception {
        dbPage.setInitialTableInDataBase(DatasetPaths.CALCULATION_HISTORY_DATASET_PATH);
        final int HISTORY_RECORDS_COUNT = 6;

        Response response = calculatorPage.getCalculationHistory();
        verifyResponseCode(response,OK);

        String responseBody = response.readEntity(String.class);

        JSONArray history = new JSONArray(responseBody);
        verifyNumberOfRecords(history,HISTORY_RECORDS_COUNT);
    }

    private void verifyNumberOfRecords(final JSONArray recordHistory, final int recordsCount) {
        assertThat(recordHistory.length(),is(recordsCount));
    }

    private void verifyResponseCode(final Response response, final int expectedCode) {
        assertThat(response.getStatus(), equalTo(expectedCode));
    }

    private void verifySuccessfulResponseBody(final Response response,final String calculationResult) {
        String responseBody = response.readEntity(String.class);
        JSONObject actual = new JSONObject(responseBody);

        assertThat(actual.getString("result"),is(calculationResult));
    }

    private void verifyErrorResponseBody(final Response response, final int expectedErrorCode, final String expectedMessage) {
        String responseBody = response.readEntity(String.class);
        JSONObject actual = new JSONObject(responseBody);

        assertThat(actual.getInt("errorCode"), is(expectedErrorCode));
        assertThat(actual.getString("message"), is(expectedMessage));
    }
}
