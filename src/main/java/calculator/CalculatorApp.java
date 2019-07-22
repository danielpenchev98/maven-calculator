package calculator;


import calculator.computation.EquationComponent;
import calculator.computation.ReversePolishCalculationAlgorithm;
import calculator.exceptions.InvalidComponentException;
import calculator.exceptions.InvalidEquationException;
import calculator.inputcontrol.EquationStructureValidator;
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

        EquationStructureValidator structureValidator=new EquationStructureValidator();
        InputFormatter formatter = new InputFormatter(structureValidator);
        List<EquationComponent> formattedInput = formatter.doFormat(equation);

        ReversePolishNotationParser specialParser=new ReversePolishNotationParser();
        List<EquationComponent> reversePolishFormatEquation = specialParser.formatFromInfixToReversedPolishNotation(formattedInput);

        ReversePolishCalculationAlgorithm algorithm=new ReversePolishCalculationAlgorithm();

        return algorithm.calculateEquation(reversePolishFormatEquation);
    }
}