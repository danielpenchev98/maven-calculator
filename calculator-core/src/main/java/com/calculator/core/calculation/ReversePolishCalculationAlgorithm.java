package com.calculator.core.calculation;

import com.calculator.core.exceptions.BadInputException;
import com.calculator.core.exceptions.InvalidExpressionException;
import com.calculator.core.exceptions.InvalidParameterException;
import com.calculator.core.operators.ExpressionComponent;
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

    public double calculateExpression(final List<ExpressionComponent> expression) throws BadInputException {
        this.supplier.clear();
        List<ExpressionComponent> equationInRPN = formatToReversePolishNotation(expression);

        for (ExpressionComponent component : equationInRPN) {
            process(component);
        }

        if (hasErrorInTheCalculation()) {
            throw new InvalidExpressionException("Invalid equation. Logical error. There aren't enough operators");
        }
        return getNextNumberFromSupplier();
    }

    private List<ExpressionComponent> formatToReversePolishNotation(final List<ExpressionComponent> expression) {
        return parser.formatFromInfixToReversedPolishNotation(expression);
    }

    private void process(final ExpressionComponent component) throws BadInputException {
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
