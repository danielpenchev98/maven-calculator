package calculator.computationTests;


public class Addition implements MathOperation {

    /**
     * @param first_number - first argument of the mathematical operation +
     * @param second_number - second argument of the mathematical operation +
     * @return the result of the mathematical operation +
     * */
    @Override
    public int compute(final int first_number,final int second_number) throws ArithmeticException
    {
        if((double) Integer.MAX_VALUE -first_number<second_number)
        {
           throw new OverFlowException("The result from the operation + is greater than the max value of the type Integer");
        }
        else if((double) Integer.MIN_VALUE -first_number>second_number)
        {
           throw new UnderFlowException("The result from the operation + is lesser than the min value of the type Integer");
        }
        return first_number+second_number;
    }
}
