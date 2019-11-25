package com.calculator.webapp.scheduler;

import com.calculator.core.CalculatorApp;
import com.calculator.core.exceptions.DivisionByZeroException;
import com.calculator.webapp.db.dao.ExpressionDaoImpl;
import com.calculator.webapp.db.dao.RequestDaoImpl;
import com.calculator.webapp.db.dao.exceptions.ItemDoesNotExistException;
import com.calculator.webapp.db.dto.RequestDTO;
import com.calculator.webapp.db.dto.ExpressionDTO;
import com.calculator.webapp.db.dto.requeststatus.RequestStatus;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.quartz.JobExecutionContext;

import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class PendingCalculationJobTest {

    private PendingCalculationJob job;

    @Mock
    private CalculatorApp calculator;

    @Mock
    private RequestDaoImpl requestDao;

    @Mock
    private ExpressionDaoImpl expressionDao;

    @Mock
    private JobExecutionContext context;

    private String testExpression = "1+1";
    private double testResult = 2.0;

    @Before
    public void setUp() throws Exception {
        setDaoBehaviour("1/0");
    //    Mockito.when(calculator.calculateResult(testExpression)).thenReturn(testResult);
        job=new PendingCalculationJob(calculator,requestDao,expressionDao);
    }

    @Test
    public void execute_unknownExpression_expressionSavedInDB() throws Exception{
        Mockito.when(expressionDao.getItem(testExpression)).thenThrow(new ItemDoesNotExistException("does not exist"));

        job.execute(context);

        ArgumentCaptor<ExpressionDTO> expressionCaptor = ArgumentCaptor.forClass(ExpressionDTO.class);
        Mockito.verify(expressionDao).saveItem(expressionCaptor.capture());

        verifyUpdatedCalculation(expressionCaptor.getValue(),testResult,null);
    }

    @Test
    public void execute_illegalExpression() throws Exception{
        Mockito.when(expressionDao.getItem("1/0")).thenReturn(new ExpressionDTO("1/0",0.0,null));
        Mockito.when(calculator.calculateResult("1/0")).thenThrow(new DivisionByZeroException("Division by zero"));
        job.execute(context);
    }

    @Test
    public void execute_knownExpression_expressionNotSavedInDB() throws Exception{
        Mockito.when(expressionDao.getItem(testExpression)).thenReturn(new ExpressionDTO(testExpression,testResult,null));

        job.execute(context);

        Mockito.verify(expressionDao,Mockito.never()).update(Mockito.any());
    }

    @Test
    public void execute_processRequest_updateStatusInDB(){

        job.execute(context);

        ArgumentCaptor<RequestDTO> requestCaptor = ArgumentCaptor.forClass(RequestDTO.class);
        Mockito.verify(requestDao).update(requestCaptor.capture());

        verifyUpdatedRequestStatus(requestCaptor.getValue(), RequestStatus.COMPLETED.getStatusCode());
    }

    private void verifyUpdatedRequestStatus(final RequestDTO completedRequest, final int statusCode) {
        assertThat(completedRequest.getStatusCode(),is(statusCode));
    }

    private void setDaoBehaviour(final String expression) {
        List<RequestDTO> pendingCalculations = Collections.singletonList(new RequestDTO(expression,new Date()));
        Mockito.when(requestDao.getAllPendingRequests()).thenReturn(pendingCalculations);
    }

    private void verifyUpdatedCalculation(final ExpressionDTO completedCalculation, final double calculationResult, final String errorMsg){
        assertThat(completedCalculation.getCalculationResult(),is(calculationResult));
        assertThat(completedCalculation.getErrorMsg(),is(errorMsg));
    }

}
