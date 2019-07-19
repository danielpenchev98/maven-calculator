package calculator;


import calculator.computation.ComputationalMachine;
import calculator.computation.MathOperatorFactory;
import calculator.computation.ReversePolishCalculationAlgorithm;
import calculator.exceptions.InvalidComponentException;
import calculator.exceptions.InvalidEquationException;
import calculator.inputcontrol.EquationValidator;
import calculator.inputcontrol.InputFormatter;
import calculator.inputcontrol.ReversePolishNotationParser;

import java.util.List;

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
        List<String> formattedInput = formatter.doFormat(equation);
        EquationValidator validator=new EquationValidator();

        validator.validateEquation(formattedInput);


        ReversePolishNotationParser specialParser=new ReversePolishNotationParser(validator,new MathOperatorFactory());
        List<String> reversePolishFormatEquation = specialParser.formatFromInfixToReversedPolishNotation(formattedInput);

        ComputationalMachine calculator= new ComputationalMachine(new MathOperatorFactory());
        ReversePolishCalculationAlgorithm algorithm=new ReversePolishCalculationAlgorithm(calculator,validator);

        return algorithm.calculateEquation(reversePolishFormatEquation);
    }
}