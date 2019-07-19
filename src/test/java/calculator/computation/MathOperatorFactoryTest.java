package calculator.computation;

import calculator.exceptions.InvalidParameterException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MathOperatorFactoryTest {

    private MathOperatorFactory factory;

    @Before
    public void setUp()
    {
        factory=new MathOperatorFactory();
    }


    @Test
    public void createArithmeticOperation_CreateAdditionObject_NoExceptionExpected() throws InvalidParameterException
    {
        MathOperator operation = factory.createOperation("+");
        assertTrue(operation instanceof Addition);
    }

    @Test
    public void createArithmeticOperation_CreateSubtractionObject_NoExceptionExpected() throws InvalidParameterException
    {
        MathOperator operation = factory.createOperation("-");
        assertTrue(operation instanceof Subtraction);
    }

    @Test
    public void createArithmeticOperation_CreateMultiplicationObject_NoExceptionExpected() throws InvalidParameterException
    {
        MathOperator operation = factory.createOperation("*");
        assertTrue(operation instanceof Multiplication);
    }

    @Test
    public void createArithmeticOperation_CreateDivisionObject_NoExceptionExpected() throws InvalidParameterException
    {
        MathOperator operation = factory.createOperation("/");
        assertTrue(operation instanceof Division);
    }

    @Test
    public void createArithmeticOperation_CreatePowerObject_NoExceptionThrown() throws InvalidParameterException
    {
        MathOperator operation = factory.createOperation("^");
        assertTrue(operation instanceof  Power);
    }

    @Test (expected = InvalidParameterException.class)
    public void createArithmeticOperation_CreateIllegalOperationObject_InvalidParameterExceptionThrown() throws InvalidParameterException
    {
        factory.createOperation("illegal");
    }
}