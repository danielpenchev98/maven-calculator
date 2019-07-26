package com.calculator.core;

import com.calculator.core.computation.EquationComponent;
import com.calculator.core.computation.EquationComponentFactory;
import com.calculator.core.computation.ReversePolishCalculationAlgorithm;
import com.calculator.core.exceptions.InvalidComponentException;
import com.calculator.core.exceptions.InvalidEquationException;
import com.calculator.core.exceptions.InvalidParameterException;
import com.calculator.core.inputprocessing.EquationStructureValidator;
import com.calculator.core.inputprocessing.InputFormatter;
import com.calculator.core.inputprocessing.ReversePolishNotationParser;

import java.util.List;

/**
 * Class which represents the calculator as a whole - uses the 3 main abstractions :
 * PrimalParser - used as a input formatter,
 * ComputationalMachine - used for calculation the operations* NumberSupplier - storage, used for calculating the equation,
 */

class CalculatorApp {

    private EquationStructureValidator structureValidator;
    private EquationComponentFactory factory;
    private InputFormatter formatter;
    private ReversePolishNotationParser parser;
    private ReversePolishCalculationAlgorithm algorithm;


    public CalculatorApp(final EquationStructureValidator structureValidator,final EquationComponentFactory factory,final InputFormatter formatter,final ReversePolishNotationParser parser,final ReversePolishCalculationAlgorithm algorithm)
    {
        this.structureValidator=structureValidator;
        this.factory=factory;
        this.formatter=formatter;
        this.parser=parser;
        this.algorithm=algorithm;
    }

    public CalculatorApp()
    {
        this.structureValidator=new EquationStructureValidator();
        this.factory=new EquationComponentFactory();
        this.formatter=new InputFormatter(structureValidator,factory);
        this.parser=new ReversePolishNotationParser();
        this.algorithm=new ReversePolishCalculationAlgorithm();
    }

    /**
     * Functions which formats and calculates the equation
     * @param equation - user input
     */
    double calculateResult(final String equation) throws InvalidEquationException, InvalidComponentException
    {
        List<EquationComponent> formattedInput = formatter.doFormat(equation);
        List<EquationComponent> reversePolishFormatEquation = parser.formatFromInfixToReversedPolishNotation(formattedInput);
        double result=0;
        try {
            result = algorithm.calculateEquation(reversePolishFormatEquation);
        } catch (InvalidParameterException sysError) {
            //TODO save them in log file probably
            System.out.println("hello");
        }
        return result;
    }
}