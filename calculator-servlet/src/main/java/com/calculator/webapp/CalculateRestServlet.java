package com.calculator.webapp;

import com.calculator.core.CalculatorApp;
import com.calculator.core.exceptions.BadInputException;
import com.calculator.webapp.servletresponse.ServletError;
import com.calculator.webapp.servletresponse.ServletResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/calculation")
@Produces(MediaType.APPLICATION_JSON)
public class CalculateRestServlet {

    private static final Logger logger = LogManager.getLogger(CalculateRestServlet.class);

    private static final int BAD_REQUEST_CODE=400;
    private static final int INTERNAL_SERVER_CODE=500;

    @GET
    public Response doGetCalculationResult(@QueryParam("equation") String equation)
    {
        Response response;
        try {
            ServletResult calculationResult = new ServletResult(getCalculationResult(equation));
            response= Response.status(Response.Status.OK).entity(calculationResult).build();
        } catch (BadInputException badInput) {
            ServletError userInputError = new ServletError(BAD_REQUEST_CODE, badInput.getMessage());
            response=Response.status(Response.Status.BAD_REQUEST).entity(userInputError).build();
        } catch (Exception ex) {
            logger.error("Problem with servlet :\n", ex);
            ServletError systemError = new ServletError(INTERNAL_SERVER_CODE, ex.getMessage());
            response=Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(systemError).build();
        }
        return response;
    }


    private String getCalculationResult(final String equation) throws Exception {
        if (equation == null) {
            throw new Exception("Equation parameter is missing");
        }

        CalculatorApp calculator = getCalculator();
        return String.valueOf(calculator.calculateResult(equation));

    }

    protected CalculatorApp getCalculator()
    {
        return new CalculatorApp();
    }
}
