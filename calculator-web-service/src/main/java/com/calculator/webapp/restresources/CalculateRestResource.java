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
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

@Path("/calculator")
@Produces(MediaType.APPLICATION_JSON)
public class CalculateRestResource {

    private static final Logger logger = LogManager.getLogger(CalculateRestResource.class);
    private final ObjectMapper mapper;
    private final CalculatorApp calculator;
    //private final CalculatorDaoImpl dao;

    @Inject
    public CalculateRestResource(final ObjectMapper mapper,final CalculatorApp calculator/*,final CalculatorDaoImpl dao*/) {
        this.mapper=mapper;
        this.calculator=calculator;
       // this.dao=dao;
    }

    @GET
    @Path("/calculate")
    public Response doGetCalculationResult(@QueryParam("equation") String equation) throws JsonProcessingException {
        try {
            Connection conn =
                    DriverManager.getConnection("jdbc:mysql://localhost/world?" +
                            "user=root&password=root");
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
        finally {
           // CalculatorResponseDTO test=new CalculatorResponseDTO();
            //dao.saveItem(test);
        }
    }

    /*@GET
    @Path("/calculationHistory")
    public Response doGetCalculationHistory() throws JsonProcessingException {
        List<CalculatorResponseDTO> calculationHistory=dao.getAllItems();
        return createResponse(Response.Status.OK,calculationHistory);
    }*/

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
