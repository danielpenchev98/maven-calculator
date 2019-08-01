package webapp;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CalculateServletTest {



    @Test
    public void doGet_1() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        Mockito.when(request.getParameter("equation")).thenReturn("2");
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        Mockito.when(response.getWriter()).thenReturn(writer);

        CalculateServlet servlet=new CalculateServlet();
        servlet.doGet(request,response);

        writer.flush();
        assertTrue(stringWriter.toString().contains("2"));
        Mockito.verify(request, times(1)).getParameter("equation");
    }

    @Test
    public void doGet_2() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        Mockito.when(request.getParameter("equation")).thenReturn("1/0");
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        Mockito.when(response.getWriter()).thenReturn(writer);

        CalculateServlet servlet=new CalculateServlet();
        servlet.doGet(request,response);

        writer.flush();
        assertTrue(stringWriter.toString().contains("2"));
        Mockito.verify(request, times(1)).getParameter("equation");
    }

}