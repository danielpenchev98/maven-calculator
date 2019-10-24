package com.calculator.webapp.quartz;

import com.calculator.core.CalculatorApp;
import com.calculator.webapp.db.dao.CalculatorDaoImpl;
import com.calculator.webapp.db.dto.CalculationRequestDTO;
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
    public void setUp() throws Exception{
        job=new PendingCalculationJob(calculator,dao);
        setBehaviourOfMockedDependencies();
    }

    @Test
    public void execute_completedCalculation() throws Exception{
        job.execute(context);

        ArgumentCaptor<CalculationRequestDTO> calculationCaptor = ArgumentCaptor.forClass(CalculationRequestDTO.class);
        Mockito.verify(dao).update(calculationCaptor.capture());

        CalculationRequestDTO completedCalculation = calculationCaptor.getValue();
        verifyUpdatedResponseMessage(completedCalculation);
    }

    private void setBehaviourOfMockedDependencies() throws Exception{
        List<CalculationRequestDTO> pendingCalculations = Collections.singletonList(new CalculationRequestDTO("1+1","PENDING",new Date()));
        Mockito.when(dao.getAllPendingCalculations()).thenReturn(pendingCalculations);
        Mockito.when(calculator.calculateResult("1+1")).thenReturn(2.0);
    }

    private void verifyUpdatedResponseMessage(final CalculationRequestDTO completedCalculation){
        assertThat(completedCalculation.getResponseMsg(),is("2.0"));
    }

}
