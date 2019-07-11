package calculator.computation;


public class Multiplication implements MathArithmeticOperator {

    private final static int PRIORITY=3;
    private final static String SYMBOL="*";
    /**
     * @param firstNumber - first argument of the mathematical operation *
     * @param secondNumber - second argument of the mathematical operation *
     * @return the result of the mathematical operation *
     * */
    @Override
    public double compute(final double firstNumber,final double secondNumber) throws ArithmeticException
    {
        return firstNumber*secondNumber;
    }

    /**
     * @return the priority of the operator
     */
    @Override
    public int getPriority() {
        return Multiplication.PRIORITY;
    }

    /**
     * @return if the operator is left associative
     */
    @Override
    public boolean isLeftAssociative() {
        return true;
    }

    /**
     * @return get the special symbol of operator
     */
    @Override
    public String getSymbol() {
        return Multiplication.SYMBOL;
    }


}
