package calculator;


import calculator.computation.ComputationalMachine;
import calculator.computation.EquationComponentFactory;
import calculator.computation.ReversePolishCalculationAlgorithm;
import calculator.exceptions.InvalidComponentException;
import calculator.exceptions.InvalidEquationException;
import calculator.inputcontrol.*;

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
        ComponentValidator componentValidator=new ComponentValidator();
        EquationStructureValidator structureValidator=new EquationStructureValidator();
        InputFormatter formatter = new InputFormatter(structureValidator,componentValidator);
        List<String> formattedInput = formatter.doFormat(equation);

        ReversePolishNotationParser specialParser=new ReversePolishNotationParser(componentValidator,new EquationComponentFactory());
        List<String> reversePolishFormatEquation = specialParser.formatFromInfixToReversedPolishNotation(formattedInput);

        ComputationalMachine calculator= new ComputationalMachine(new EquationComponentFactory());
        ReversePolishCalculationAlgorithm algorithm=new ReversePolishCalculationAlgorithm(calculator,componentValidator);

        return algorithm.calculateEquation(reversePolishFormatEquation);
    }
}