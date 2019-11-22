package com.calculator.webapp.scheduler;

import com.calculator.core.CalculatorApp;
import com.calculator.core.exceptions.BadInputException;
import com.calculator.webapp.db.dao.ExpressionDaoImpl;
import com.calculator.webapp.db.dao.RequestDaoImpl;
import com.calculator.webapp.db.dao.exceptions.ItemDoesNotExistException;
import com.calculator.webapp.db.dto.RequestDTO;
import com.calculator.webapp.db.dto.ExpressionDTO;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

import static com.calculator.webapp.db.dto.requeststatus.RequestStatus.COMPLETED;

public class PendingCalculationJob implements Job {

    private RequestDaoImpl requestDao;
    private ExpressionDaoImpl expressionDao;
    private CalculatorApp calculator;

    private Logger logger = LoggerFactory.getLogger(PendingCalculationJob.class);


    public PendingCalculationJob(){
        this(new CalculatorApp(),new RequestDaoImpl(),new ExpressionDaoImpl());
    }

    PendingCalculationJob(final CalculatorApp calculator, final RequestDaoImpl requestDao, final ExpressionDaoImpl expressionDao){
        this.calculator=calculator;
        this.requestDao=requestDao;
        this.expressionDao=expressionDao;
    }

    @Override
    public synchronized void execute(JobExecutionContext jobExecutionContext) {
        List<RequestDTO> pendingCalculations = requestDao.getAllPendingRequests();
        for(RequestDTO pendingCalculation : pendingCalculations){
            processRequest(pendingCalculation);
        }
    }

    private void processRequest(final RequestDTO request){
        String expression = request.getExpression();
        if(expressionDoesNotExist(expression)) {
            completeCalculation(expression);
        }
        request.setStatusCode(COMPLETED.getStatusCode());
        requestDao.update(request);
    }

    private boolean expressionDoesNotExist(final String expression){
        try {
            expressionDao.getItem(expression);
            return false;
        } catch(ItemDoesNotExistException ex){
            logger.warn(ex.getMessage()+"\nStack trace :" + Arrays.toString(ex.getStackTrace()));
            return true;
        }
    }

    private void completeCalculation(final String expression){
        ExpressionDTO expressionRecord = new ExpressionDTO(expression);
        try {
            double result = calculateExpression(expression);
            expressionRecord.setCalculationResult(result);
        } catch (BadInputException badRequest) {
            logger.warn("User error :" + Arrays.toString(badRequest.getStackTrace()));
            String userError = badRequest.getMessage();
            expressionRecord.setErrorMsg(userError);
        } catch(Exception ex){
            logger.error("System error :" + Arrays.toString(ex.getStackTrace()));
            return;
        }
        expressionDao.saveItem(expressionRecord);
    }

    private double calculateExpression(final String expression) throws Exception {
        return calculator.calculateResult(expression);
    }
}
