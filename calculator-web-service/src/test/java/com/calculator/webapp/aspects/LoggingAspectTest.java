package com.calculator.webapp.aspects;

import com.calculator.core.CalculatorApp;
import com.calculator.core.exceptions.BadInputException;
import com.calculator.webapp.db.dao.ExpressionDaoImpl;
import com.calculator.webapp.db.dao.RequestDaoImpl;
import com.calculator.webapp.db.dao.exceptions.ItemDoesNotExistException;
import com.calculator.webapp.db.dto.ExpressionDTO;
import com.calculator.webapp.db.dto.RequestDTO;
import com.calculator.webapp.scheduler.PendingCalculationJob;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;

import java.util.Arrays;
import java.util.Date;

import static org.aspectj.lang.Aspects.aspectOf;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.is;

@RunWith(MockitoJUnitRunner.class)
public class LoggingAspectTest {

    @Mock
    private CalculatorApp calculator;

    @Mock
    private RequestDaoImpl requestDao;

    @Mock
    private ExpressionDaoImpl expressionDao;

    @Mock
    private PendingCalculationJob job;

    @Mock
    private Logger logger;

    @Before
    public void setUp(){
        LoggingAspect aspect = aspectOf(LoggingAspect.class);
        aspect.setLogger(logger);
    }

    @Test
    public void verifyWhenProblemWithCalculatorThenLog() {
        try {
            Exception badInput = new BadInputException("Division by zero");
            Mockito.when(calculator.calculateResult("1/0")).thenThrow(badInput);
            calculator.calculateResult("1/0");
        } catch(BadInputException ex){
            assertThat(getLoggerInput(),is("There was a problem with the expression :1/0\n"+getExceptionInfo(ex)));
        }
    }

    @Test
    public void verifyWhenRequestIsSavedThenLog(){
        Date time = new Date();
        RequestDTO request = new RequestDTO("1+1",time);
        requestDao.saveItem(request);

        assertThat(getLoggerInput(),is(request+" has been saved"));
    }

    @Test
    public void verifyWhenExpressionIsSavedThenLog(){
        ExpressionDTO expression = new ExpressionDTO("1+1");
        expressionDao.saveItem(expression);

        assertThat(getLoggerInput(),is(expression+" has been saved"));
    }

    @Test
    public void verifyWhenNonExistingRequestIsRequiredThenLog() {
        try{
            ItemDoesNotExistException itemNotFound = new ItemDoesNotExistException("Request not found");
            Mockito.when(requestDao.getItem(1L)).thenThrow(itemNotFound);
            requestDao.getItem(1L);
        } catch(ItemDoesNotExistException ex){
            assertThat(getLoggerInput(),is(getExceptionInfo(ex)));
        }
    }

    @Test
    public void verifyWhenNonExistingExpressionIsRequiredThenLog(){
        try{
            ItemDoesNotExistException itemNotFound = new ItemDoesNotExistException("Expression not found");
            Mockito.when(expressionDao.getItem("1+1")).thenThrow(itemNotFound);
            expressionDao.getItem("1+1");
        } catch(ItemDoesNotExistException ex){

            assertThat(getLoggerInput(),is(getExceptionInfo(ex)));
        }
    }

    private String getExceptionInfo(Exception ex){
        return ex.getMessage()+"\n"+ Arrays.toString(ex.getStackTrace());
    }

    private String getLoggerInput(){
        ArgumentCaptor<String> requestCaptor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(logger).error(requestCaptor.capture());
        return requestCaptor.getValue();
    }

}
