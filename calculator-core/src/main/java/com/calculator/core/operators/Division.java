package com.calculator.core.operators;

import com.calculator.core.exceptions.DivisionByZeroException;

public class Division implements MathArithmeticOperator {

    private final static int PRIORITY=3;
    final static String SYMBOL="/";
    /**
     * @param firstNumber - first argument of the mathematical operation /
     * @param secondNumber - second argument of the mathematical operation /
     * @exception ArithmeticException - when a division on zero occurs
     * @return the result of the mathematical operation /
     * */
    @Override
    public double compute(final double firstNumber, final double secondNumber) throws DivisionByZeroException {
        if(secondNumber==0) {
            throw new DivisionByZeroException("Division by zero");
        }
        return firstNumber/secondNumber;
    }

    /**
     * @return the priority of the operator
     */
    @Override
    public int getPriority() {
        return Division.PRIORITY;
    }

    /**
     * @return if the operator is left associative
     */
    @Override
    public boolean isLeftAssociative() {
        return true;
    }

}
