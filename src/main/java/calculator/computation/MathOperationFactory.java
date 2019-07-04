package calculator.computation;

/**
 * Class which is responsible only for the creation of MathOperation objects.
 */
class MathOperationFactory {
    /**
     * @param operation - string which represents the symbol of the wanted operation
     * @return a new object of that MathOperation class
     */
    static MathOperation createOperation(String operation)
    {
        //to change it - should return null as default???
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
