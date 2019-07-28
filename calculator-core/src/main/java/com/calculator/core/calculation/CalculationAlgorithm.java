package com.calculator.core.calculation;

import com.calculator.core.operators.EquationComponent;
import com.calculator.core.exceptions.InvalidEquationException;

import java.util.List;

public interface CalculationAlgorithm {
    double calculateEquation(final List<EquationComponent> components) throws InvalidEquationException;
}
