package com.calculator.webapp.aspects;

import com.calculator.core.CalculatorApp;
import com.calculator.core.exceptions.BadInputException;
import com.calculator.webapp.db.dao.ExpressionDaoImpl;
import com.calculator.webapp.db.dao.RequestDaoImpl;
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
import java.util.List;

import static org.aspectj.lang.Aspects.aspectOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
    public void verifyWhenExpressionIsSavedThenLog(){

        requestDao.saveItem(new RequestDTO("1+1",new Date()));

        ArgumentCaptor<String> requestCaptor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(logger).error(requestCaptor.capture());

        assertTrue(requestCaptor.getValue().contains("1+1"));
    }
}
