package com.calculator.webapp.restresponse;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

public class CalculationResult {
    private String result;

    public CalculationResult(final String result)
    {
        setResult(result);
    }

    @JsonSetter("result")
    public void setResult(final String result)
    {
        this.result=result;
    }

    @JsonGetter("result")
    public String getResult() {
        return this.result;
    }
}