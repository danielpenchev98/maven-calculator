package calculator.computation;

import calculator.exceptions.InvalidComponentException;

public class EquationComponentFactory {

    public EquationComponent createComponent(final String operation) throws InvalidComponentException
    {
        if(operation.matches("-?[0-9]+(.[0-9]+)?"))
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
            default: throw new InvalidComponentException("Unsupported operation");
        }
    }
}