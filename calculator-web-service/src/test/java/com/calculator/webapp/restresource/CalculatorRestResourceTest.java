package com.calculator.webapp.restresource;

import com.calculator.core.CalculatorApp;
import com.calculator.core.exceptions.DivisionByZeroException;
import com.calculator.webapp.db.dao.CalculatorDaoImpl;
import com.calculator.webapp.db.dto.CalculatorResponseDTO;
import com.calculator.webapp.restresources.CalculatorRestResource;
import com.calculator.webapp.restresponse.CalculationError;
import com.calculator.webapp.restresponse.CalculationResult;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    //TODO it be mocked or not ??????????
    private final ObjectMapper mapper = new ObjectMapper();

    @Mock
    private CalculatorApp app;

    @Mock
    private CalculatorDaoImpl dao;

    @Before
    public void setUp() {
        resource = new CalculatorRestResource(mapper,app,dao);
    }

    @Test
    public void doGetCalculationResult_LegalEquation_ExpectedResult() throws Exception {
        CalculationResult result = new CalculationResult("2.0");
        String expectedResponseBody = mapper.writeValueAsString(result);

        Mockito.when(app.calculateResult("1+1")).thenReturn(2.0);

        Response response=resource.doGetCalculationResult("1+1");
        String actual = (String)response.getEntity();

        verifyResponseCode(response,Response.Status.OK.getStatusCode());
        assertThat(actual, is(expectedResponseBody));
    }

    @Test
    public void doGetCalculationResult_IllegalEquation_ErrorMessage() throws Exception {
        CalculationError userError = new CalculationError(Response.Status.BAD_REQUEST.getStatusCode(),"Division by zero");
        String expectedResponseBody = mapper.writeValueAsString(userError);

        Mockito.when(app.calculateResult("1/0")).thenThrow(new DivisionByZeroException("Division by zero"));

        Response response=resource.doGetCalculationResult("1/0");
        String actualResponseBody=(String)response.getEntity();

        verifyResponseCode(response,Response.Status.BAD_REQUEST.getStatusCode());
        assertThat(actualResponseBody, is(expectedResponseBody));
    }

    @Test
    public void doGetCalculatorResult_MissingParameterInTheUrl_ServerError() throws Exception {
        CalculationError urlError = new CalculationError(Response.Status.BAD_REQUEST.getStatusCode(),"Equation parameter is missing from URL");
        String expectedResponseBody = mapper.writeValueAsString(urlError);

        Response response=resource.doGetCalculationResult(null);
        String actualResponseBody=(String)response.getEntity();

        verifyResponseCode(response,Response.Status.BAD_REQUEST.getStatusCode());
        verifyResponseBody(expectedResponseBody,actualResponseBody);
    }

    @Test
    public void doGetCalculationHistory_ExpectedResponseObject() throws Exception {
        List<CalculatorResponseDTO> history = new ArrayList<>();
        String expected = mapper.writeValueAsString(history);

        Mockito.when(dao.getAllItems()).thenReturn(new ArrayList<>());

        Response response=resource.doGetCalculationHistory();
        String actual=(String)response.getEntity();

        assertThat(response.getStatus(),is(Response.Status.OK.getStatusCode()));
        assertThat(actual, is(expected));
    }

    private void verifyResponseCode(final Response actualResponse, final int expectedStatusCode){
        assertThat(actualResponse.getStatus(),is(expectedStatusCode));
    }

    private void verifyResponseBody(final String expectedResponseBody, final String actualResponseBody) {
        assertThat(actualResponseBody, is(expectedResponseBody));
    }

}
