package com.calculator.core.calculation;

import com.calculator.core.exceptions.BadInputException;
import com.calculator.core.operators.ExpressionComponent;

import java.util.List;

public interface CalculationAlgorithm {
    double calculateExpression(final List<ExpressionComponent> components) throws BadInputException;
}
