package com.calculator.webapp.restresponse;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

public class CalculationError {
    private int errorCode;
    private String message;

    @JsonCreator
    public CalculationError(@JsonProperty("errorCode") final int errorCode, @JsonProperty("message") final String message) {
        setErrorCode(errorCode);
        setMessage(message);
    }

    @JsonSetter("errorCode")
    public void setErrorCode(final int errorCode) {
        this.errorCode = errorCode;
    }

    @JsonGetter("errorCode")
    public int getErrorCode() {
        return errorCode;
    }

    @JsonSetter("message")
    public void setMessage(final String message) {
        this.message = message;
    }

    @JsonGetter("message")
    public String getMessage() {
        return message;
    }
}