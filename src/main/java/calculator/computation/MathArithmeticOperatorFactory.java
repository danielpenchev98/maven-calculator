package calculator.computation;

import calculator.exceptions.InvalidParameterException;

/**
 * Class which is responsible only for the creation of MathArithmeticOperator objects.
 */
public class MathArithmeticOperatorFactory {

    /**
     * @param operation - string which represents the symbol of the wanted operation
     * @return a new object of that MathArithmeticOperator class
     */
    public  MathArithmeticOperator createArithmeticOperation(String operation)
    {
        switch(operation)
        {
            case "+": return new Addition();
            case "-":return new Subtraction();
            case "*": return new Multiplication();
            case "/":return new Division();
            case "^":return new Power();
            default: throw new InvalidParameterException("Unsupported operation");
        }
    }
}
