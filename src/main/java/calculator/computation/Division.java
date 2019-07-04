package calculator.computation;

public class Division implements MathOperation {
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
