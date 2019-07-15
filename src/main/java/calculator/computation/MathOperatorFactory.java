package calculator.computation;

import calculator.exceptions.InvalidOperatorException;

public class MathOperatorFactory {

    public static MathOperator createOperation(String operation) throws InvalidOperatorException
    {

        MathArithmeticOperatorFactory factory=new MathArithmeticOperatorFactory();
        switch(operation)
        {
            case "(": return new OpeningBracket();
            case ")": return new ClosingBracket();
            default: return factory.createArithmeticOperation(operation);
        }
    }
}
