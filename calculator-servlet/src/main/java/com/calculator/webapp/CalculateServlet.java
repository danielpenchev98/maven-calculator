package com.calculator.webapp;

import com.calculator.core.CalculatorApp;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;


import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class CalculateServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(CalculateServlet.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws  IOException {

        String result;

        JSONObject jsonResult=new JSONObject();

        try{
            result=getCalculationResult(request);
            jsonResult.put("result",result);
        }
        catch (Exception ex)
        {
            logger.error("Problem with servlet :\n",ex);
            result=ex.getMessage();
            jsonResult.put("error",ex.getMessage());
            //jsonResult.put("error",HttpServletResponse.SC_BAD_REQUEST);
            //jsonResult.put("message",ex.getMessage());
        }
        printResponseInJSON(response,jsonResult);
    }

    private String getCalculationResult(final HttpServletRequest request) throws Exception
    {
        String getPathInfo = request.getPathInfo();
        String equation=getPathInfo.substring(1);

        CalculatorApp calculator=getCalculator();
        return String.valueOf(calculator.calculateResult(equation));

    }

    private void printResponseInJSON(final HttpServletResponse response,final JSONObject jsonResult) throws IOException
    {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print(jsonResult);
        out.flush();
    }

    protected CalculatorApp getCalculator()
    {
        return new CalculatorApp();
    }
}
