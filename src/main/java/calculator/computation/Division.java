package calculator.computation;

import calculator.exceptions.DivisionByZeroException;

public class Division implements MathOperation {

    final static int PRIORITY=3;
    /**
     * @param first_number - first argument of the mathematical operation /
     * @param second_number - second argument of the mathematical operation /
     * @exception ArithmeticException - when a division on zero occurs
     * @return the result of the mathematical operation /
     * */
    @Override
    public double compute(final double first_number, final double second_number) throws DivisionByZeroException
    {
        if(second_number==0)
        {
            throw new DivisionByZeroException("It's illegal to divide on zero");
        }
        return first_number/second_number;
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
