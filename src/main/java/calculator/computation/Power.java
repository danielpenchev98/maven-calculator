package calculator.computation;

public class Power implements MathArithmeticOperator {

    private final static int PRIORITY=4;
    final static String SYMBOL="^";
    /**
     * @param firstNumber  - the first parameter of the mathematical operation
     * @param secondNumber - the second parameter of the mathematical operation
     * @return the result of the mathematical operation
     * @throws ArithmeticException - if some type of error occurs like division on zero
     */

    //TODO to change everything to double
    @Override
    public double compute(final double firstNumber, final double secondNumber) throws ArithmeticException {
        return Math.pow(firstNumber,secondNumber);
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

    /**
     * @return get the special symbol of operator
     */
    @Override
    public String getSymbol() {
        return Power.SYMBOL;
    }

    @Override
    public boolean equals(Object obj)
    {
        return obj instanceof Power;
    }
}
