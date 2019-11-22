package com.calculator.core.operators;

public class NumberComponent implements ExpressionComponent {

    private final String value;

    public NumberComponent(final String value)
    {
        this.value=value;
    }

    public String getValue()
    {
        return value;
    }


}
