package calculator.container;

public class OutOfItemsException extends Exception {
    public OutOfItemsException(String message)
    {
        super(message);
    }
}
