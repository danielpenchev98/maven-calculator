package calculator.exceptions;

/**
 * Exception, which signalizes for the lack of items in the storage
 */
public class OutOfItemsException extends Exception {
    public OutOfItemsException(final String message)
    {
        super(message);
    }
}
