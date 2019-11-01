package com.calculator.webapp.quartz;

import com.calculator.core.CalculatorApp;
import com.calculator.webapp.db.dao.CalculatorDaoImpl;
import com.calculator.webapp.db.dto.CalculationRequestDTO;
import org.quartz.*;

import java.util.List;

import static com.calculator.webapp.db.dto.requeststatus.RequestStatus.COMPLETED;

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
        List<CalculationRequestDTO> pendingCalculations = dao.getAllPendingCalculations();

        for(CalculationRequestDTO pendingCalculation : pendingCalculations){
            completeCalculation(pendingCalculation);
        }

    }

    private void completeCalculation(final CalculationRequestDTO calculation){
        try {
            double result = calculateEquation(calculation.getEquation());
            calculation.setCalculationResult(result);
        } catch (Exception e) {
            String errorMsg = e.getMessage();
            calculation.setErrorMsg(errorMsg);
        }

        calculation.setStatusCode(COMPLETED.getStatusCode());
        dao.update(calculation);
    }


    private double calculateEquation(final String equation) throws Exception {
        return calculator.calculateResult(equation);
    }
}
