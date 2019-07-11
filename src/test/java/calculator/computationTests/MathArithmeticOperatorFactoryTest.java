package calculator.computationTests;

import calculator.computation.*;
import calculator.exceptions.InvalidOperatorException;
import org.junit.Test;

import static org.junit.Assert.*;

public class MathArithmeticOperatorFactoryTest {


    @Test
    public void createOperation_CreateAdditionObject_NoExceptionExpected() throws InvalidOperatorException
    {
        MathArithmeticOperator operation= MathArithmeticOperatorFactory.createOperation("+");
        assertTrue(operation instanceof Addition);
    }

    @Test
    public void createOperation_CreateSubtractionObject_NoExceptionExpected() throws InvalidOperatorException
    {
        MathArithmeticOperator operation= MathArithmeticOperatorFactory.createOperation("-");
        assertTrue(operation instanceof Subtraction);
    }

    @Test
    public void createOperation_CreateMultiplicationObject_NoExceptionExpected() throws InvalidOperatorException
    {
        MathArithmeticOperator operation= MathArithmeticOperatorFactory.createOperation("*");
        assertTrue(operation instanceof Multiplication);
    }

    @Test
    public void createOperation_CreateDivisionObject_NoExceptionExpected() throws InvalidOperatorException
    {
        MathArithmeticOperator operation= MathArithmeticOperatorFactory.createOperation("/");
        assertTrue(operation instanceof Division);
    }

    @Test
    public void createOperation_CreatePowerObject_NoExceptionThrown() throws InvalidOperatorException
    {
        MathArithmeticOperator operation= MathArithmeticOperatorFactory.createOperation("^");
        assertTrue(operation instanceof  Power);
    }

    @Test (expected = InvalidOperatorException.class)
    public void createOperation_CreateIllegalOperationObject_InvalidParameterExceptionThrown() throws InvalidOperatorException
    {
        MathArithmeticOperatorFactory.createOperation("illegal");
    }
}