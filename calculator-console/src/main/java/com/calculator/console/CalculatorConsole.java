package com.calculator.console;

import com.calculator.core.CalculatorApp;
import com.calculator.core.exceptions.DivisionByZeroException;
import com.calculator.core.exceptions.InvalidComponentException;
import com.calculator.core.exceptions.InvalidEquationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class CalculatorConsole {

        private static final int VALID_NUMBER_OF_ARGUMENTS=1;
        private static final int POSITION_OF_ARGUMENT=0;

        private static Logger logger= LogManager.getLogger(CalculatorConsole.class.getName());

        public static void main(String[] argc)
        {
            if (hasInvalidNumberOfArguments(argc.length)) {
                System.out.print("Invalid number of arguments");
                return;
            }

            CalculatorApp application = new CalculatorApp();

            try {
                System.out.print(application.calculateResult(argc[POSITION_OF_ARGUMENT]));
            } catch (InvalidEquationException ex) {
                logger.info("Problem with the structure of equation :\n"+ex);
                System.out.print("Problem with the structure of equation :" + ex.getMessage());
            } catch (InvalidComponentException ex) {
                logger.info("Problem with a component of equation :\n"+ex);
                System.out.print("Problem with a component of equation :" + ex.getMessage());
            } catch (DivisionByZeroException ex) {
                logger.info("Arithmetic error :\n"+ex);
                System.out.print("Arithmetic error :" + ex.getMessage());
            } catch (Exception sysError) {
                logger.error("Problem with the application :\n"+sysError);
                System.out.print("Problem with the application. Please try again");
            }
        }

        private static boolean hasInvalidNumberOfArguments(final int argLength) {
            return VALID_NUMBER_OF_ARGUMENTS != argLength;
        }
}
