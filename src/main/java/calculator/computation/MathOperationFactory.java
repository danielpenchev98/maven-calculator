package calculator.computation;

public class MathOperationFactory {
    static MathOperation createOperation(String operation)
    {
        switch(operation)
        {
            case "+": return new Addition();
            case "-":return new Subtraction();
            case "*": return new Multiplication();
            case "/":return new Division();
            default:return null;
        }
    }
}
