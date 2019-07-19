package calculator.computation;

import calculator.exceptions.InvalidParameterException;

public class MathOperatorFactory {

    public MathOperator createOperation(String operation)
    {
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