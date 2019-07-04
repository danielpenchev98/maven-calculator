package calculator.computation;

//should check if it overflows
public class Multiplication implements MathOperation {
    @Override
    public int compute(int first_number, int second_number) {
        return first_number*second_number;
    }
}
