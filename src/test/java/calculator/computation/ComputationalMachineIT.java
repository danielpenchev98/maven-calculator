package calculator.computation;

import calculator.exceptions.DivisionByZeroException;
import calculator.exceptions.InvalidComponentException;
import calculator.exceptions.InvalidParameterException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ComputationalMachineIT {

    private ComputationalMachine machine;

    private final double DELTA=0.01;

    @Before
    public void setUp(){
        machine=new ComputationalMachine(new EquationComponentFactory());
    }

    @Test(expected = DivisionByZeroException.class)
    public void computeAction_ComputeDivisionOfTwoNumbers_DivisionByZeroExceptionThrown() throws ArithmeticException, InvalidComponentException{
        machine.computeAction("/",2.0,0);
    }

    @Test(expected= InvalidComponentException.class)
    public void computeAction_ComputeNotSupportedOperation_InvalidParameterExceptionThrown() throws InvalidComponentException
    {

        machine.computeAction("+/-",1,1);
    }

    @Test
    public void computeAction_ComputeSupportedOperation_ExpectedResult() throws InvalidComponentException
    {
        double result=machine.computeAction("^",2,3);
        assertEquals(8, result,DELTA);
    }
}