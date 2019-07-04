package calculator.computation;


//should be added throws???
public interface MathOperation {
     int compute(final int first_number, final int second_number) throws InvalidMathematicalOperationException;
}
