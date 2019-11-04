package com.calculator.webapp.restresources;

import com.calculator.webapp.db.dao.CalculatorDaoImpl;
import com.calculator.webapp.db.dao.exceptions.ItemDoesNotExistException;
import com.calculator.webapp.db.dto.CalculationRequestDTO;
import com.calculator.webapp.restresponses.CalculationError;
import com.calculator.webapp.restresponses.RequestId;
import com.calculator.webapp.restresponses.CalculationResult;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.List;

import static com.calculator.webapp.db.dto.requeststatus.RequestStatus.COMPLETED;


@Path("/calculator")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CalculatorRestResource {

    private final CalculatorDaoImpl dao;

    private static final int ACCEPTED = Response.Status.ACCEPTED.getStatusCode();
    private static final int OK = Response.Status.OK.getStatusCode();
    private static final int BAD_REQUEST = Response.Status.BAD_REQUEST.getStatusCode();
    private static final int NOT_FOUND = Response.Status.NOT_FOUND.getStatusCode();


    @Inject
    public CalculatorRestResource(final CalculatorDaoImpl dao) {
        this.dao = dao;
    }

    @POST
    @Path("/calculate")
    public Response queueCurrentRequest(EquationRequestBody body) {
        Long itemId = saveCalculationRequest(body.getEquation());
        return createResponseWithPayload(ACCEPTED,new RequestId(itemId));
    }

    @GET
    @Path("/calculations/{id}")
    public Response doGetCalculationResult(@NotNull @PathParam("id") Long id) {
        try{
            CalculationRequestDTO calculation = dao.getItem(id);
            return isEvaluated(calculation) ? getCalculationResultResponse(calculation) : getPendingCalculationResponse();
        }
        catch (ItemDoesNotExistException ex){
            return createResponseWithoutPayload(NOT_FOUND);
        }
    }

    private Response getPendingCalculationResponse(){
       return createResponseWithoutPayload(ACCEPTED);
    }

    private Response getCalculationResultResponse(final CalculationRequestDTO calculation){
        String errorMsg = calculation.getErrorMsg();
        if(isSuccessfulCalculationResult(errorMsg)){
            return createResponseWithPayload(OK,new CalculationResult(calculation.getCalculationResult()));
        } else {
            return createResponseWithPayload(BAD_REQUEST,new CalculationError(BAD_REQUEST,errorMsg));
        }
    }

    private boolean isSuccessfulCalculationResult(final String calculationResult){
        return calculationResult == null;
    }

    private boolean isEvaluated(final CalculationRequestDTO calculation){
        return calculation.getStatusCode()==COMPLETED.getStatusCode();
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
        List<CalculationRequestDTO> calculationHistory = dao.getAllItems();
        return createResponseWithPayload(OK,calculationHistory);
    }

    private Long saveCalculationRequest(final String equation) {
        CalculationRequestDTO request  = new CalculationRequestDTO(equation,new Date());
        dao.saveItem(request);
        return request.getId();
    }
}
