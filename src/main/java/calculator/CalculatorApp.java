package calculator;

import calculator.computation.*;
import calculator.exceptions.InvalidComponentException;
import calculator.exceptions.InvalidEquationException;
import calculator.inputcontrol.ReversePolishNotationParser;
import calculator.inputcontrol.*;

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
    double calculateResult(final String equation) throws InvalidEquationException, InvalidComponentException
    {
        InputFormatter formatter = new InputFormatter();
        String formattedInput = formatter.doFormat(equation);
        EquationValidator validator=new EquationValidator();

        validator.validateEquation(formattedInput);


        MathOperatorFactory operatorFactory=new MathOperatorFactory();
        ReversePolishNotationParser specialParser=new ReversePolishNotationParser(validator,operatorFactory);
        String reversePolishFormatEquation;
        reversePolishFormatEquation = specialParser.formatFromInfixToReversedPolishNotation(formattedInput);

        MathArithmeticOperatorFactory arithmeticOperatorFactory=new MathArithmeticOperatorFactory();
        ComputationalMachine calculator= new ComputationalMachine(arithmeticOperatorFactory);
        ReversePolishCalculationAlgorithm algorithm=new ReversePolishCalculationAlgorithm(calculator,validator);

        double finalResult=algorithm.calculateEquation(reversePolishFormatEquation);
        return finalResult;

    }
}