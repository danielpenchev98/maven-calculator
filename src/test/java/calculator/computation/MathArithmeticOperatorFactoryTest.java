package calculator.computation;

import calculator.exceptions.InvalidOperatorException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MathArithmeticOperatorFactoryTest {

    private MathArithmeticOperatorFactory factory;

    @Before
    public void setUp()
    {
        factory=new MathArithmeticOperatorFactory();
    }


    @Test
    public void createArithmeticOperation_CreateAdditionObject_NoExceptionExpected() throws InvalidOperatorException
    {
        MathArithmeticOperator operation = factory.createArithmeticOperation("+");
        assertTrue(operation instanceof Addition);
    }

    @Test
    public void createArithmeticOperation_CreateSubtractionObject_NoExceptionExpected() throws InvalidOperatorException
    {
        MathArithmeticOperator operation = factory.createArithmeticOperation("-");
        assertTrue(operation instanceof Subtraction);
    }

    @Test
    public void createArithmeticOperation_CreateMultiplicationObject_NoExceptionExpected() throws InvalidOperatorException
    {
        MathArithmeticOperator operation = factory.createArithmeticOperation("*");
        assertTrue(operation instanceof Multiplication);
    }

    @Test
    public void createArithmeticOperation_CreateDivisionObject_NoExceptionExpected() throws InvalidOperatorException
    {
        MathArithmeticOperator operation = factory.createArithmeticOperation("/");
        assertTrue(operation instanceof Division);
    }

    @Test
    public void createArithmeticOperation_CreatePowerObject_NoExceptionThrown() throws InvalidOperatorException
    {
        MathArithmeticOperator operation = factory.createArithmeticOperation("^");
        assertTrue(operation instanceof  Power);
    }

    @Test (expected = InvalidOperatorException.class)
    public void createArithmeticOperation_CreateIllegalOperationObject_InvalidParameterExceptionThrown() throws InvalidOperatorException
    {
        factory.createArithmeticOperation("illegal");
    }
}