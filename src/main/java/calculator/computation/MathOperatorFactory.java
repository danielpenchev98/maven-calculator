package calculator.computation;

import calculator.exceptions.InvalidOperatorException;

public class MathOperatorFactory {

    public static MathOperator createOperation(String operation) throws InvalidOperatorException
    {
        switch(operation)
        {
            case "(": return new OpeningBracket();
            case ")": return new ClosingBracket();
            default: return MathArithmeticOperatorFactory.createOperation(operation);
        }
    }
}
