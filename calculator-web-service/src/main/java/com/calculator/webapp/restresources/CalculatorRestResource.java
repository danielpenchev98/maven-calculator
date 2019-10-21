package com.calculator.webapp.restresources;

import com.calculator.core.CalculatorApp;
import com.calculator.webapp.db.dao.CalculatorDaoImpl;
import com.calculator.webapp.db.dao.exceptions.ItemDoesNotExistException;
import com.calculator.webapp.db.dto.CalculatorResponseDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.List;

@Path("/calculator")
@Produces(MediaType.APPLICATION_JSON)
public class CalculatorRestResource {

    private static final Logger logger = LogManager.getLogger(CalculatorRestResource.class);
    private final CalculatorApp calculator;
    private final CalculatorDaoImpl dao;

    private static final int ACCEPTED = Response.Status.ACCEPTED.getStatusCode();
    private static final int OK = Response.Status.OK.getStatusCode();
    private static final int BAD_REQUEST = Response.Status.BAD_REQUEST.getStatusCode();
    private static final String PENDING_REQUEST = "Not evaluated";

    @Inject
    public CalculatorRestResource(final CalculatorApp calculator, final CalculatorDaoImpl dao) {
        this.calculator = calculator;
        this.dao = dao;
    }

    @POST
    @Path("/calculate")
    public Response doGetCalculationId(@NotNull @QueryParam("equation") String equation) {
        Long itemId = saveCalculationRequest(equation);
        return createResponseWithPayload(ACCEPTED,itemId);
    }

    @GET
    @Path("/calculate/{id}")
    public Response doGetCalculationResult(@NotNull @PathParam("id") Long id) {
        try{
            CalculatorResponseDTO calculation = dao.getItem(id);
            return isNotEvaluated(calculation) ? createResponseWithoutPayload(ACCEPTED) : createResponseWithPayload(OK,calculation);
        }
        catch (ItemDoesNotExistException ex){
            return createResponseWithoutPayload(BAD_REQUEST);
        }
    }

    private boolean isNotEvaluated(final CalculatorResponseDTO calculation){
        return calculation.getResponseMsg().equals("Not evaluated");
    }

    private Response createResponseWithPayload(final int statusCode,final Object responseBody){
        return Response.status(statusCode).entity(responseBody).build();
    }

    private Response createResponseWithoutPayload(final int statusCode){
        return Response.status(statusCode).build();
    }

    @GET
    @Path("/calculationHistory")
    public Response doGetCalculationHistory() {
        List<CalculatorResponseDTO> calculationHistory = dao.getAllItems();
        return createResponseWithPayload(OK,calculationHistory);
    }

    private Long saveCalculationRequest(final String equation) {
        CalculatorResponseDTO request  = new CalculatorResponseDTO(equation,PENDING_REQUEST, new Date());
        dao.saveItem(request);
        return request.getId();
    }
}
