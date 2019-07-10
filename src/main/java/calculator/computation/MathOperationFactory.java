package calculator.computation;

import calculator.exceptions.InvalidOperatorException;

/**
 * Class which is responsible only for the creation of MathOperation objects.
 */
public class MathOperationFactory {
    /**
     * @param operation - string which represents the symbol of the wanted operation
     * @return a new object of that MathOperation class
     */
    public static MathOperation createOperation(String operation) throws InvalidOperatorException
    {
        switch(operation)
        {
            case "+": return new Addition();
            case "-":return new Subtraction();
            case "*": return new Multiplication();
            case "/":return new Division();
            case "^":return new Power();
            default: throw new InvalidOperatorException("Unsupported operation");
        }
    }
}
