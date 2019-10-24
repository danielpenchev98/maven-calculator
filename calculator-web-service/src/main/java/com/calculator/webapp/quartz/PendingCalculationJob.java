package com.calculator.webapp.quartz;

import com.calculator.core.CalculatorApp;
import com.calculator.webapp.db.dao.CalculatorDaoImpl;
import com.calculator.webapp.db.dto.CalculatorResponseDTO;
import org.quartz.*;

import java.util.List;

public class PendingCalculationJob implements Job {

    private CalculatorDaoImpl dao;
    private CalculatorApp calculator;

    public PendingCalculationJob(){
        this(new CalculatorApp(),new CalculatorDaoImpl());
    }

    PendingCalculationJob(final CalculatorApp calculator,final CalculatorDaoImpl dao){
        this.calculator=calculator;
        this.dao=dao;
    }


    @Override
    public synchronized void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        List<CalculatorResponseDTO> pendingCalculations = dao.getAllPendingCalculations();

        for(CalculatorResponseDTO pendingCalculation : pendingCalculations){
            completeCalculation(pendingCalculation);
        }

    }

    private void setUpJob(){
        dao.clearEntityManagerContext();
    }

    private void completeCalculation(final CalculatorResponseDTO calculation){
        String result ="";
        try {
            result = calculateEquation(calculation.getEquation());
        } catch (Exception e) {
            result = e.getMessage();
        }


        calculation.setResponseMsg(result);
        dao.update(calculation);
    }

    private String calculateEquation(final String equation) throws Exception {
        double result = calculator.calculateResult(equation);
        return String.valueOf(result);
    }
}