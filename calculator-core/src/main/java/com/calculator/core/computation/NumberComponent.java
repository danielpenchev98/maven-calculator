package com.calculator.core.computation;

public class NumberComponent implements EquationComponent {

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
