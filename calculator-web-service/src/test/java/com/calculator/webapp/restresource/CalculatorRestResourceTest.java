package com.calculator.webapp.restresource;

import com.calculator.webapp.db.dao.RequestDaoImpl;
import com.calculator.webapp.db.dao.exceptions.ItemDoesNotExistException;
import com.calculator.webapp.db.dto.CalculationRequestDTO;
import com.calculator.webapp.restresources.CalculatorRestResource;
import com.calculator.webapp.restresources.EquationRequestBody;
import com.calculator.webapp.restresponses.CalculationError;
import com.calculator.webapp.restresponses.RequestId;
import com.calculator.webapp.restresponses.CalculationResult;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import javax.ws.rs.core.Response;


import java.util.ArrayList;
import java.util.List;

import static com.calculator.webapp.db.dto.requeststatus.RequestStatus.COMPLETED;
import static com.calculator.webapp.db.dto.requeststatus.RequestStatus.PENDING;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class CalculatorRestResourceTest {
/*
    private static final int OK = Response.Status.OK.getStatusCode();
    private static final int ACCEPTED = Response.Status.ACCEPTED.getStatusCode();
    private static final int BAD_REQUEST = Response.Status.BAD_REQUEST.getStatusCode();
    private static final int NOT_FOUND = Response.Status.NOT_FOUND.getStatusCode();

    private CalculatorRestResource resource;

    @Mock
    private RequestDaoImpl mockedDao;

    @Mock
    private CalculationRequestDTO entity;

    @Before
    public void setUp() {
        resource = new CalculatorRestResource(mockedDao);
    }

    @Test
    public void queueCurrentRequest_idOfJob(){
        Response response=resource.queueCurrentRequest(new EquationRequestBody("1+1"));

        assertThat(response.getEntity(),is(instanceOf(RequestId.class)));
        verifyResponseCode(response,ACCEPTED);
    }

    @Test
    public void doGetCalculationResult_calculationFinished_successfulCalculation() throws Exception {
        Mockito.when(entity.getCalculationResult()).thenReturn(2.0);
        Mockito.when(entity.getStatusCode()).thenReturn(COMPLETED.getStatusCode());

        CalculationResult expectedResult = new CalculationResult(2.0);
        Mockito.when(mockedDao.getItem(1l)).thenReturn(entity);

        Response response=resource.doGetCalculationResult(1l);
        CalculationResult actualResult = (CalculationResult) response.getEntity();

        verifyResponseCode(response,OK);
        verifyCalculationResult(expectedResult,actualResult);
    }

    @Test
    public void doGetCalculationResult_calculationFinished_unsuccessfulCalculation() throws Exception {
        Mockito.when(entity.getErrorMsg()).thenReturn("Division by zero");
        Mockito.when(entity.getStatusCode()).thenReturn(COMPLETED.getStatusCode());

        CalculationError expectedResult = new CalculationError(BAD_REQUEST,"Division by zero");
        Mockito.when(mockedDao.getItem(1l)).thenReturn(entity);

        Response response=resource.doGetCalculationResult(1l);
        CalculationError actualResult = (CalculationError) response.getEntity();

        verifyResponseCode(response,BAD_REQUEST);
        verifyCalculationError(expectedResult,actualResult);
    }

    @Test
    public void doGetCalculationResult_CalculationPending() throws Exception {
        Mockito.when(entity.getStatusCode()).thenReturn(PENDING.getStatusCode());

        Mockito.when(mockedDao.getItem(1l)).thenReturn(entity);

        Response response=resource.doGetCalculationResult(1l);

        assertThat(response.hasEntity(),is(false));
        verifyResponseCode(response,ACCEPTED);
    }

    @Test
    public void doGetCalculationResult_CalculationMissing() throws Exception {
        Mockito.when(mockedDao.getItem(1l)).thenThrow(new ItemDoesNotExistException("Item not found"));

        Response response=resource.doGetCalculationResult(1l);

        assertThat(response.hasEntity(),is(false));
        verifyResponseCode(response,NOT_FOUND);
    }

    @Test
    public void doGetCalculationHistory_ExpectedResponseObject(){
        List<CalculationRequestDTO> expectedHistory = new ArrayList<>();

        Mockito.when(mockedDao.getAllItems()).thenReturn(new ArrayList<>());

        Response response=resource.doGetCalculationHistory();
        List<CalculationRequestDTO> actualHistory = (List<CalculationRequestDTO>) response.getEntity();

        verifyResponseCode(response,OK);
        assertThat(actualHistory.size(),is(expectedHistory.size()));
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

*/

}