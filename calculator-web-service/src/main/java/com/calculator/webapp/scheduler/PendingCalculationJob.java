package com.calculator.webapp.scheduler;

import com.calculator.core.CalculatorApp;
import com.calculator.core.exceptions.BadInputException;
import com.calculator.webapp.db.dao.EquationDaoImpl;
import com.calculator.webapp.db.dao.RequestDaoImpl;
import com.calculator.webapp.db.dto.CalculationRequestDTO;
import com.calculator.webapp.db.dto.CalculatorResponseDTO;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

import static com.calculator.webapp.db.dto.requeststatus.RequestStatus.COMPLETED;

public class PendingCalculationJob implements Job {

    private RequestDaoImpl requestDao;
    private EquationDaoImpl equationDao;
    private CalculatorApp calculator;

    private Logger logger = LoggerFactory.getLogger(PendingCalculationJob.class);


    public PendingCalculationJob(){
        this(new CalculatorApp(),new RequestDaoImpl(),new EquationDaoImpl());
    }

    PendingCalculationJob(final CalculatorApp calculator,final RequestDaoImpl requestDao,final EquationDaoImpl equationDao){
        this.calculator=calculator;
        this.requestDao=requestDao;
        this.equationDao=equationDao;
    }

    @Override
    public synchronized void execute(JobExecutionContext jobExecutionContext) {
        List<CalculationRequestDTO> pendingCalculations = requestDao.getAllPendingRequests();

        for(CalculationRequestDTO pendingCalculation : pendingCalculations){
            try {
                CalculatorResponseDTO response = equationDao.getItem(pendingCalculation.getEquation());
                if(response!=null){
                   continue;
                }
            } catch(Exception ex){
                logger.error("System error :" + Arrays.toString(ex.getStackTrace()));
                return;
            }
            completeCalculation(pendingCalculation.getEquation());

            pendingCalculation.setStatusCode(COMPLETED.getStatusCode());
            requestDao.update(pendingCalculation);
        }

    }

    private void completeCalculation(final String equation){
        CalculatorResponseDTO response = new CalculatorResponseDTO(equation);
        try {
            double result = calculateEquation(equation);
            response.setCalculationResult(result);
        } catch (BadInputException badRequest) {
            logger.warn("User error :" + Arrays.toString(badRequest.getStackTrace()));
            String userError = badRequest.getMessage();
            response.setErrorMsg(userError);
        } catch(Exception ex){
            logger.error("System error :" + Arrays.toString(ex.getStackTrace()));
            return;
        }

        equationDao.update(response);
    }

    private double calculateEquation(final String equation) throws Exception {
        return calculator.calculateResult(equation);
    }
}
