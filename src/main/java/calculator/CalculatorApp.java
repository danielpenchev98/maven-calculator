package calculator;


import calculator.computation.ComputationalMachine;
import calculator.container.OutOfItemsException;
import calculator.inputControl.InputParser;
import calculator.inputControl.InvalidTypeOfEquationComponent;

import java.util.List;

/**
 * Class which represents the calculator as a whole - uses the 3 main abstractions :
 * InputParser - used as a input formatter,
 * NumberSupplier - storage, used for calculating the equation,
 * ComputationalMachine - used for calculation the operations
 */
class CalculatorApp {

    /**
     * Help function, which uses special Parses to format the input
     * @param input - input to be formatted
     * @param parser - object, which will format the input
     * @return formatted input
     * @throws InvalidTypeOfEquationComponent - if an invalid component of an equation is found
     * @throws NullPointerException - if the input is the empty string
     */
    private String[] parseInput(String input, InputParser parser) throws InvalidTypeOfEquationComponent//, NullPointerException
    {
        String[] splitInput= parser.formatAndValidateInput(input);
        if(splitInput==null)
        {
            throw new NullPointerException("The input consists of 0 or 1 components");
        }
        return splitInput;
    }


    /**
     * Functions which formats and calculates the equation
     * @param equation - user input
     */
    void processEquationAndCalculateResult(final String equation)
    {
        InputParser parser= InputParser.getInstance();
        String[] splitInput;
        try {
            splitInput=parseInput(equation,parser);
        }
        catch(InvalidTypeOfEquationComponent invalidType)
        {
            System.out.println("Invalid equation. The input should consists of numbers and supported mathematical operations");
            return;
        }
        catch(NullPointerException nullPtr)
        {
            System.out.println(nullPtr.getMessage());
            return;
        }


        ComputationalMachine calculator= ComputationalMachine.getInstance();
        ReversePolishCalculationAlgorithm algorithm=ReversePolishCalculationAlgorithm.getInstance(calculator,parser);
        try {
            int finalResult=algorithm.calculateEquation(splitInput);
            System.out.printf("%d\n",finalResult);
        }
        catch (OutOfItemsException noItems)
        {
            System.out.println("Invalid equation ");
        }
        catch(Exception logicalError) {
            System.out.println(logicalError.getMessage());
        }

    }
}
