package calculator.computation;

/**
 * Class which represents the computational logic of the calculator. It uses Singleton design patten
 */
public class ComputationalMachine{

    /**
     * unique instance of ComputationalMachine class
     */
    private static volatile ComputationalMachine uniqueInstance;

    /**
     * Reference to type derivative of MathOperation - If something in the implementation of the operation changes, then this class wont notice it
     */
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


    /**
     * This function changes the state of the ComputationalMachine object
     * @param action - represents the operation we want to compute
     */
    private void setMathematicalOperation(final String action) throws InvalidParameterException
    {
        operation= MathOperationFactory.createOperation(action);
    }

    /**
     * @param action - the operation the ComputationalMachine object should compute
     * @param first_number - the first parameter of the operation
     * @param second_number - the second parameter of the operation
     * @return the result of the operation
     * @throws ArithmeticException - a mathematical exception, which signalizes about a logical error - like division on zero
     */
    public int computeAction(final String action,final int first_number,final int second_number) throws ArithmeticException, InvalidParameterException
    {
        setMathematicalOperation(action);
        return operation.compute(first_number,second_number);
    }

}