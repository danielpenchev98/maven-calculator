package com.calculator.webapp.restresponse;

public class CalculationResult {
    private String result;

    public CalculationResult(final String result)
    {
        setResult(result);
    }

    public void setResult(final String result)
    {
        this.result=result;
    }

    public String getResult() {
        return this.result;
    }
}