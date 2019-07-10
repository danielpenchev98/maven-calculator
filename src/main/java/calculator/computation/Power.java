package calculator.computation;

public class Power implements MathOperation {

    private final static int PRIORITY=4;
    /**
     * @param first_number  - the first parameter of the mathematical operation
     * @param second_number - the second parameter of the mathematical operation
     * @return the result of the mathematical operation
     * @throws ArithmeticException - if some type of error occurs like division on zero
     */

    //TODO to change everything to double
    @Override
    public double compute(final double first_number, final double second_number) throws ArithmeticException {
        return Math.pow(first_number,second_number);
    }

    /**
     * @return the priority of the operator
     */
    @Override
    public int getPriority() {
        return Power.PRIORITY;
    }

    /**
     * @return if the operator is left associative
     */
    @Override
    public boolean isLeftAssociative() {
        return false;
    }
}
