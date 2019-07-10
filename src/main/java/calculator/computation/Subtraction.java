package calculator.computation;

public class Subtraction implements MathOperation {

    private final static int PRIORITY=2;
    /**
     * @param first_number - first argument of the mathematical operation -
     * @param second_number - second argument of the mathematical operation -
     * @return the result of the mathematical operation -
     * */
    @Override
    public double compute(final double first_number, final double second_number) throws ArithmeticException {

        return first_number-second_number;
    }

    /**
     * @return the priority of the operator
     */
    @Override
    public int getPriority() {
        return Subtraction.PRIORITY;
    }

    /**
     * @return if the operator is left associative
     */
    @Override
    public boolean isLeftAssociative() {
        return true;
    }

}
