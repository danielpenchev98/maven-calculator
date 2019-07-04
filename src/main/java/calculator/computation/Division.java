package calculator.computation;

public class Division implements MathOperation {

    /**
     * @param first_number - first argument of the mathematical operation /
     * @param second_number - second argument of the mathematical operation /
     * @exception InvalidMathematicalOperationException - when a division on zero occurs
     * @return the result of the mathematical operation /
     * */
    @Override
    public int compute(int first_number, int second_number) throws InvalidMathematicalOperationException
    {
        if(second_number==0)
        {
            throw new InvalidMathematicalOperationException("It's illegal to divide on zero");
        }
        return first_number/second_number;
    }
}
