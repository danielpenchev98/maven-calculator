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

class CalculatorApp {

    private InputFormatter formatter;
    private ReversePolishNotationParser parser;
    private ReversePolishCalculationAlgorithm algorithm;


    public CalculatorApp(final InputFormatter formatter,final ReversePolishNotationParser parser,final ReversePolishCalculationAlgorithm algorithm)
    {
        this.formatter=formatter;
        this.parser=parser;
        this.algorithm=algorithm;
    }


    double calculateResult(final String equation) throws InvalidEquationException, InvalidComponentException
    {
        List<EquationComponent> formattedInput = formatter.doFormat(equation);
        List<EquationComponent> reversePolishFormatEquation = parser.formatFromInfixToReversedPolishNotation(formattedInput);
        return algorithm.calculateEquation(reversePolishFormatEquation);
    }
}