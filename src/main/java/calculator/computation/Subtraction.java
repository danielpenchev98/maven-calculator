package calculator.computation;

import calculator.exceptions.OverFlowException;
import calculator.exceptions.UnderFlowException;

public class Subtraction implements MathOperation {

    private final static int PRIORITY=1;
    /**
     * @param first_number - first argument of the mathematical operation -
     * @param second_number - second argument of the mathematical operation -
     * @return the result of the mathematical operation -
     * */
    @Override
    public int compute(int first_number, int second_number) throws ArithmeticException {

        if((double) Integer.MAX_VALUE +second_number<first_number)
        {
            throw new OverFlowException("The result from the operation + is greater than the max value of the type Integer");
        }
        else if((double) Integer.MIN_VALUE + second_number>first_number)
        {
            throw new UnderFlowException("The result from the operation + is lesser than the min value of the type Integer");
        }
        return first_number-second_number;
    }

    /**
     * @return the priority of the operator
     */
    @Override
    public int getPriority() {
        return Subtraction.PRIORITY;
    }

    /**
     * @return if the operator is left associative
     */
    @Override
    public boolean isLeftAssociative() {
        return true;
    }

}
