package com.calculator.webapp;

import com.calculator.core.CalculatorApp;
import com.calculator.core.exceptions.DivisionByZeroException;
import com.calculator.webapp.servletresponse.ServletError;
import com.calculator.webapp.servletresponse.ServletResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import javax.ws.rs.core.Response;


import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.hamcrest.MockitoHamcrest.argThat;


@RunWith(MockitoJUnitRunner.class)
public class CalculateRestResourceTest {

    @InjectMocks
    private CalculateRestResource resource;

    @Mock
    private CalculatorApp app;

    @Mock
    private ObjectMapper mapper;

    @Test
    public void doGetCalculationResult_LegalEquation_ExpectedResult() throws Exception {

        Mockito.when(app.calculateResult("1+1")).thenReturn(2.0);
        Mockito.when(mapper.writeValueAsString(argThat(instanceOf(ServletResult.class)))).thenReturn("\"result\":\"2.0\"");

        Response response=resource.doGetCalculationResult("1+1");
        String actual=(String)response.getEntity();

        assertThat(actual, containsString("\"result\":\"2.0\""));
    }

    @Test
    public void doGetCalculationResult_IllegalEquation_ErrorMessage() throws Exception
    {
        Mockito.when(app.calculateResult("1/0")).thenThrow(new DivisionByZeroException("Division by zero"));
        Mockito.when(mapper.writeValueAsString(argThat(instanceOf(ServletError.class)))).thenReturn("{\"errorCode\":400,\"message\":\"Division by zero\"}");

        Response response=resource.doGetCalculationResult("1/0");
        String actual=(String)response.getEntity();

        assertThat(actual, containsString("\"errorCode\":400"));
        assertThat(actual, containsString("\"message\":\"Division by zero\""));

    }
}