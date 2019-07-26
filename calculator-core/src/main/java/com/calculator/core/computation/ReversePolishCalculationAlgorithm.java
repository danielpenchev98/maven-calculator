package com.calculator.core.computation;

import com.calculator.core.exceptions.InvalidEquationException;
import com.calculator.core.exceptions.InvalidParameterException;

import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;

public class ReversePolishCalculationAlgorithm {

    private Stack<NumberComponent> supplier;

    public ReversePolishCalculationAlgorithm()
    {
        supplier=new Stack<>();
    }

    public double calculateEquation(final List<EquationComponent> components) throws EmptyStackException, InvalidEquationException {
        for (EquationComponent component : components) {
            process(component);
        }

        if (supplier.size() != 1) {
            throw new InvalidEquationException("Invalid equation. Logical error. There aren't enough operators");
        }

        return getNextNumberFromSupplier();
    }

    private void process(final EquationComponent component) {
        if (component instanceof NumberComponent) {
            supplier.add((NumberComponent) component);
        } else if (component instanceof MathArithmeticOperator) {
            executeOperation((MathArithmeticOperator) component);
        } else {
            throw new InvalidParameterException("Error. There shouldn't be any components different from numbers and math arithmetic operators");
        }
    }

    private void executeOperation(final MathArithmeticOperator operator) throws EmptyStackException {
        double rightNumber = getNextNumberFromSupplier();
        double leftNumber = getNextNumberFromSupplier();
        double result = operator.compute(leftNumber, rightNumber);
        supplier.add(new NumberComponent(String.valueOf(result)));
    }

    private double getNextNumberFromSupplier() {
        String number = supplier.pop().getValue();
        return Double.valueOf(number);
    }
}