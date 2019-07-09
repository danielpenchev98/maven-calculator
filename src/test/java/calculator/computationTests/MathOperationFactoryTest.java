package calculator.computationTests;

import calculator.computation.*;
import calculator.exceptions.InvalidParameterException;
import org.junit.Test;

import static org.junit.Assert.*;

public class MathOperationFactoryTest {


    @Test
    public void CreateOperation_CreateAdditionObject_NoExceptionExpected() throws InvalidParameterException
    {
        MathOperation operation=MathOperationFactory.createOperation("+");
        assertTrue(operation instanceof Addition);
    }

    @Test
    public void CreateOperation_CreateSubtractionObject_NoExceptionExpected() throws InvalidParameterException
    {
        MathOperation operation=MathOperationFactory.createOperation("-");
        assertTrue(operation instanceof Subtraction);
    }

    @Test
    public void CreateOperation_CreateMultiplicationObject_NoExceptionExpected() throws InvalidParameterException
    {
        MathOperation operation=MathOperationFactory.createOperation("*");
        assertTrue(operation instanceof Multiplication);
    }

    @Test
    public void CreateOperation_CreateDivisionObject_NoExceptionExpected() throws InvalidParameterException
    {
        MathOperation operation=MathOperationFactory.createOperation("/");
        assertTrue(operation instanceof Division);
    }

    @Test (expected = InvalidParameterException.class)
    public void CreateOperation_CreateIllegalOperationObject_InvalidParameterExceptionThrown() throws InvalidParameterException
    {
        MathOperationFactory.createOperation("illegal");
    }
}