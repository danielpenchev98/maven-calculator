package calculator.computation;

import calculator.exceptions.InvalidOperatorException;

/**
 * Class which represents the computational logic of the calculator. It uses Singleton design patten, because the calculator can have only one computing unit
 */
public class ComputationalMachine{


    private static volatile ComputationalMachine uniqueInstance;


    private MathArithmeticOperator operation;

    /**
     *  the constructor is private in order to maintain the Logic of Singleton - Single object of that class only
     */
    private ComputationalMachine()
    {
        operation=null;
    }

    /**
     * @return a reference to the only object of type ComputationalMachine
     */
    public static ComputationalMachine getInstance()
    {
        if(uniqueInstance==null)
        {
            synchronized (ComputationalMachine.class)
            {
                if(uniqueInstance==null)
                {
                    uniqueInstance=new ComputationalMachine();
                }

            }
        }
        return uniqueInstance;
    }

    private void setMathematicalOperation(final String action) throws InvalidOperatorException
    {
        operation= MathArithmeticOperatorFactory.createArithmeticOperation(action);
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

}