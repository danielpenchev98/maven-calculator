package calculator.computation;

import calculator.exceptions.InvalidOperatorException;


public class ComputationalMachine{

    private MathArithmeticOperator operation;

    public ComputationalMachine()
    {
        operation=null;
    }

    /**
     * @param action - the operation the ComputationalMachine object should compute
     * @param firstNumber - the first parameter of the operation
     * @param secondNumber - the second parameter of the operation
     * @return the result of the operation
     * @throws ArithmeticException - a mathematical exceptions, which signalizes about a logical error - like division on zero
     */

    //TODO should i list all the different exceptions or just the Super class
    public double computeAction(final String action,final double firstNumber,final double secondNumber) throws ArithmeticException, InvalidOperatorException
    {
        setMathematicalOperation(action);
        return operation.compute(firstNumber,secondNumber);
    }

    private void setMathematicalOperation(final String action) throws InvalidOperatorException
    {
        MathArithmeticOperatorFactory factory = new MathArithmeticOperatorFactory();
        operation= factory.createArithmeticOperation(action);
    }

}