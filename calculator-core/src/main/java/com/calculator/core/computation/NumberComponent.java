package com.calculator.core.computation;

public class NumberComponent implements EquationComponent {

    private String value;

    public NumberComponent(final String value)
    {
        this.value=value;
    }

    public String getValue()
    {
        return value;
    }


}
