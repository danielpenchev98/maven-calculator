package calculator.exceptions;

/**
 * Exception signalizing about an arithmetic overflow
 */
public class OverFlowException extends ArithmeticException {
    public OverFlowException(String message)
    {
        super(message);
    }
}
