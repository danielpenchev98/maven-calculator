package calculator.computation;

import calculator.exceptions.OverFlowException;
import calculator.exceptions.UnderFlowException;

public class Division implements MathOperation {

    final static int PRIORITY=2;
    /**
     * @param first_number - first argument of the mathematical operation /
     * @param second_number - second argument of the mathematical operation /
     * @exception ArithmeticException - when a division on zero occurs
     * @return the result of the mathematical operation /
     * */
    @Override
    public double compute(final double first_number, final double second_number) throws ArithmeticException
    {
        if(second_number==0)
        {
            throw new ArithmeticException("It's illegal to divide on zero");
        }
        else if(Double.MAX_VALUE/first_number<1/second_number)
        {
            throw new OverFlowException("The result from the operation + is greater than the max value of the type Integer");
        }
        else if(Double.MIN_VALUE -first_number>second_number)
        {
            throw new UnderFlowException("The result from the operation + is lesser than the min value of the type Integer");
        }
        //for type Integer it's impossible to find OverFlow or UnderFlow
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
