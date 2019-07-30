package com.calculator.core;

import com.calculator.core.calculation.CalculationAlgorithm;
import com.calculator.core.inputformatting.InputFormatter;
import com.calculator.core.operators.EquationComponent;
import java.util.List;

public class CalculatorApp {

    private final InputFormatter formatter;
    private final CalculationAlgorithm algorithm;


    public CalculatorApp(final InputFormatter formatter, final CalculationAlgorithm algorithm)
    {
        this.formatter=formatter;
        this.algorithm=algorithm;
    }

    public double calculateResult(final String equation) throws Exception
    {
        List<EquationComponent> formattedInput = formatter.doFormat(equation);
        return algorithm.calculateEquation(formattedInput);
    }
}