package com.calculator.webapp.aspects;

import com.calculator.core.exceptions.BadInputException;
import com.calculator.webapp.db.dto.ExpressionDTO;
import com.calculator.webapp.db.dto.RequestDTO;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

@Aspect
public class LoggingAspect {
    private Logger logger =  LoggerFactory.getLogger(LoggingAspect.class);

    void setLogger(final Logger logger){
        this.logger = logger;
    }

    @Pointcut("call(void com.calculator.webapp.db.dao.Dao+.saveItem(*)) && args(request)")
    public void savingRequest(RequestDTO request) {
    }

    @Pointcut("call(* com.calculator.webapp.db.dao.Dao+.getItem(*))")
    public void gettingItem() {
    }

    @Pointcut("call(void com.calculator.webapp.db.dao.Dao+.saveItem(*)) && args(expression)")
    public void savingExpression(ExpressionDTO expression) {
    }

    @Pointcut("call(double com.calculator.core.CalculatorApp.calculateResult(*)) && args(expression)")
    public void calculateExpressions(String expression){
    }

    @After(value = "savingRequest(request)", argNames = "request")
    public void logSavingRequest(RequestDTO request) {
        logger.error("Request with id :" + request.getId() + " for calculation of expression :" + request.getExpression() +" has been saved");
    }

    @After(value = "savingExpression(expression)", argNames = "expression")
    public void logSavingExpression(ExpressionDTO expression) {
        logger.error("Expression :" + expression.getExpression() + " has been saved");
    }

    @AfterThrowing(pointcut = "gettingItem()",throwing = "error", argNames = "error")
    public void logMissingExpressionInDB(Throwable error){
        logger.error(error.getMessage()+"\n Stack trace :"+ Arrays.toString(error.getStackTrace()));
    }

    @AfterThrowing(pointcut = "calculateExpressions(expression)", throwing = "error", argNames = "expression,error")
    public void logProblemWithExpression(String expression, Throwable error) {
        if (error instanceof BadInputException) {
            logger.error("There was a problem with the expression :" + expression + "\nReason :" + error.getMessage() + "\nStack trace :" + Arrays.toString(error.getStackTrace()));
        } else {
            logger.error("System error :" + Arrays.toString(error.getStackTrace()));
        }
    }

}
