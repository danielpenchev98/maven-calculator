package com.calculator.core.calculation;

import com.calculator.core.exceptions.InvalidEquationException;
import com.calculator.core.exceptions.InvalidParameterException;
import com.calculator.core.operators.EquationComponent;
import com.calculator.core.operators.MathArithmeticOperator;
import com.calculator.core.operators.NumberComponent;

import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;

public class ReversePolishCalculationAlgorithm implements CalculationAlgorithm {

    private final ReversePolishNotationParser parser;

    private final int NUMBERS_IN_SUPPLIER_AFTER_CALCULATION = 1;

    public ReversePolishCalculationAlgorithm()
    {
        this(new ReversePolishNotationParser());
    }

    public ReversePolishCalculationAlgorithm(final ReversePolishNotationParser parser)
    {
        this.parser=parser;
    }

    public double calculateEquation(final List<EquationComponent> equation) throws EmptyStackException, InvalidEquationException {

        List<EquationComponent> equationInRPN=formatToReversePolishNotation(equation);
        Stack<NumberComponent> numberSupplier=new Stack<>();

        for (EquationComponent component : equationInRPN) {
            process(component,numberSupplier);
        }

        if (hasErrorInTheCalculation(numberSupplier)) {
            throw new InvalidEquationException("Invalid equation. Logical error. There aren't enough operators");
        }

        return getNextNumber(numberSupplier);
    }

    private List<EquationComponent> formatToReversePolishNotation(final List<EquationComponent> equation)
    {
        return parser.formatFromInfixToReversedPolishNotation(equation);
    }

    private void process(final EquationComponent component,final Stack<NumberComponent> numberSupplier) {
        if (component instanceof NumberComponent) {
            numberSupplier.add((NumberComponent) component);
        } else if (component instanceof MathArithmeticOperator) {
            executeOperation((MathArithmeticOperator) component,numberSupplier);
        } else {
            throw new InvalidParameterException("Error. There shouldn't be any components different from numbers and math arithmetic operators");
        }
    }

    private void executeOperation(final MathArithmeticOperator operator,final Stack<NumberComponent> supplier) throws EmptyStackException {
        double rightNumber = getNextNumber(supplier);
        double leftNumber = getNextNumber(supplier);
        double result = operator.compute(leftNumber, rightNumber);
        supplier.add(new NumberComponent(String.valueOf(result)));
    }

    private double getNextNumber(final Stack<NumberComponent> supplier) {
        String number = supplier.pop().getValue();
        return Double.parseDouble(number);
    }

    private boolean hasErrorInTheCalculation(final Stack<NumberComponent> supplier)
    {
        return supplier.size()!=NUMBERS_IN_SUPPLIER_AFTER_CALCULATION;
    }

}
