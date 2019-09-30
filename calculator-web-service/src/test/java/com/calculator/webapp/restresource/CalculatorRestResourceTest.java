package com.calculator.webapp.restresource;

import com.calculator.core.CalculatorApp;
import com.calculator.core.exceptions.DivisionByZeroException;
import com.calculator.webapp.db.dao.CalculatorDaoImpl;
import com.calculator.webapp.restresources.CalculatorRestResource;
import com.calculator.webapp.restresponse.CalculationError;
import com.calculator.webapp.restresponse.CalculationResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import javax.ws.rs.core.Response;


import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.hamcrest.MockitoHamcrest.argThat;

@RunWith(MockitoJUnitRunner.class)
public class CalculatorRestResourceTest {

    @InjectMocks
    private CalculatorRestResource resource;

    @Mock
    private CalculatorApp app;

    @Mock
    private ObjectMapper mapper;

    @Mock
    private CalculatorDaoImpl dao;

    @Test
    public void doGetCalculationResult_LegalEquation_ExpectedResult() throws Exception {
        Mockito.when(app.calculateResult("1+1")).thenReturn(2.0);
        Mockito.when(mapper.writeValueAsString(argThat(instanceOf(CalculationResult.class)))).thenReturn("{\"result\":\"2.0\"}");

        Response response=resource.doGetCalculationResult("1+1");
        String actual=(String)response.getEntity();

        assertThat(response.getStatus(),is(Response.Status.OK.getStatusCode()));
        assertThat(actual, containsString("\"result\":\"2.0\""));
    }

    @Test
    public void doGetCalculationResult_IllegalEquation_ErrorMessage() throws Exception {
        Mockito.when(app.calculateResult("1/0")).thenThrow(new DivisionByZeroException("Division by zero"));
        Mockito.when(mapper.writeValueAsString(argThat(instanceOf(CalculationError.class)))).thenReturn("{\"errorCode\":400,\"message\":\"Division by zero\"}");

        Response response=resource.doGetCalculationResult("1/0");
        String actual=(String)response.getEntity();

        assertThat(response.getStatus(),is(Response.Status.BAD_REQUEST.getStatusCode()));
        assertThat(actual, containsString("\"errorCode\":400"));
        assertThat(actual, containsString("\"message\":\"Division by zero\""));
    }

    @Test
    public void doGetCalculatorResult_MissingParameterInTheUrl_ServerError() throws Exception {
        Mockito.when(mapper.writeValueAsString(argThat(instanceOf(CalculationError.class)))).thenReturn("{\"errorCode\":400,\"message\":\"Equation parameter is missing from URL\"}");
        Response response=resource.doGetCalculationResult(null);
        String actual=(String)response.getEntity();

        assertThat(response.getStatus(),is(Response.Status.BAD_REQUEST.getStatusCode()));
        assertThat(actual, containsString("\"errorCode\":400"));
        assertThat(actual, containsString("\"message\":\"Equation parameter is missing from URL\""));
    }

    @Test
    public void doGetCalculationHistory_ExpectedResponseObject() throws Exception {
        String jsonResult = "[]";

        Mockito.when(dao.getAllItems()).thenReturn(new ArrayList<>());
        Mockito.when(mapper.writeValueAsString(argThat((instanceOf(ArrayList.class))))).thenReturn(jsonResult);

        Response response=resource.doGetCalculationHistory();
        String actual=(String)response.getEntity();

        assertThat(response.getStatus(),is(Response.Status.OK.getStatusCode()));
        assertThat(actual, containsString(jsonResult));
    }

}