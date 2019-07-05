package calculator.computationTests;

import calculator.computation.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class MathOperationFactoryTest {


    @Test
    public void CreateOperation_CreateAdditionObject_NoExceptionExpected() throws InvalidParameterException
    {
        if(!(MathOperationFactory.createOperation("+") instanceof Addition))
        {
            fail("Couldn't create an object of type Addition");
        }
    }

    @Test
    public void CreateOperation_CreateSubtractionObject_NoExceptionExpected() throws InvalidParameterException
    {
        if(!(MathOperationFactory.createOperation("-") instanceof Subtraction))
        {
            fail("Couldn't create an object of type Subtraction");
        }
    }

    @Test
    public void CreateOperation_CreateMultiplicationObject_NoExceptionExpected() throws InvalidParameterException
    {
        if(!(MathOperationFactory.createOperation("*") instanceof Multiplication))
        {
            fail("Couldn't create an object of type Multiplication");
        }
    }

    @Test
    public void CreateOperation_CreateDivisionObject_NoExceptionExpected() throws InvalidParameterException
    {
        if(!(MathOperationFactory.createOperation("/") instanceof Division))
        {
            fail("Couldn't create an object of type Division");
        }
    }

    @Test (expected = InvalidParameterException.class)
    public void CreateOperation_CreateIllegalOperationObject_InvalidParameterExceptionThrown() throws InvalidParameterException
    {
        MathOperationFactory.createOperation("illegal");
    }
}