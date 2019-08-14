package com.calculator.webapp;

import com.calculator.core.CalculatorApp;
import com.calculator.core.exceptions.DivisionByZeroException;
import com.calculator.webapp.servletresponse.ServletError;
import com.calculator.webapp.servletresponse.ServletResult;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import javax.ws.rs.core.Response;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;


@RunWith(MockitoJUnitRunner.class)
public class CalculateRestServletTest {

    private CalculateRestServlet resource;

    @Mock
    private CalculatorApp app;

    @Before
    public void setUp() {
        resource = new CalculateRestServlet() {
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
        Object actual=response.getEntity();

        assertThat(actual,is(instanceOf(ServletResult.class)));

        String actualResult=((ServletResult)actual).getResult();
        assertThat(actualResult,is("2.0"));
    }

    @Test
    public void doGetCalculationResult_IllegalEquation_ErrorMessage() throws Exception
    {
        Mockito.when(app.calculateResult("1/0")).thenThrow(new DivisionByZeroException("Division by zero"));

        Response response=resource.doGetCalculationResult("1/0");
        Object actual=response.getEntity();

        assertThat(actual,is(instanceOf(ServletError.class)));

        int actualErrorCode=((ServletError)actual).getErrorCode();
        String actualMessage=((ServletError)actual).getMessage();

        assertThat(actualErrorCode,is(400));
        assertThat(actualMessage,is("Division by zero"));
    }
}