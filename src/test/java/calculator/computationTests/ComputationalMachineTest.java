package calculator.computationTests;

import calculator.computation.ComputationalMachine;
import calculator.exceptions.InvalidParameterException;
import calculator.exceptions.OverFlowException;
import calculator.exceptions.UnderFlowException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ComputationalMachineTest {

    private ComputationalMachine machine;
    @Before
    public void setUp()
    {
        machine=ComputationalMachine.getInstance();
    }
    @After
    public void tearDown()
    {
        machine=null;
    }

    @Test
    public void getInstance_GetTheUniqueInstanceOfComputationalMachineClass_True() {
        ComputationalMachine check=ComputationalMachine.getInstance();
        assertSame(machine,check);
    }

    @Test
    public void computeAction_ComputeMultiplicationOfTwoIntegers_True() throws ArithmeticException, InvalidParameterException {
        int first_number=2;
        int second_number=0;
        String operation="*";
        double result=machine.computeAction(operation,first_number,second_number);
        assertEquals(0,result,0);
    }

    @Test(expected = OverFlowException.class)
    public void computeAction_ComputeOperationOfTwoPositiveIntegers_OverFlowExceptionThrown() throws ArithmeticException, InvalidParameterException
    {
        int number=2000000000;
        machine.computeAction("*",number,number);
    }

    @Test(expected = UnderFlowException.class)
    public void computeAction_ComputeOperationOfOnePositiveAndOneNegativeInteger_UnderFlowExceptionThrown() throws ArithmeticException, InvalidParameterException
    {
        int number=2000000000;
        machine.computeAction("*",number,-number);
    }

    @Test(expected=InvalidParameterException.class)
    public void computeAction_ComputeNotSupportedOperation_InvalidParameterExceptionThrown() throws InvalidParameterException
    {
        machine.computeAction("+/-",1,1);
    }
}