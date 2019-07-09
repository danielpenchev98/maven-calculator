package calculator.computation;

import calculator.exceptions.InvalidParameterException;

/**
 * Class which represents the computational logic of the calculator. It uses Singleton design patten, because the calculator can have only one computing unit
 */
public class ComputationalMachine{


    private static volatile ComputationalMachine uniqueInstance;


    private MathOperation operation;

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

    private void setMathematicalOperation(final String action) throws InvalidParameterException
    {
        operation= MathOperationFactory.createOperation(action);
    }

    /**
     * @param action - the operation the ComputationalMachine object should compute
     * @param first_number - the first parameter of the operation
     * @param second_number - the second parameter of the operation
     * @return the result of the operation
     * @throws ArithmeticException - a mathematical exceptions, which signalizes about a logical error - like division on zero
     */
    public double computeAction(final String action,final double first_number,final double second_number) throws ArithmeticException, InvalidParameterException
    {
        setMathematicalOperation(action);
        return operation.compute(first_number,second_number);
    }

}