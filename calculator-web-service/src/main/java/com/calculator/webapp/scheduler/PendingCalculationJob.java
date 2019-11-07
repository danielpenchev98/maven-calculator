package com.calculator.webapp.scheduler;

import com.calculator.core.CalculatorApp;
import com.calculator.core.exceptions.BadInputException;
import com.calculator.webapp.db.dao.CalculatorDaoImpl;
import com.calculator.webapp.db.dto.CalculationRequestDTO;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.BadRequestException;
import java.util.List;

import static com.calculator.webapp.db.dto.requeststatus.RequestStatus.COMPLETED;

public class PendingCalculationJob implements Job {

    private CalculatorDaoImpl dao;
    private CalculatorApp calculator;

    private Logger logger = LoggerFactory.getLogger(PendingCalculationJob.class);


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
        } catch (BadInputException badRequest) {
            logger.warn("User error :" + badRequest.getStackTrace());
            String userError = badRequest.getMessage();
            calculation.setErrorMsg(userError);
        } catch(Exception ex){
            logger.error("System error :" + ex.getStackTrace());
            return;
        }

        calculation.setStatusCode(COMPLETED.getStatusCode());
        dao.update(calculation);
    }


    private double calculateEquation(final String equation) throws Exception {
        return calculator.calculateResult(equation);
    }
}
