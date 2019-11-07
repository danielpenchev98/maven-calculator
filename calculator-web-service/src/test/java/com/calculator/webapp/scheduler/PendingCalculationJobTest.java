package com.calculator.webapp.scheduler;

import com.calculator.core.CalculatorApp;
import com.calculator.core.exceptions.DivisionByZeroException;
import com.calculator.webapp.db.dao.CalculatorDaoImpl;
import com.calculator.webapp.db.dto.CalculationRequestDTO;
import com.calculator.webapp.scheduler.PendingCalculationJob;
import org.hamcrest.core.IsNull;
import org.junit.Before;
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
    private CalculatorDaoImpl dao;

    @Mock
    private JobExecutionContext context;

    @Before
    public void setUp() {
        job=new PendingCalculationJob(calculator,dao);
    }

    @Test
    public void execute_completedLegalCalculation() throws Exception{
        setDaoBehaviour("1+1");
        Mockito.when(calculator.calculateResult("1+1")).thenReturn(2.0);
        job.execute(context);

        ArgumentCaptor<CalculationRequestDTO> calculationCaptor = ArgumentCaptor.forClass(CalculationRequestDTO.class);
        Mockito.verify(dao).update(calculationCaptor.capture());

        CalculationRequestDTO completedCalculation = calculationCaptor.getValue();
        verifyUpdatedCalculation(completedCalculation,2.0,null);
    }

    @Test
    public void execute_completedIllegalCalculation() throws Exception {
        setDaoBehaviour("1/0");
        Mockito.when(calculator.calculateResult("1/0")).thenThrow(new DivisionByZeroException("Division by zero"));
        job.execute(context);

        ArgumentCaptor<CalculationRequestDTO> calculationCaptor = ArgumentCaptor.forClass(CalculationRequestDTO.class);
        Mockito.verify(dao).update(calculationCaptor.capture());

        CalculationRequestDTO completedCalculation = calculationCaptor.getValue();
        verifyUpdatedCalculation(completedCalculation,0.0,"Division by zero");
    }

    private void setDaoBehaviour(final String equation){
        List<CalculationRequestDTO> pendingCalculations = Collections.singletonList(new CalculationRequestDTO(equation,new Date()));
        Mockito.when(dao.getAllPendingCalculations()).thenReturn(pendingCalculations);
    }

    private void verifyUpdatedCalculation(final CalculationRequestDTO completedCalculation,final double calculationResult,final String errorMsg){
        assertThat(completedCalculation.getCalculationResult(),is(calculationResult));
        assertThat(completedCalculation.getErrorMsg(),is(errorMsg));
    }

}
