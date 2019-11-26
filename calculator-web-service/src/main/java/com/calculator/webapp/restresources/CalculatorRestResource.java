package com.calculator.webapp.restresources;

import com.calculator.webapp.db.dao.ExpressionDaoImpl;
import com.calculator.webapp.db.dao.RequestDaoImpl;
import com.calculator.webapp.db.dao.exceptions.ItemDoesNotExistException;
import com.calculator.webapp.db.dto.RequestDTO;
import com.calculator.webapp.db.dto.ExpressionDTO;
import com.calculator.webapp.restresponses.CalculationError;
import com.calculator.webapp.restresponses.RequestId;
import com.calculator.webapp.restresponses.CalculationResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.Date;

import static com.calculator.webapp.db.dto.requeststatus.RequestStatus.COMPLETED;


@Path("/calculator")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CalculatorRestResource {

    private final RequestDaoImpl requestDao;
    private final ExpressionDaoImpl expressionDao;

    private static final int ACCEPTED = Response.Status.ACCEPTED.getStatusCode();
    private static final int OK = Response.Status.OK.getStatusCode();
    private static final int BAD_REQUEST = Response.Status.BAD_REQUEST.getStatusCode();
    private static final int NOT_FOUND = Response.Status.NOT_FOUND.getStatusCode();

    @Inject
    public CalculatorRestResource(final RequestDaoImpl requestDao, final ExpressionDaoImpl expressionDao) {
        this.requestDao = requestDao;
        this.expressionDao=expressionDao;
    }

    @POST
    @Path("/calculate")
    public Response queueCurrentRequest(final ExpressionRequestBody body) {
        Long itemId = saveCalculationRequest(body.getExpression());
        return createResponseWithPayload(ACCEPTED,new RequestId(itemId));
    }

    @GET
    @Path("/calculations/{id}")
    public Response doGetCalculationResult(@NotNull @PathParam("id") Long id) {
        try{
            RequestDTO calculation = requestDao.getItem(id);
            return isEvaluated(calculation) ? getCalculationResultResponse(calculation) : getPendingCalculationResponse();
        } catch (ItemDoesNotExistException ex){
            return createResponseWithoutPayload(NOT_FOUND);
        }
    }

    private Response getPendingCalculationResponse(){
       return createResponseWithoutPayload(ACCEPTED);
    }

    private Response getCalculationResultResponse(final RequestDTO calculation) throws ItemDoesNotExistException {
        ExpressionDTO result = expressionDao.getItem(calculation.getExpression());
        if (isSuccessfulCalculationResult(result.getErrorMsg())) {
            return createResponseWithPayload(OK, new CalculationResult(result.getCalculationResult()));
        } else {
            return createResponseWithPayload(BAD_REQUEST, new CalculationError(BAD_REQUEST, result.getErrorMsg()));
        }
    }

    private boolean isSuccessfulCalculationResult(final String calculationResult){
        return calculationResult == null;
    }

    private boolean isEvaluated(final RequestDTO calculation){
        return calculation.getStatusCode()==COMPLETED.getStatusCode();
    }

    private Response createResponseWithPayload(final int statusCode,final Object responseBody){
        return Response.status(statusCode).entity(responseBody).build();
    }

    private Response createResponseWithoutPayload(final int statusCode){
        return Response.status(statusCode).build();
    }

    private Long saveCalculationRequest(final String expression) {
        RequestDTO request  = new RequestDTO(expression,new Date());
        requestDao.saveItem(request);
        return request.getId();
    }
}
