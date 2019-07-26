package com.calculator.core;

import com.calculator.core.computation.EquationComponentFactory;
import com.calculator.core.computation.ReversePolishCalculationAlgorithm;
import com.calculator.core.exceptions.DivisionByZeroException;
import com.calculator.core.exceptions.InvalidComponentException;
import com.calculator.core.exceptions.InvalidEquationException;
import com.calculator.core.inputprocessing.EquationStructureValidator;
import com.calculator.core.inputprocessing.InputFormatter;
import com.calculator.core.inputprocessing.ReversePolishNotationParser;

public class CalculatorAppConsole {
    public static void main(String[] argc)
    {
        InputFormatter formatter=new InputFormatter(new EquationStructureValidator(),new EquationComponentFactory());
        CalculatorApp application=new CalculatorApp(formatter,new ReversePolishNotationParser(),new ReversePolishCalculationAlgorithm());

        try {
            System.out.print(application.calculateResult(argc[0]));
        }
        catch (InvalidEquationException ex)
        {
            System.out.print("Problem with the structure of equation :"+ex.getMessage());
        }
        catch(InvalidComponentException ex)
        {
            System.out.print("Problem with a component of equation :"+ex.getMessage());
        }
        catch(DivisionByZeroException ex)
        {
            System.out.print("Arithmetic error :"+ex.getMessage());
        }
        catch (Exception sysError)
        {
            //TODO to log the system trace
           System.out.print("Problem with the application");
        }
    }
}
