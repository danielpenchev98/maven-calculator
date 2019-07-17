package calculator.computation;

import calculator.exceptions.DivisionByZeroException;
import calculator.exceptions.InvalidOperatorException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ComputationalMachineIT {

    private ComputationalMachine machine;

    private final double DELTA=0.01;

    @Before
    public void setUp(){
        machine=new ComputationalMachine(new MathArithmeticOperatorFactory());
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

    @Test
    public void computeAction_ComputeSupportedOperation_ExpectedResult() throws InvalidOperatorException
    {
        double result=machine.computeAction("^",2,3);
        assertEquals(8, result,DELTA);
    }
}