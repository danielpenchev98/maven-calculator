package calculator;


import calculator.computation.*;
import calculator.exceptions.InvalidOperatorException;
import calculator.inputControl.ReversePolishNotationParser;
import calculator.inputControl.*;
import calculator.inputControl.RPParserTriggersTest.ReversePolishComponentTriggerFactory;

import java.util.EmptyStackException;

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

        MathArithmeticOperatorFactory arithmeticOperatorFactory=new MathArithmeticOperatorFactory();

        ReversePolishComponentTriggerFactory operatorFactory=new ReversePolishComponentTriggerFactory(validator,arithmeticOperatorFactory);
        ReversePolishNotationParser specialParser=new ReversePolishNotationParser(operatorFactory);
        String reversePolishFormatEquation;
        try {
            reversePolishFormatEquation = specialParser.formatFromInfixToReversedPolishNotation(formattedInput);
        }
        catch (EmptyStackException problemWithReversePolishParser)
        {
            //TODO should save it in log file for the developers - it shouldn't even happen at this point
            System.out.println("Please try again");
            return;
        }
        catch(InvalidOperatorException exc)
        {
            //should save it in log file for the developers
            //this error should even happen at this point of the program
            System.out.println("Please try again");
            return;
        }

        String[] splitInput=reversePolishFormatEquation.split(" ");

        ComputationalMachine calculator= new ComputationalMachine(arithmeticOperatorFactory);
        ReversePolishCalculationAlgorithm algorithm=new ReversePolishCalculationAlgorithm(calculator,validator);

        try {
            double finalResult=algorithm.calculateEquation(splitInput);
            System.out.println(finalResult);
        }
        catch (EmptyStackException noItems)
        {
            System.out.println("Invalid equation. Not enough Numbers");
        }
        catch(Exception logicalError) {
            System.out.println(logicalError.getMessage());
        }

    }

}
