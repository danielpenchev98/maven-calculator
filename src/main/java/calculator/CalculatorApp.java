package calculator;


import calculator.computation.ComputationalMachine;
import calculator.computation.ReversePolishCalculationAlgorithm;
import calculator.exceptions.OutOfItemsException;
import calculator.inputControl.ReversePolishNotationParser;
import calculator.inputControl.*;

/**
 * Class which represents the calculator as a whole - uses the 3 main abstractions :
 * PrimalParser - used as a input formatter,

 * ComputationalMachine - used for calculation the operations* NumberSupplier - storage, used for calculating the equation,
 */
class CalculatorApp {


    /**
     * Functions which formats and calculates the equation
     * @param equation - user input
     */
    void processEquationAndCalculateResult(final String equation)
    {
        PrimalParser parser= new PrimalParser();
        String formattedInput=parser.formatInput(equation);
        EquationValidator validator=new EquationValidator();
        try
        {
            validator.validateEquation(formattedInput);
        }
        //TODO maybe multiple catches is better
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
            return;
        }

        ReversePolishNotationParser specialParser=new ReversePolishNotationParser();
        String reversePolishFormatEquation=specialParser.formatFromInfixToReversedPolishNotation(formattedInput);

        String[] splitInput=reversePolishFormatEquation.split(" ");

        ComputationalMachine calculator= ComputationalMachine.getInstance();
        ReversePolishCalculationAlgorithm algorithm=new ReversePolishCalculationAlgorithm(calculator,validator);

        try {
            double finalResult=algorithm.calculateEquation(splitInput);
            System.out.println(finalResult);
        }
        catch (OutOfItemsException noItems)
        {
            System.out.println("Invalid equation. Not enough Numbers");
        }
        catch(Exception logicalError) {
            System.out.println(logicalError.getMessage());
        }

    }
}
