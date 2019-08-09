package com.calculator.webapp;

import com.calculator.core.CalculatorApp;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class CalculateServlet extends HttpServlet {

    static final Logger logger = LogManager.getLogger(CalculateServlet.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws  IOException {

        String equation = request.getParameter("equation");
        CalculatorApp calculator=getCalculator();

        String result;
        try{
            result=String.valueOf(calculator.calculateResult(equation));
        }
        catch (Exception ex)
        {
            logger.error("Problem with servlet :\n",ex);
            result=ex.getMessage();
        }

        printResponseInJSON(response,result);
    }

    private void printResponseInJSON(final HttpServletResponse response,final String result) throws IOException
    {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.println(result);
        out.flush();
    }

    protected CalculatorApp getCalculator()
    {
        return new CalculatorApp();
    }
}
