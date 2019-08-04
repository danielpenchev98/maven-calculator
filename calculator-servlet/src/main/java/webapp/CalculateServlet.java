package webapp;


import com.calculator.core.CalculatorApp;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@WebServlet(name="CalculateServlet", urlPatterns = "/CalculateServlet")
public class CalculateServlet extends HttpServlet {

    private final CalculatorApp calculator;

    public CalculateServlet()
    {
        this(new CalculatorApp());
    }

    CalculateServlet(final CalculatorApp calculator)
    {
        this.calculator=calculator;
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws  IOException {

        String equation = request.getParameter("equation");

        String result;
        try{
            result=String.valueOf(calculator.calculateResult(equation));
        }
        catch (Exception ex)
        {
            result=ex.getMessage();
        }

        printResponseInHTML(response,result);
    }

    private void printResponseInHTML(final HttpServletResponse response,final String result) throws IOException
    {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println(result);
        out.println("</body></html>");
        out.flush();
    }
}
