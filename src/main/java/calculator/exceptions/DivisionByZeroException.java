package calculator.exceptions;

public class DivisionByZeroException extends ArithmeticException {
    public DivisionByZeroException(final String message)
    {
        super(message);
    }
}
