package calculator.computation;

public class Addition implements MathOperation {
    @Override
    public int compute(int first_number, int second_number) {
        return first_number+second_number;
    }
}
