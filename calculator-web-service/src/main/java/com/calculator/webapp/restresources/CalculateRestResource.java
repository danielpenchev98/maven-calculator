package com.calculator.webapp.restresources;

import com.calculator.core.CalculatorApp;
import com.calculator.core.exceptions.BadInputException;
import com.calculator.webapp.restresponse.CalculationError;
import com.calculator.webapp.restresponse.CalculationResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/calculate")
@Produces(MediaType.APPLICATION_JSON)
public class CalculateRestResource {

    private static final Logger logger = LogManager.getLogger(CalculateRestResource.class);

    private final ObjectMapper mapper;
    private final CalculatorApp calculator;

    @Inject
    public CalculateRestResource(final ObjectMapper mapper,final CalculatorApp calculator) {
        this.mapper=mapper;
        this.calculator=calculator;
    }


    @GET
    public Response doGetCalculationResult(@QueryParam("equation") String equation) throws JsonProcessingException {
        try {
            CalculationResult calculationResult = new CalculationResult(getCalculationResult(equation));
            return createResponse(Response.Status.OK, calculationResult);
        } catch (BadInputException badInput) {
            logger.warn("User input error",badInput);
            CalculationError userInputError = new CalculationError(Response.Status.BAD_REQUEST.getStatusCode(), badInput.getMessage());
            return createResponse(Response.Status.BAD_REQUEST, userInputError);
        } catch (Exception ex) {
            logger.error("Problem with service :\n", ex);
            CalculationError systemError = new CalculationError(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), ex.getMessage());
            return createResponse(Response.Status.INTERNAL_SERVER_ERROR, systemError);
        }
    }

    private Response createResponse(final Response.Status statusCode, final Object result) throws JsonProcessingException {
        String jsonResult = mapper.writeValueAsString(result);
        return Response.status(statusCode).entity(jsonResult).build();
    }

    private String getCalculationResult(final String equation) throws Exception {
        if (equation == null) {
            throw new BadInputException("Equation parameter is missing from URL");
        }

        return String.valueOf(calculator.calculateResult(equation));
    }
}
