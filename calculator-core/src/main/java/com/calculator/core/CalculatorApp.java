package com.calculator.core;

import com.calculator.core.calculation.CalculationAlgorithm;
import com.calculator.core.inputformatting.InputFormatter;
import com.calculator.core.operators.EquationComponent;
import com.calculator.core.inputformatting.EquationFormatter;

import java.util.List;

class CalculatorApp {

    private final InputFormatter formatter;
    private final CalculationAlgorithm algorithm;


    CalculatorApp(final InputFormatter formatter, final CalculationAlgorithm algorithm)
    {
        this.formatter=formatter;
        this.algorithm=algorithm;
    }

    double calculateResult(final String equation) throws Exception
    {
        List<EquationComponent> formattedInput = formatter.doFormat(equation);
        return algorithm.calculateEquation(formattedInput);
    }
}