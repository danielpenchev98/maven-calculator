package com.calculator.core.exceptions;

public class InvalidParameterException extends RuntimeException {
    public InvalidParameterException(final String message)
    {
        super(message);
    }
}
