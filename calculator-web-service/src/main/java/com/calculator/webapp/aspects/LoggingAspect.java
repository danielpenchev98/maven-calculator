package com.calculator.webapp.aspects;

import com.calculator.webapp.db.dao.Dao;
import com.calculator.webapp.db.dao.RequestDaoImpl;
import com.calculator.webapp.db.dto.RequestDTO;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class LoggingAspect {
    private Logger logger =  LoggerFactory.getLogger(LoggingAspect.class);


    @Pointcut("execution(void com.calculator.webapp.db.dao.RequestDaoImpl.saveItem(*)) && args(request)")
    public void saving(RequestDTO request){
    }

    @Before(value = "saving(request)")
    public void advice(RequestDTO request){
        logger.info("Item with id :"+request.getId()+" and content :"+request.getExpression());
    }

    /*@Pointcut("execution(boolean com.calculator.webapp.restresources.CalculatorRestResource.isSuccessfulCalculationResult(*))")
    public void savingRequest(){}

    @Before("savingRequest()")
    public void advice(){
    }*/
    /*@Pointcut("execution(void com.calculator.webapp.restresponses.CalculationError.setErrorCode(*))")
    public void pointcut(){}

    @After("pointcut()")
    public void advice(){
        logger.warn("yaya");
    }*/

}
