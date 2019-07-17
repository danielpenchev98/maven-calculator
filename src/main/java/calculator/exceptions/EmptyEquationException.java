package calculator.exceptions;

public class EmptyEquationException extends InvalidEquationException {
    public EmptyEquationException(final String message)
    {
        super(message);
    }
}
