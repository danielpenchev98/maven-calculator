package com.calculator.webapp.servletresponse;

public class ServletError {
    private int errorCode;
    private String message;

    public ServletError(final int errorCode, final String message) {
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
