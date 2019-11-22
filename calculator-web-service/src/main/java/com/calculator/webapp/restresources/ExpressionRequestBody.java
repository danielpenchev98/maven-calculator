package com.calculator.webapp.restresources;

public class ExpressionRequestBody {
    private String expression;

    public ExpressionRequestBody(){this("");}

    public ExpressionRequestBody(final String expression){
        this.expression=expression;
    }

    public void setExpression(final String expression){
        this.expression=expression;
    }
    public String getExpression(){return this.expression;}
}
