package calculator.computation;

public class Addition implements MathArithmeticOperator {

    private final static int PRIORITY=2;
    final static String SYMBOL="+";
    /**
     * @param firstNumber - first argument of the mathematical operation +
     * @param secondNumber - second argument of the mathematical operation +
     * @return the result of the mathematical operation +
     * */
    @Override
    public double compute(final double firstNumber,final double secondNumber)
    {
        return firstNumber+secondNumber;
    }

    //TODO The name is against the Clean code principle - should fix it
    /**
     * @return the priority of the operator
     */
    @Override
    public int getPriority() {
        return Addition.PRIORITY;
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
        return Addition.SYMBOL;
    }
}
