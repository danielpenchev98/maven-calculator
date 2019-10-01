package com.calculator.webapp.restresources;

import com.calculator.core.CalculatorApp;
import com.calculator.core.exceptions.BadInputException;
import com.calculator.webapp.db.dao.CalculatorDaoImpl;
import com.calculator.webapp.db.dto.CalculatorResponseDTO;
import com.calculator.webapp.restresponse.CalculationError;
import com.calculator.webapp.restresponse.CalculationResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.List;

@Path("/calculator")
@Produces(MediaType.APPLICATION_JSON)
public class CalculatorRestResource {

    private static final Logger logger = LogManager.getLogger(CalculatorRestResource.class);
    private final ObjectMapper mapper;
    private final CalculatorApp calculator;
    private final CalculatorDaoImpl dao;

    @Inject
    public CalculatorRestResource(final ObjectMapper mapper, final CalculatorApp calculator, final CalculatorDaoImpl dao) {
        this.mapper=mapper;
        this.calculator=calculator;
        this.dao=dao;
    }

    //TODO during the execution of the rest service to log the sql statements - currently isnt working

    @GET
    @Path("/calculate")
    public Response doGetCalculationResult(@NotNull @QueryParam("equation") String equation) throws JsonProcessingException {
        String responseMsg="";
        try {
            responseMsg = getCalculationResult(equation);
            return getSuccessfulRequestResponse(responseMsg);
        } catch (BadInputException badInput) {
            logger.warn("User input error",badInput);
            responseMsg=badInput.getMessage();
            return getErrorRequestResponse(Response.Status.BAD_REQUEST,responseMsg);
        } catch (Exception ex) {
            logger.error("Problem with service :\n", ex);
            responseMsg=ex.getMessage();
            return getErrorRequestResponse(Response.Status.INTERNAL_SERVER_ERROR,responseMsg);
        }
        finally {
            saveCalculationResponse(equation,responseMsg);
        }
    }

    private Response getSuccessfulRequestResponse(final String responseMsg) throws JsonProcessingException {
        CalculationResult calculationResult = new CalculationResult(responseMsg);
        return createResponse(Response.Status.OK,calculationResult);
    }

    private Response getErrorRequestResponse(final Response.Status status, final String errorMsg) throws JsonProcessingException {
        CalculationError errorResponseBody = new CalculationError(status.getStatusCode(),errorMsg);
        return createResponse(status,errorResponseBody);
    }


    private Response createResponse(final Response.Status status,final Object responseBody) throws JsonProcessingException {
        String jsonMsgBody = mapper.writeValueAsString(responseBody);
        return  Response.status(status).entity(jsonMsgBody).build();
    }

    @GET
    @Path("/calculationHistory")
    public Response doGetCalculationHistory() throws JsonProcessingException {
        List<CalculatorResponseDTO> calculationHistory=dao.getAllItems();
        return createResponse(Response.Status.OK,calculationHistory);
    }

    private void saveCalculationResponse(final String equation, final String responseMsg) {
        Date currentTime = new Date();
        CalculatorResponseDTO test = new CalculatorResponseDTO(equation,responseMsg,currentTime);
        dao.saveItem(test);
    }

    private String getCalculationResult(final String equation) throws Exception {
        if (equation == null) {
            throw new BadInputException("Equation parameter is missing from URL");
        }

        return String.valueOf(calculator.calculateResult(equation));
    }
}
