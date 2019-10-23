package com.calculator.webapp.quartz;

import com.calculator.core.CalculatorApp;
import com.calculator.webapp.db.dao.CalculatorDaoImpl;
import com.calculator.webapp.db.dto.CalculatorResponseDTO;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.quartz.JobDataMap;
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
        job=new PendingCalculationJob();
        setBehaviourOfMockedDependencies();
    }

    @Test
    public void execute_completedCalculation() throws Exception{
        job.execute(context);

        ArgumentCaptor<CalculatorResponseDTO> calculationCaptor = ArgumentCaptor.forClass(CalculatorResponseDTO.class);
        Mockito.verify(dao).update(calculationCaptor.capture());

        CalculatorResponseDTO completedCalculation = calculationCaptor.getValue();
        verifyUpdatedResponseMessage(completedCalculation);
    }

    private void setBehaviourOfMockedDependencies() throws Exception{
        List<CalculatorResponseDTO> pendingCalculations = Collections.singletonList(new CalculatorResponseDTO("1+1", "Not evaluated", new Date()));
        Mockito.when(context.getMergedJobDataMap()).thenReturn(getJobDataMap());
        Mockito.when(dao.getAllPendingCalculations()).thenReturn(pendingCalculations);
        Mockito.when(calculator.calculateResult("1+1")).thenReturn(2.0);
    }

    private void verifyUpdatedResponseMessage(final CalculatorResponseDTO completedCalculation){
        assertThat(completedCalculation.getResponseMsg(),is("2.0"));
    }

    private JobDataMap getJobDataMap(){
        JobDataMap map = new JobDataMap();
        map.put("dao",dao);
        map.put("calculator",calculator);
        return map;
    }

}
