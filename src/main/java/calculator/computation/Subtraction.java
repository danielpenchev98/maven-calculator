package calculator.computation;

public class Subtraction implements MathOperation {

    /**
     * @param first_number - first argument of the mathematical operation -
     * @param second_number - second argument of the mathematical operation -
     * @return the result of the mathematical operation -
     * */
    @Override
    public int compute(int first_number, int second_number) {
        return first_number-second_number;
    }
}
