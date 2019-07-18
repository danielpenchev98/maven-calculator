package calculator.computation;

public class MathOperatorFactory {

    public MathOperator createOperation(String operation)
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