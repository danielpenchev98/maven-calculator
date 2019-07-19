package calculator.computation;

import calculator.exceptions.DivisionByZeroException;
import calculator.exceptions.InvalidParameterException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ComputationalMachineIT {

    private ComputationalMachine machine;

    private final double DELTA=0.01;

    @Before
    public void setUp(){
        machine=new ComputationalMachine(new MathOperatorFactory());
    }

    @Test(expected = DivisionByZeroException.class)
    public void computeAction_ComputeDivisionOfTwoNumbers_DivisionByZeroExceptionThrown() throws ArithmeticException, InvalidParameterException {
        machine.computeAction("/",2.0,0);
    }

    @Test(expected= InvalidParameterException.class)
    public void computeAction_ComputeNotSupportedOperation_InvalidParameterExceptionThrown() throws InvalidParameterException
    {

        machine.computeAction("+/-",1,1);
    }

    @Test
    public void computeAction_ComputeSupportedOperation_ExpectedResult() throws InvalidParameterException
    {
        double result=machine.computeAction("^",2,3);
        assertEquals(8, result,DELTA);
    }
}