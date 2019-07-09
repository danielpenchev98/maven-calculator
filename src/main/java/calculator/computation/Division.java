package calculator.computation;

public class Division implements MathOperation {

    final static int PRIORITY=2;
    /**
     * @param first_number - first argument of the mathematical operation /
     * @param second_number - second argument of the mathematical operation /
     * @exception ArithmeticException - when a division on zero occurs
     * @return the result of the mathematical operation /
     * */
    @Override
    public int compute(final int first_number, final int second_number) throws ArithmeticException
    {
        if(second_number==0)
        {
            throw new ArithmeticException("It's illegal to divide on zero");
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
