package com.calculator.webapp.restresponse;

public class CalculationError {
    private int errorCode;
    private String message;

    public CalculationError(final int errorCode,final String message) {
        setErrorCode(errorCode);
        setMessage(message);
    }

    public void setErrorCode(final int errorCode) {
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
