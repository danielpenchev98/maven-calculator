package com.calculator.webapp.aspects;

import com.calculator.core.CalculatorApp;
import com.calculator.core.exceptions.BadInputException;
import com.calculator.webapp.db.dao.ExpressionDaoImpl;
import com.calculator.webapp.db.dao.RequestDaoImpl;
import com.calculator.webapp.db.dao.exceptions.ItemDoesNotExistException;
import com.calculator.webapp.db.dto.ExpressionDTO;
import com.calculator.webapp.db.dto.RequestDTO;
import com.calculator.webapp.scheduler.PendingCalculationJob;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
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

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;


    @Before
    public void setUp(){
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void tearDown(){
        System.setOut(originalOut);
    }

    @Test
    public void verifyWhenProblemWithCalculatorThenLog() {
        try {
            Exception badInput = new BadInputException("Division by zero");
            Mockito.when(calculator.calculateResult("1/0")).thenThrow(badInput);
            calculator.calculateResult("1/0");
        } catch(BadInputException ex){
            assertTrue(outContent.toString().contains("There was a problem with the expression :1/0\n"+getExceptionInfo(ex)));
        }
    }

    @Test
    public void verifyWhenRequestIsSavedThenLog(){
        Date time = new Date();
        RequestDTO request = new RequestDTO("1+1",time);
        requestDao.saveItem(request);

        assertTrue(outContent.toString().contains(request.toString()+" has been saved"));
    }

    @Test
    public void verifyWhenExpressionIsSavedThenLog(){
        ExpressionDTO expression = new ExpressionDTO("1+1");
        expressionDao.saveItem(expression);

        assertTrue(outContent.toString().contains(expression.toString()+" has been saved"));
    }

    @Test
    public void verifyWhenNonExistingRequestIsRequiredThenLog() {
        try{
            ItemDoesNotExistException itemNotFound = new ItemDoesNotExistException("Request not found");
            Mockito.when(requestDao.getItem(1L)).thenThrow(itemNotFound);
            requestDao.getItem(1L);
        } catch(ItemDoesNotExistException ex){
            assertTrue(outContent.toString().contains(getExceptionInfo(ex)));
        }
    }

    @Test
    public void verifyWhenNonExistingExpressionIsRequiredThenLog(){
        try{
            ItemDoesNotExistException itemNotFound = new ItemDoesNotExistException("Expression not found");
            Mockito.when(expressionDao.getItem("1+1")).thenThrow(itemNotFound);
            expressionDao.getItem("1+1");
        } catch(ItemDoesNotExistException ex){
            assertTrue(outContent.toString().contains(getExceptionInfo(ex)));
        }
    }

    private String getExceptionInfo(Exception ex){
        return ex.getMessage()+"\n"+ Arrays.toString(ex.getStackTrace());
    }
}
