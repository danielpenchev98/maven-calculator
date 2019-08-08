package com.calculator.console;

import com.calculator.core.CalculatorApp;
import com.calculator.core.exceptions.DivisionByZeroException;
import com.calculator.core.exceptions.InvalidComponentException;
import com.calculator.core.exceptions.InvalidEquationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.logging.FileHandler;

public class CalculatorConsole {

        private static final int VALID_NUMBER_OF_ARGUMENTS=1;
        private static final int POSITION_OF_ARGUMENT=0;

        private static Logger logger= LogManager.getLogger(CalculatorConsole.class);

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
                System.out.print("Problem with the structure of equation :" + ex.getMessage());
            } catch (InvalidComponentException ex) {
                System.out.print("Problem with a component of equation :" + ex.getMessage());
            } catch (DivisionByZeroException ex) {
                System.out.print("Arithmetic error :" + ex.getMessage());
            } catch (Exception sysError) {

                createErrorLog(sysError);
                System.out.print("Problem with the application");
            }
        }

        private static boolean hasInvalidNumberOfArguments(final int argLength) {
            return VALID_NUMBER_OF_ARGUMENTS != argLength;
        }

        private static void createErrorLog(Exception sysError)
        {
            /*try
            {
            FileHandler fh=new FileHandler("./errorLog.log",true);
            logger.addHandler(fh);
            logger.error("Problem with the application");
            }
            catch(Exception ex)
            {

            }*/
        }
}
