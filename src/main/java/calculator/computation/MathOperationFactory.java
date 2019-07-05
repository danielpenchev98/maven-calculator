package calculator.computation;

/**
 * Class which is responsible only for the creation of MathOperation objects.
 */
public class MathOperationFactory {
    /**
     * @param operation - string which represents the symbol of the wanted operation
     * @return a new object of that MathOperation class
     */
    public static MathOperation createOperation(String operation) throws InvalidParameterException
    {
        //to change it - should return null as default???
        switch(operation)
        {
            case "+": return new Addition();
            case "-":return new Subtraction();
            case "*": return new Multiplication();
            case "/":return new Division();
            default: throw new InvalidParameterException("Unsupported operation");
        }
    }
}
