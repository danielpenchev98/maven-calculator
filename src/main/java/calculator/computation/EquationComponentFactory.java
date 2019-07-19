package calculator.computation;

import calculator.exceptions.InvalidParameterException;

public class EquationComponentFactory {

    public EquationComponent createOperation(final String operation)
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
            default: throw new InvalidParameterException("Unsupported operation");
        }
    }
}