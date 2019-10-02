package com.calculator.webapp.test.pageobjects.webclient;


public class CalculatorResultPojo {
    private String result;

    public  CalculatorResultPojo() { }

    public CalculatorResultPojo(final String result)
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
