package calculator.computation;

public class Division implements MathOperation {

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
}
