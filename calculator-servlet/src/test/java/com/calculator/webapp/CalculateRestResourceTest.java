package com.calculator.webapp;

import com.calculator.core.CalculatorApp;
import com.calculator.core.exceptions.DivisionByZeroException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import javax.ws.rs.core.Response;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;


@RunWith(MockitoJUnitRunner.class)
public class CalculateRestResourceTest {

    private CalculateRestResource resource;

    @Mock
    private CalculatorApp app;

    @Before
    public void setUp() {
        resource = new CalculateRestResource() {
            @Override
            protected CalculatorApp getCalculator() {
                return app;
            }
        };
    }


    @Test
    public void doGetCalculationResult_LegalEquation_ExpectedResult() throws Exception {

        Mockito.when(app.calculateResult("1+1")).thenReturn(2.0);

        Response response=resource.doGetCalculationResult("1+1");
        String actual=(String)response.getEntity();

        assertThat(actual, containsString("\"result\":\"2.0\""));
    }

    @Test
    public void doGetCalculationResult_IllegalEquation_ErrorMessage() throws Exception
    {
        Mockito.when(app.calculateResult("1/0")).thenThrow(new DivisionByZeroException("Division by zero"));

        Response response=resource.doGetCalculationResult("1/0");
        String actual=(String)response.getEntity();

        assertThat(actual, containsString("\"errorCode\":400"));
        assertThat(actual, containsString("\"message\":\"Division by zero\""));

    }
}