package calculator.computation;

import calculator.exceptions.InvalidComponentException;
import calculator.exceptions.InvalidParameterException;


public class ComputationalMachine{

    private MathArithmeticOperator operation;

    private EquationComponentFactory factory;

    public ComputationalMachine(final EquationComponentFactory operatorFactory )
    {
        operation=null;
        factory=operatorFactory;
    }

    /**
     * @param action - the operation the ComputationalMachine object should compute
     * @param firstNumber - the first parameter of the operation
     * @param secondNumber - the second parameter of the operation
     * @return the result of the operation
     * @throws ArithmeticException - a mathematical exceptions, which signalizes about a logical error - like division on zero
     */

    //TODO should i list all the different exceptions or just the Super class
    public double computeAction(final String action,final double firstNumber,final double secondNumber) throws ArithmeticException,InvalidComponentException
    {
        setMathematicalOperation(action);
        return operation.compute(firstNumber,secondNumber);
    }

    private void setMathematicalOperation(final String action) throws InvalidComponentException
    {
        operation=(MathArithmeticOperator)factory.createComponent(action);
    }

}