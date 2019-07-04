package calculator.computation;

/**
 * Exception signalizing about a logical problem during the computation of the operation
 */
public class InvalidMathematicalOperationException extends Exception {
    public InvalidMathematicalOperationException(String message)
    {
        super(message);
    }
}
