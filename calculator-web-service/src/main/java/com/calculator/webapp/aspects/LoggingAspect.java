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

    @Pointcut("call(void com.calculator.webapp.db.dao.RequestDaoImpl.saveItem(RequestDTO)) && args(request)")
    public void savingRequest(RequestDTO request) {
    }

    @Pointcut("call(ExpressionDTO com.calculator.webapp.db.dao.ExpressionDaoImpl.getItem(String)) && args(key)")
    public void gettingExpression(String key) {
    }

    @Pointcut("call(RequestDTO com.calculator.webapp.db.dao.RequestDaoImpl.getItem(Long)) && args(key)")
    public void gettingRequest(Long key){
    }

    @Pointcut("call(ExpressionDTO com.calculator.webapp.db.dao.ExpressionDaoImpl.saveItem(ExpressionDTO)) && args(expression)")
    public void savingExpression(ExpressionDTO expression) {
    }

    @Pointcut("call(double com.calculator.core.CalculatorApp.calculateResult(*)) && args(expression)")
    public void calculateExpressions(String expression){
    }

    @Before(value = "savingRequest(request)", argNames = "request")
    public void logSavingRequest(RequestDTO request) {
        logger.error("Item with id :" + request.getId() + " and content :" + request.getExpression());
    }

    @Before(value = "savingExpression(expression)", argNames = "expression")
    public void logSavingExpression(ExpressionDTO expression) {
        logger.error("Expression :" + expression.getExpression() + " and content :" + expression.getCalculationResult());
    }

    @Before(value="gettingExpression(key)",argNames="key")
    public void logGettingExpression(String key){
        logger.error("Expression :"+key+" has been requested from the database");
    }

    @Before(value = "gettingRequest(key)", argNames = "key")
    public void logGettingDto(Long key){
            logger.error("Request with id :"+key+" has been requested from the database");
    }

    @AfterThrowing(pointcut = "gettingRequest(key)",throwing = "error", argNames = "key,error")
    public void logMissingRequestInDB(Long key,Throwable error){
        logger.error(error.getMessage()+"\n Stack trace :"+ Arrays.toString(error.getStackTrace()));
    }

    @AfterThrowing(pointcut = "gettingExpression(key)",throwing = "error", argNames = "key,error")
    public void logMissingExpressionInDB(String key,Throwable error){
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

   /* @Pointcut("call(* org.quartz.*.*(..))")
    public void problemWithQuartz(){
    }

    @AfterThrowing(pointcut = "problemWithQuartz()",throwing = "error",argNames = "error")
    public void logProblemWithQuartz(Throwable error){
        logger.error("Problem with quartz  ->"+error.getMessage()+"\nStack trace :"+ Arrays.toString(error.getStackTrace()));
    }*/

}
