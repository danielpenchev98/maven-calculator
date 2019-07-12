package calculator.computationTests;

import calculator.computation.ComputationalMachine;
import calculator.exceptions.DivisionByZeroException;
import calculator.exceptions.InvalidOperatorException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ComputationalMachineTest {

    private ComputationalMachine machine;

    //should i mock somehow factory method

    @Before
    public void setUp()
    {
        machine=new ComputationalMachine();
    }
    @After
    public void tearDown()
    {
        machine=null;
    }


    @Test(expected = DivisionByZeroException.class)
    public void computeAction_ComputeDivisionOfTwoNumbers_DivisionByZeroExceptionThrown() throws ArithmeticException, InvalidOperatorException {
        machine.computeAction("/",2.0,0);
    }

    @Test(expected= InvalidOperatorException.class)
    public void computeAction_ComputeNotSupportedOperation_InvalidParameterExceptionThrown() throws InvalidOperatorException
    {
        machine.computeAction("+/-",1,1);
    }
}