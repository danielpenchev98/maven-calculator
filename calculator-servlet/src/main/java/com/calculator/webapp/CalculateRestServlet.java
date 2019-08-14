package com.calculator.webapp;

import com.calculator.core.CalculatorApp;
import com.calculator.core.exceptions.BadInputException;
import com.calculator.webapp.servletresponse.ServletError;
import com.calculator.webapp.servletresponse.ServletResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    public Response doGetCalculationResult(@QueryParam("equation") String equation) throws JsonProcessingException
    {
        try {
            ServletResult calculationResult = new ServletResult(getCalculationResult(equation));
            return createResponse(Response.Status.OK,calculationResult);
        } catch (BadInputException badInput) {
            ServletError userInputError = new ServletError(BAD_REQUEST_CODE, badInput.getMessage());
            return createResponse(Response.Status.BAD_REQUEST,userInputError);
        } catch (Exception ex) {
            logger.error("Problem with servlet :\n", ex);
            ServletError systemError = new ServletError(INTERNAL_SERVER_CODE, ex.getMessage());
            return createResponse(Response.Status.INTERNAL_SERVER_ERROR,systemError);
        }
    }

    private Response createResponse(final Response.Status statusCode,final Object result) throws JsonProcessingException
    {
        ObjectMapper mapper=new ObjectMapper();
        String jsonResult=mapper.writeValueAsString(result);
        return Response.status(statusCode).entity(jsonResult).build();
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
