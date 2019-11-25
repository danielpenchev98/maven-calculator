package com.calculator.core;

import com.calculator.core.calculation.CalculationAlgorithm;
import com.calculator.core.calculation.ReversePolishCalculationAlgorithm;
import com.calculator.core.exceptions.BadInputException;
import com.calculator.core.inputformatting.ExpressionFormatter;
import com.calculator.core.inputformatting.InputFormatter;
import com.calculator.core.operators.ExpressionComponent;

import java.util.List;

public class CalculatorApp {

    private final InputFormatter formatter;
    private final CalculationAlgorithm algorithm;

    public CalculatorApp(final InputFormatter formatter, final CalculationAlgorithm algorithm) {
        this.formatter = formatter;
        this.algorithm = algorithm;
    }

    public CalculatorApp() {
        this(new ExpressionFormatter(), new ReversePolishCalculationAlgorithm());
    }

    public double calculateResult(final String expression) throws BadInputException {
        List<ExpressionComponent> formattedInput = formatter.doFormat(expression);
        return algorithm.calculateExpression(formattedInput);
    }
}