package com.calculator.webapp.servletresponse;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

public class ServletResult {
    public String result;

    public ServletResult(final String result)
    {
        setCalculationResult(result);
    }

    @JsonSetter("result")
    public void setCalculationResult(final String result)
    {
        this.result=result;
    }

    @JsonGetter("result")
    public String getCalculationResult()
    {
        return this.result;
    }
}
