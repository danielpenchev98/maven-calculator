package calculator.computation;

import calculator.exceptions.DivisionByZeroException;
import calculator.exceptions.InvalidParameterException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class ComputationalMachineTest {

    @InjectMocks
    private ComputationalMachine machine;

    @Mock
    private MathArithmeticOperatorFactory factory;

    @Mock
    private MathArithmeticOperator operation;


    @Test(expected = DivisionByZeroException.class)
    public void computeAction_ComputeDivisionOfTwoNumbers_DivisionByZeroExceptionThrown() throws ArithmeticException, InvalidParameterException {

        Mockito.when(factory.createArithmeticOperation("/")).thenReturn(operation);
        Mockito.when(operation.compute(2.0,0)).thenThrow(new DivisionByZeroException("Division on zero"));

        machine.computeAction("/",2.0,0);
    }

    @Test(expected= InvalidParameterException.class)
    public void computeAction_ComputeNotSupportedOperation_InvalidParameterExceptionThrown() throws InvalidParameterException
    {

        Mockito.when(factory.createArithmeticOperation("+/-")).thenThrow(new InvalidParameterException("invalid operation"));
        machine.computeAction("+/-",1,1);
    }

    @Test
    public void computeAction_ComputeSupportedOperation_ExpectedResult() throws InvalidParameterException
    {
        Mockito.when(factory.createArithmeticOperation("^")).thenReturn(operation);
        Mockito.when(operation.compute(2,3)).thenReturn(8.0);
        double result=machine.computeAction("^",2,3);
        assertEquals(8, result,0.001);
    }


}