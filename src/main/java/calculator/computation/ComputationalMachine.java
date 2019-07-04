package calculator.computation;

public class ComputationalMachine{

    private static volatile ComputationalMachine uniqueInstance;
    private MathOperation operation;

    private ComputationalMachine()
    {
        operation=null;
    }

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

    private void setMathematicalOperation(final String action)
    {
        operation= MathOperationFactory.createOperation(action);
    }

    public int computeAction(final String action,final int first_number,final int second_number) throws InvalidMathematicalOperationException
    {
        setMathematicalOperation(action);
        return operation.compute(first_number,second_number);
    }

}