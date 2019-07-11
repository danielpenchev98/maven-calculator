package calculator.computationTests;

import calculator.computation.*;
import calculator.exceptions.InvalidOperatorException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MathOperatorFactoryTest {

    @Test
    public void createOperation_CreateOpeningBracketObject_ObjectCreated() throws InvalidOperatorException {
        MathOperator operator=MathOperatorFactory.createOperation("(");
        assertTrue(operator instanceof OpeningBracket);
    }

    @Test
    public void createOperation_CreateClosingBracketObject_ObjectCreated() throws InvalidOperatorException
    {
        MathOperator operator=MathOperatorFactory.createOperation(")");
        assertTrue(operator instanceof ClosingBracket);
    }

    @Test
    public void createOperation_CreateArithmeticOperatorObject_ObjectCreated() throws InvalidOperatorException
    {
        MathOperator operator=MathOperatorFactory.createOperation("+");
        assertTrue(operator instanceof MathArithmeticOperator);
    }

    @Test(expected = InvalidOperatorException.class)
    public void createOperation_CreateIllegalArithmeticOperatorObject() throws InvalidOperatorException
    {
        MathOperatorFactory.createOperation("fake");
    }
}