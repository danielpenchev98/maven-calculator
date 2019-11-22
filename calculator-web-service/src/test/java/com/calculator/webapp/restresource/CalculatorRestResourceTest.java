package com.calculator.webapp.restresource;

import com.calculator.webapp.db.dao.ExpressionDaoImpl;
import com.calculator.webapp.db.dao.RequestDaoImpl;
import com.calculator.webapp.db.dao.exceptions.ItemDoesNotExistException;
import com.calculator.webapp.db.dto.RequestDTO;
import com.calculator.webapp.db.dto.ExpressionDTO;
import com.calculator.webapp.restresources.CalculatorRestResource;
import com.calculator.webapp.restresources.ExpressionRequestBody;
import com.calculator.webapp.restresponses.CalculationError;
import com.calculator.webapp.restresponses.RequestId;
import com.calculator.webapp.restresponses.CalculationResult;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import javax.ws.rs.core.Response;


import static com.calculator.webapp.db.dto.requeststatus.RequestStatus.COMPLETED;
import static com.calculator.webapp.db.dto.requeststatus.RequestStatus.PENDING;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class CalculatorRestResourceTest {

    private static final int OK = Response.Status.OK.getStatusCode();
    private static final int ACCEPTED = Response.Status.ACCEPTED.getStatusCode();
    private static final int BAD_REQUEST = Response.Status.BAD_REQUEST.getStatusCode();
    private static final int NOT_FOUND = Response.Status.NOT_FOUND.getStatusCode();

    private CalculatorRestResource resource;

    @Mock
    private RequestDaoImpl requestDao;

    @Mock
    private ExpressionDaoImpl expressionDao;

    @Mock
    private RequestDTO request;

    @Mock
    private ExpressionDTO expression;

    @Before
    public void setUp() {
        resource = new CalculatorRestResource(requestDao,expressionDao);
    }

    @Test
    public void queueCurrentRequest_idOfJob(){
        Response response=resource.queueCurrentRequest(new ExpressionRequestBody("1+1"));

        assertThat(response.getEntity(),is(instanceOf(RequestId.class)));
        verifyResponseCode(response,ACCEPTED);
    }

    @Test
    public void doGetCalculationResult_calculationFinished_successfulCalculation() throws Exception {
        Mockito.when(expression.getCalculationResult()).thenReturn(2.0);
        Mockito.when(request.getStatusCode()).thenReturn(COMPLETED.getStatusCode());

        CalculationResult expectedResult = new CalculationResult(2.0);
        Mockito.when(requestDao.getItem(1l)).thenReturn(request);
        Mockito.when(request.getExpression()).thenReturn("1+1");
        Mockito.when(expressionDao.getItem(request.getExpression())).thenReturn(expression);

        Response response=resource.doGetCalculationResult(1l);
        CalculationResult actualResult = (CalculationResult) response.getEntity();

        verifyResponseCode(response,OK);
        verifyCalculationResult(expectedResult,actualResult);
    }

    @Test
    public void doGetCalculationResult_calculationFinished_unsuccessfulCalculation() throws Exception {
        Mockito.when(expression.getErrorMsg()).thenReturn("Division by zero");
        Mockito.when(request.getStatusCode()).thenReturn(COMPLETED.getStatusCode());

        CalculationError expectedResult = new CalculationError(BAD_REQUEST,"Division by zero");
        Mockito.when(requestDao.getItem(1l)).thenReturn(request);
        Mockito.when(request.getExpression()).thenReturn("1/0");
        Mockito.when(expressionDao.getItem(request.getExpression())).thenReturn(expression);

        Response response=resource.doGetCalculationResult(1l);
        CalculationError actualResult = (CalculationError) response.getEntity();

        verifyResponseCode(response,BAD_REQUEST);
        verifyCalculationError(expectedResult,actualResult);
    }

    @Test
    public void doGetCalculationResult_CalculationPending() throws Exception {
        Mockito.when(request.getStatusCode()).thenReturn(PENDING.getStatusCode());

        Mockito.when(requestDao.getItem(1l)).thenReturn(request);

        Response response=resource.doGetCalculationResult(1l);

        assertThat(response.hasEntity(),is(false));
        verifyResponseCode(response,ACCEPTED);
    }

    @Test
    public void doGetCalculationResult_CalculationMissing() throws Exception {
        Mockito.when(requestDao.getItem(1l)).thenThrow(new ItemDoesNotExistException("Item not found"));

        Response response=resource.doGetCalculationResult(1l);

        assertThat(response.hasEntity(),is(false));
        verifyResponseCode(response,NOT_FOUND);
    }

    private void verifyResponseCode(final Response actualResponse, final int expectedStatusCode){
        assertThat(actualResponse.getStatus(),is(expectedStatusCode));
    }

    private void verifyCalculationResult(final CalculationResult expectedResponseBody, final CalculationResult actualResponseBody) {
       assertThat(actualResponseBody.getResult(),is(expectedResponseBody.getResult()));
    }

    private void verifyCalculationError(final CalculationError expectedError, final CalculationError actualError) {
        assertThat(actualError.getErrorCode(),is(expectedError.getErrorCode()));
        assertThat(actualError.getMessage(),is(expectedError.getMessage()));
    }

}