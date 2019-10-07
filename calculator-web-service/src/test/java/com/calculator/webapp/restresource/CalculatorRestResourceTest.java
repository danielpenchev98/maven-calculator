package com.calculator.webapp.restresource;

import com.calculator.core.CalculatorApp;
import com.calculator.core.exceptions.DivisionByZeroException;
import com.calculator.webapp.db.dao.CalculatorDaoImpl;
import com.calculator.webapp.db.dto.CalculatorResponseDTO;
import com.calculator.webapp.restresources.CalculatorRestResource;
import com.calculator.webapp.restresponse.CalculationError;
import com.calculator.webapp.restresponse.CalculationResult;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import javax.ws.rs.core.Response;


import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class CalculatorRestResourceTest {


    private CalculatorRestResource resource;

    @Mock
    private CalculatorApp mockedApp;

    @Mock
    private CalculatorDaoImpl mockedDao;

    @Before
    public void setUp() {
        resource = new CalculatorRestResource(mockedApp,mockedDao);
    }

    @Test
    public void doGetCalculationResult_LegalEquation_ExpectedResult() throws Exception {
        CalculationResult expectedResult = new CalculationResult("2.0");

        Mockito.when(mockedApp.calculateResult("1+1")).thenReturn(2.0);

        Response response=resource.doGetCalculationResult("1+1");
        CalculationResult actualResult = (CalculationResult) response.getEntity();

        verifyResponseCode(response,Response.Status.OK.getStatusCode());
        verifyCalculationResult(expectedResult,actualResult);
    }

    @Test
    public void doGetCalculationResult_IllegalEquation_ErrorMessage() throws Exception {
        CalculationError expectedUserError = new CalculationError(Response.Status.BAD_REQUEST.getStatusCode(),"Division by zero");

        Mockito.when(mockedApp.calculateResult("1/0")).thenThrow(new DivisionByZeroException("Division by zero"));

        Response response=resource.doGetCalculationResult("1/0");
        CalculationError actualUserError = (CalculationError) response.getEntity();

        verifyResponseCode(response,Response.Status.BAD_REQUEST.getStatusCode());
        verifyCalculationError(expectedUserError,actualUserError);
    }

    @Test
    public void doGetCalculatorResult_MissingParameterInTheUrl_ServerError(){
        CalculationError expectedUrlError = new CalculationError(Response.Status.BAD_REQUEST.getStatusCode(),"Equation parameter is missing from URL");

        Response response=resource.doGetCalculationResult(null);
        CalculationError actualUrlError = (CalculationError) response.getEntity();

        verifyResponseCode(response,Response.Status.BAD_REQUEST.getStatusCode());
        verifyCalculationError(expectedUrlError,actualUrlError);
    }

    @Test
    public void doGetCalculationHistory_ExpectedResponseObject(){
        List<CalculatorResponseDTO> expectedHistory = new ArrayList<>();

        Mockito.when(mockedDao.getAllItems()).thenReturn(new ArrayList<>());

        Response response=resource.doGetCalculationHistory();
        List<CalculatorResponseDTO> actualHistory = (List<CalculatorResponseDTO>) response.getEntity();

        verifyResponseCode(response,Response.Status.OK.getStatusCode());
        assertThat(actualHistory.size(),is(expectedHistory.size()));
    }

    private void verifyResponseCode(final Response actualResponse, final int expectedStatusCode){
        assertThat(actualResponse.getStatus(),is(expectedStatusCode));
    }

    private void verifyCalculationResult(final CalculationResult expectedResponseBody, final CalculationResult actualResponseBody) {
        assertThat(actualResponseBody.getResult(), is(expectedResponseBody.getResult()));
    }

    private void verifyCalculationError(final CalculationError expectedError, final CalculationError actualError) {
        assertThat(actualError.getErrorCode(),is(expectedError.getErrorCode()));
        assertThat(actualError.getMessage(), is(expectedError.getMessage()));
    }

}
