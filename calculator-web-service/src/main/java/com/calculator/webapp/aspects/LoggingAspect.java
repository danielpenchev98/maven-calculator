package com.calculator.webapp.aspects;

import com.calculator.core.exceptions.BadInputException;
import com.calculator.webapp.db.dao.Dao;
import com.calculator.webapp.db.dao.RequestDaoImpl;
import com.calculator.webapp.db.dto.ExpressionDTO;
import com.calculator.webapp.db.dto.RequestDTO;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

@Aspect
public class LoggingAspect {
    private Logger logger =  LoggerFactory.getLogger(LoggingAspect.class);


    @Pointcut("execution(void com.calculator.webapp.db.dao.Dao.saveItem(*)) && args(request)")
    public void savingDto(Object request){
    }

    @Before(value = "savingDto(request)", argNames = "request")
    public void adviceSavingDto(Object request){
        if(request instanceof RequestDTO){
            logger.info("Item with id :"+((RequestDTO)request).getId()+" and content :"+((RequestDTO)request).getExpression());
        }
        else{
            logger.info("Expression :"+((ExpressionDTO)request).getExpression()+" and content :"+((ExpressionDTO)request).getCalculationResult());
        }
    }

    @Pointcut("execution(* com.calculator.webapp.db.dao.Dao.getItem(*)) && args(key)")
    public void gettingDto(Object key){
    }

    @Before(value = "gettingDto(key)", argNames = "key")
    public void adviceGettingDto(Object key){
        if(key instanceof String){
            logger.info("Expression :"+key+" has been requested from the database");
        }
        else{
            logger.info("Request with id :"+key+" has been requested from the database");
        }
    }

    @AfterThrowing(pointcut = "gettingDto(key)",throwing = "error", argNames = "key,error")
    public void missingItemInDB(Object key,Throwable error){
        logger.warn(error.getMessage()+"\n Stack trace :"+ Arrays.toString(error.getStackTrace()));
    }

    @Pointcut("execution(double com.calculator.webapp.scheduler.PendingCalculationJob.calculateExpression(*)) && args(expression)")
    public void calculateExpressions(String expression){
    }

    @AfterThrowing(pointcut = "calculateExpressions(expression)",throwing = "error",argNames = "expression,error")
    public void problemWithExpression(String expression,Throwable error){
        if(error instanceof BadInputException){
            logger.warn("There was a problem with the expression :"+expression+"\nReason :"+error.getMessage()+"\nStack trace :"+Arrays.toString(error.getStackTrace()));
        }
        else{
            logger.error("System error :" + Arrays.toString(error.getStackTrace()));
        }
    }



}
