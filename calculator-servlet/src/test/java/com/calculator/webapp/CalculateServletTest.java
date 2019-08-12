package com.calculator.webapp;

import com.calculator.core.CalculatorApp;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class CalculateServletTest {

    private CalculateServlet servlet;

    @Mock
    private CalculatorApp app;

    @Mock
    private HttpServletResponse response;

    @Mock
     private HttpServletRequest request;

    @Before
    public void setUp()
    {
        servlet=new CalculateServlet(){
            @Override
            protected CalculatorApp getCalculator() {
                return app;
            }
        };
    }

    @Test
    public void doGet_ResultOfCalculationTest() throws Exception {

        String equation="1+1";

       String expected="{\"result\":\"2.0\"}";

        Mockito.when(request.getPathInfo()).thenReturn("/"+equation);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);

        Mockito.when(response.getWriter()).thenReturn(writer);
        Mockito.when(app.calculateResult(equation)).thenReturn(2.0);

        servlet.doGet(request,response);

        assertEquals(expected,stringWriter.toString());
        Mockito.verify(response).setContentType("application/json");
    }

    @Test
    public void doGet_ErrorMessage() throws Exception
    {
        String equation="1/0";
        String expected="{\"error\":\"Division by zero\"}";

        Mockito.when(request.getPathInfo()).thenReturn("/"+equation);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);

        Mockito.when(response.getWriter()).thenReturn(writer);
        Mockito.when(app.calculateResult(equation)).thenThrow(new Exception("Division by zero"));

        servlet.doGet(request,response);

        assertEquals(expected,stringWriter.toString());
        Mockito.verify(response).setContentType("application/json");
    }

}