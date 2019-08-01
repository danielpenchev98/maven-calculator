package webapp;

import com.calculator.core.CalculatorApp;
import com.calculator.core.exceptions.DivisionByZeroException;
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
        servlet=new CalculateServlet(app);
    }

    @Test
    public void doGet_ResultOfCalculation() throws Exception {


        String equation="1+1";
        Mockito.when(request.getParameter("equation")).thenReturn(equation);
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        Mockito.when(response.getWriter()).thenReturn(writer);
        Mockito.when(app.calculateResult(equation)).thenReturn(2.0);

        servlet.doGet(request,response);

        writer.flush();
        assertTrue(stringWriter.toString().contains("2"));
    }

    @Test
    public void doGet_ErrorMessage() throws Exception
    {
        String equation="1/0";
        Mockito.when(request.getParameter("equation")).thenReturn(equation);
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        Mockito.when(response.getWriter()).thenReturn(writer);
        Mockito.when(app.calculateResult(equation)).thenThrow(new DivisionByZeroException("Division on zero"));

        servlet.doGet(request,response);
        writer.flush();
        assertTrue(stringWriter.toString().contains("Division on zero"));
    }



}