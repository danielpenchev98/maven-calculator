package com.calculator.webapp.scheduler;

import com.calculator.core.CalculatorApp;
import com.calculator.core.exceptions.DivisionByZeroException;
import com.calculator.webapp.db.dao.EquationDaoImpl;
import com.calculator.webapp.db.dao.RequestDaoImpl;
import com.calculator.webapp.db.dto.CalculationRequestDTO;
import com.calculator.webapp.db.dto.CalculatorResponseDTO;
import com.calculator.webapp.db.dto.requeststatus.RequestStatus;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
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
    private EquationDaoImpl equationDao;

    @Mock
    private JobExecutionContext context;

    @Before
    public void setUp() {
        job=new PendingCalculationJob(calculator,requestDao,equationDao);
    }

    @Ignore
    @Test
    public void execute_completedLegalCalculation() throws Exception{
        setDaoBehaviour("1+1");
        Mockito.when(calculator.calculateResult("1+1")).thenReturn(2.0);

        ArgumentCaptor<CalculatorResponseDTO> responseCaptor = ArgumentCaptor.forClass(CalculatorResponseDTO.class);
        Mockito.verify(equationDao).update(responseCaptor.capture());

        ArgumentCaptor<CalculationRequestDTO> requestCaptor = ArgumentCaptor.forClass(CalculationRequestDTO.class);
        Mockito.verify(requestDao).update(requestCaptor.capture());

        job.execute(context);

        CalculationRequestDTO completedRequest = requestCaptor.getValue();
        CalculatorResponseDTO calculatorResponse = responseCaptor.getValue();
        verifyUpdatedCalculation(calculatorResponse,2.0,null);
        verifyUpdatedRequestStatus(completedRequest, RequestStatus.COMPLETED.getStatusCode());
    }

    private void verifyUpdatedRequestStatus(final CalculationRequestDTO completedRequest,final int statusCode) {
        assertThat(completedRequest.getStatusCode(),is(statusCode));
    }

    @Test
    public void execute_completedIllegalCalculation() throws Exception {
        setDaoBehaviour("1/0");
        Mockito.when(calculator.calculateResult("1/0")).thenThrow(new DivisionByZeroException("Division by zero"));
        Mockito.when(equationDao.getItem("1/0")).thenReturn(null);

        ArgumentCaptor<CalculationRequestDTO> requestCaptor = ArgumentCaptor.forClass(CalculationRequestDTO.class);
        Mockito.verify(requestDao).update(requestCaptor.capture());

       // ArgumentCaptor<CalculatorResponseDTO> responseCaptor = ArgumentCaptor.forClass(CalculatorResponseDTO.class);
       //Mockito.verify(equationDao).update(responseCaptor.capture());

        job.execute(context);


        CalculationRequestDTO completedRequest = requestCaptor.getValue();
        //CalculatorResponseDTO calculatorResponse = responseCaptor.getValue();
        //verifyUpdatedCalculation(calculatorResponse,0.0,"Division by zero");
        verifyUpdatedRequestStatus(completedRequest, RequestStatus.COMPLETED.getStatusCode());
    }

    private void setDaoBehaviour(final String equation){
        List<CalculationRequestDTO> pendingCalculations = Collections.singletonList(new CalculationRequestDTO(equation,new Date()));
        Mockito.when(requestDao.getAllPendingRequests()).thenReturn(pendingCalculations);
    }

    private void verifyUpdatedCalculation(final CalculatorResponseDTO completedCalculation,final double calculationResult,final String errorMsg){
        assertThat(completedCalculation.getCalculationResult(),is(calculationResult));
        assertThat(completedCalculation.getErrorMsg(),is(errorMsg));
    }

}
