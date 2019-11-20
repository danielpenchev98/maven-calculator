package com.calculator.webapp.restresources;

import com.calculator.webapp.db.dao.EquationDaoImpl;
import com.calculator.webapp.db.dao.RequestDaoImpl;
import com.calculator.webapp.db.dao.exceptions.ItemDoesNotExistException;
import com.calculator.webapp.db.dto.CalculationRequestDTO;
import com.calculator.webapp.db.dto.CalculatorResponseDTO;
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
import java.util.List;

import static com.calculator.webapp.db.dto.requeststatus.RequestStatus.COMPLETED;


@Path("/calculator")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CalculatorRestResource {

    private final RequestDaoImpl requestDao;
    private final EquationDaoImpl equationDao;

    private static final int ACCEPTED = Response.Status.ACCEPTED.getStatusCode();
    private static final int OK = Response.Status.OK.getStatusCode();
    private static final int BAD_REQUEST = Response.Status.BAD_REQUEST.getStatusCode();
    private static final int NOT_FOUND = Response.Status.NOT_FOUND.getStatusCode();
    private static final Logger logger = LoggerFactory.getLogger(CalculatorRestResource.class);

    @Inject
    public CalculatorRestResource(final RequestDaoImpl requestDao,final EquationDaoImpl equationDao) {
        this.requestDao = requestDao;
        this.equationDao=equationDao;
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
            CalculationRequestDTO calculation = requestDao.getItem(id);
            return isEvaluated(calculation) ? getCalculationResultResponse(calculation) : getPendingCalculationResponse();
        }
        catch (ItemDoesNotExistException ex){
            logger.warn("Item with id :"+id+" hasn't been found.\nStack Trace :"+ Arrays.toString(ex.getStackTrace()));
            return createResponseWithoutPayload(NOT_FOUND);
        }
    }

    private Response getPendingCalculationResponse(){
       return createResponseWithoutPayload(ACCEPTED);
    }

    private Response getCalculationResultResponse(final CalculationRequestDTO calculation){
        try {
            CalculatorResponseDTO result = equationDao.getItem(calculation.getEquation());
            String errorMsg = result.getErrorMsg();
            if (isSuccessfulCalculationResult(errorMsg)) {
                return createResponseWithPayload(OK, new CalculationResult(result.getCalculationResult()));
            } else {
                return createResponseWithPayload(BAD_REQUEST, new CalculationError(BAD_REQUEST, errorMsg));
            }
        } catch (ItemDoesNotExistException ex){
            logger.error("hasn't been found.\nStack Trace :"+ Arrays.toString(ex.getStackTrace()));
            return createResponseWithoutPayload(NOT_FOUND);
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

    private Long saveCalculationRequest(final String equation) {
        CalculationRequestDTO request  = new CalculationRequestDTO(equation,new Date());
        requestDao.saveItem(request);
        return request.getId();
    }
}
