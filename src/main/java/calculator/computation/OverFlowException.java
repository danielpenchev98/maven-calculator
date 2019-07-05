package calculator.computation;

/**
 * Exception signalizing about an arithmetic overflow
 */
public class OverFlowException extends ArithmeticException {
    OverFlowException(String message)
    {
        super(message);
    }
}
