package com.calculator.webapp.servletresponse;


public class ServletResult {
    private String result;

    public ServletResult(final String result)
    {
        setResult(result);
    }

    public void setResult(final String result)
    {
        this.result=result;
    }

    public String getResult()
    {
        return this.result;
    }
}
