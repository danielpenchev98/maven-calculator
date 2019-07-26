package com.calculator.core.computation;

import com.calculator.core.exceptions.InvalidComponentException;

public class EquationComponentFactory {

    private final static String validNumberRegex = "-?[0-9]+(.[0-9]+)?";

    public EquationComponent createComponent(final String operation) throws InvalidComponentException
    {
        if(operation.matches(validNumberRegex))
        {
            return new NumberComponent(operation);
        }

        switch(operation)
        {
            case OpeningBracket.SYMBOL: return new OpeningBracket();
            case ClosingBracket.SYMBOL: return new ClosingBracket();
            case Addition.SYMBOL: return new Addition();
            case Subtraction.SYMBOL:return new Subtraction();
            case Multiplication.SYMBOL: return new Multiplication();
            case Division.SYMBOL:return new Division();
            case Power.SYMBOL:return new Power();
            default: throw new InvalidComponentException("Unsupported component :"+operation);
        }
    }
}