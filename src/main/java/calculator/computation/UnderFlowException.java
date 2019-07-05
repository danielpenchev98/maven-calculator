package calculator.computation;

/**
 * Exception, signalizing an arithmetic underflow
 */
public class UnderFlowException extends ArithmeticException {
    public UnderFlowException(final String message)
    {
        super(message);
    }
}
