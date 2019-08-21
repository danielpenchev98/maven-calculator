package com.calculator.core.operators;

import com.calculator.core.exceptions.InvalidComponentException;

import java.util.HashMap;
import java.util.Map;

public class EquationComponentProvider {

    private final static String validNumberRegex = "-?[0-9]+(.[0-9]+)?";
    private final static Map<String,EquationComponent> validOperators = new HashMap<>() {{
                                                                            put(OpeningBracket.SYMBOL, new OpeningBracket());
                                                                            put(ClosingBracket.SYMBOL, new ClosingBracket());
                                                                            put(Addition.SYMBOL, new Addition());
                                                                            put(Subtraction.SYMBOL, new Subtraction());
                                                                            put(Multiplication.SYMBOL, new Multiplication());
                                                                            put(Division.SYMBOL, new Division());
                                                                            put(Power.SYMBOL, new Power());
                                                                        }};

    public EquationComponent getComponent(final String component) throws InvalidComponentException
    {
        if(isValidNumber(component))
        {
            return new NumberComponent(component);
        }
        else if(isValidOperator(component))
        {
            return validOperators.get(component);
        }
        else
        {
            throw new InvalidComponentException("Unsupported component :"+component);
        }
    }

    private boolean isValidNumber(final String component)
    {
        return component.matches(validNumberRegex);
    }

    private boolean isValidOperator(final String component)
    {
        return validOperators.containsKey(component);
    }
}