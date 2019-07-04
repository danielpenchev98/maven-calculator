package calculator.computation;

public class Subtraction implements MathOperation {
    @Override
    public int compute(int first_number, int second_number) {
        return first_number-second_number;
    }
}
