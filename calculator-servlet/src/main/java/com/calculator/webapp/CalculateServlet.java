package com.calculator.webapp;

import com.calculator.core.CalculatorApp;
import com.calculator.core.exceptions.BadInputException;
import com.calculator.webapp.servletresponse.ServletError;
import com.calculator.webapp.servletresponse.ServletResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class CalculateServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(CalculateServlet.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        Object resultObj;
        try {
            String result = getCalculationResult(request);
            resultObj = new ServletResult(result);
        } catch (BadInputException badInput) {
            resultObj = new ServletError(HttpServletResponse.SC_BAD_REQUEST, badInput.getMessage());
        } catch (Exception systemError) {
            logger.error("Problem with servlet :\n", systemError);
            resultObj = new ServletError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, systemError.getMessage());
        }

        //could put it in finally, but a possibility exists, where the ServletError could not be created
        printResponseInJSON(response, resultObj);
    }


    private String getCalculationResult(final HttpServletRequest request) throws Exception {
        String equation = request.getParameter("equation");
        if (equation == null) {
            throw new Exception("Equation parameter is missing");
        }

        CalculatorApp calculator = getCalculator();
        return String.valueOf(calculator.calculateResult(equation));

    }

    private void printResponseInJSON(final HttpServletResponse response, final Object resultObj) throws IOException {
        response.setContentType("application/json");

        String jsonResultObj = new ObjectMapper().writeValueAsString(resultObj);
        PrintWriter out = response.getWriter();

        out.print(jsonResultObj);
        out.flush();
    }

    protected CalculatorApp getCalculator() {
        return new CalculatorApp();
    }
}
