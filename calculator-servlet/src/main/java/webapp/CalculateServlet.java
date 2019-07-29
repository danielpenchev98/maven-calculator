package webapp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@WebServlet("/CalculateServlet")
public class CalculateServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String equation=request.getParameter("equation");

        response.setContentType("text/html");
        PrintWriter out=response.getWriter();

        out.println("<html><body>");
        out.println(equation);
        out.println("</body></html>");
    }

}
