package com.calculator.webapp.servletresponse;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;

@JsonPropertyOrder({"errorCode", "message"})
public class ServletError {
    private int errorCode;
    private String message;

    public ServletError(final int errorCode, final String message) {
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
