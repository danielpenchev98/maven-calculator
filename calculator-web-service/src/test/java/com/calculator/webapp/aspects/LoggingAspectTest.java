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

import java.util.Date;

import static org.aspectj.lang.Aspects.aspectOf;
import static org.junit.Assert.*;

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
            Mockito.when(calculator.calculateResult("1/0")).thenThrow(new BadInputException("Division by zero"));
            calculator.calculateResult("1/0");
        } catch(BadInputException ex){
            ArgumentCaptor<String> requestCaptor = ArgumentCaptor.forClass(String.class);
            Mockito.verify(logger).error(requestCaptor.capture());

            assertTrue(requestCaptor.getValue().contains("1/0"));
            assertTrue(requestCaptor.getValue().contains("Division by zero"));
        }
    }

    @Test
    public void verifyWhenRequestIsSavedThenLog(){
        requestDao.saveItem(new RequestDTO("1+1",new Date()));

        ArgumentCaptor<String> requestCaptor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(logger).error(requestCaptor.capture());

        String realLogContent =requestCaptor.getValue();

        assertTrue(realLogContent.contains("Request"));
        assertTrue(realLogContent.contains("1+1"));
        assertTrue(realLogContent.contains("saved"));
    }

    @Test
    public void verifyWhenExpressionIsSavedThenLog(){
        expressionDao.saveItem(new ExpressionDTO("1+1"));

        ArgumentCaptor<String> requestCaptor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(logger).error(requestCaptor.capture());

        String realLogContent = requestCaptor.getValue();

        assertTrue(realLogContent.contains("Expression"));
        assertTrue(realLogContent.contains("1+1"));
        assertTrue(realLogContent.contains("saved"));
    }

    @Test
    public void verifyWhenNonExistingRequestIsRequiredThenLog() {
        try{
            Mockito.when(requestDao.getItem(1L)).thenThrow(new ItemDoesNotExistException("Request not found"));
            requestDao.getItem(1L);
        } catch(ItemDoesNotExistException ex){
            ArgumentCaptor<String> requestCaptor = ArgumentCaptor.forClass(String.class);
            Mockito.verify(logger).error(requestCaptor.capture());
            assertTrue(requestCaptor.getValue().contains("Request not found"));
        }
    }

    @Test
    public void verifyWhenNonExistingExpressionIsRequiredThenLog(){
        try{
            Mockito.when(expressionDao.getItem("1+1")).thenThrow(new ItemDoesNotExistException("Expression not found"));
            expressionDao.getItem("1+1");
        } catch(ItemDoesNotExistException ex){
            ArgumentCaptor<String> requestCaptor = ArgumentCaptor.forClass(String.class);
            Mockito.verify(logger).error(requestCaptor.capture());
            assertTrue(requestCaptor.getValue().contains("Expression not found"));
        }
    }
}
