package com.calculator.core.calculation;

import com.calculator.core.exceptions.BadInputException;
import com.calculator.core.exceptions.InvalidEquationException;
import com.calculator.core.exceptions.InvalidParameterException;
import com.calculator.core.operators.EquationComponent;
import com.calculator.core.operators.MathArithmeticOperator;
import com.calculator.core.operators.NumberComponent;

import java.util.List;
import java.util.Stack;

public class ReversePolishCalculationAlgorithm implements CalculationAlgorithm {

    private Stack<NumberComponent> supplier;

    private final ReversePolishNotationParser parser;

    private final int NUMBERS_IN_SUPPLIER_AFTER_CALCULATION = 1;

    public ReversePolishCalculationAlgorithm() {
        this(new ReversePolishNotationParser());
    }

    public ReversePolishCalculationAlgorithm(final ReversePolishNotationParser parser) {
        this.parser = parser;
        this.supplier= new Stack<>();
    }


    public double calculateEquation(final List<EquationComponent> equation) throws BadInputException {
        List<EquationComponent> equationInRPN = formatToReversePolishNotation(equation);

        for (EquationComponent component : equationInRPN) {
            process(component);
        }

        if (hasErrorInTheCalculation()) {
            throw new InvalidEquationException("Invalid equation. Logical error. There aren't enough operators");
        }
        return getNextNumberFromSupplier();
    }

    private List<EquationComponent> formatToReversePolishNotation(final List<EquationComponent> equation) {
        return parser.formatFromInfixToReversedPolishNotation(equation);
    }

    private void process(final EquationComponent component) throws BadInputException {
        if (component instanceof NumberComponent) {
            supplier.add((NumberComponent) component);
        } else if (component instanceof MathArithmeticOperator) {
            executeOperation((MathArithmeticOperator) component);
        } else {
            throw new InvalidParameterException("Error. There shouldn't be any components different from numbers and math arithmetic operators");
        }
    }

    private void executeOperation(final MathArithmeticOperator operator) throws BadInputException {
        double rightNumber = getNextNumberFromSupplier();
        double leftNumber = getNextNumberFromSupplier();
        double result = operator.compute(leftNumber, rightNumber);
        supplier.add(new NumberComponent(String.valueOf(result)));
    }

    private double getNextNumberFromSupplier() {
        String number = supplier.pop().getValue();
        return Double.parseDouble(number);
    }

    private boolean hasErrorInTheCalculation() {
        return supplier.size() != NUMBERS_IN_SUPPLIER_AFTER_CALCULATION;
    }
}
