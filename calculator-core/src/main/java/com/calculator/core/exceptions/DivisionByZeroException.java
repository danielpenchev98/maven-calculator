package com.calculator.core.exceptions;

public class DivisionByZeroException extends BadInputException {
    public DivisionByZeroException(final String message)
    {
        super(message);
    }
}
