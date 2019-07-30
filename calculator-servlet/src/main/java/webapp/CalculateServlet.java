package webapp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@WebServlet(name="CalculateServlet", urlPatterns = "/CalculateServlet")
public class CalculateServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws  IOException {

        String equation=request.getParameter("equation");

        response.setContentType("text/html");
        PrintWriter out=response.getWriter();

        out.println("<html><body>");
        out.println("2");
        out.println("</body></html>");
    }

}
