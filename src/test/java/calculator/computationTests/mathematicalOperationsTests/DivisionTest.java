package calculator.computationTests.mathematicalOperationsTests;

import calculator.computation.Division;
import calculator.exceptions.DivisionByZeroException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class DivisionTest {

    private Division div;

    private final double DELTA=0.0001d;

    @Before
    public void setUp()
    {
        div=new Division();
    }

    @Test
    public void compute_SimpleDivision()
    {
        assertEquals(3.14285714286,div.compute(22.0,7),DELTA);
    }

    @Test(expected= DivisionByZeroException.class)
    public void compute_TwoPositiveIntegersAsParams_ArithmeticExceptionThrown()
    {
        div.compute(100,0);
    }

    @Test
    public void getPriority_RequestPriorityOfOperator_GetPriority() {
        assertEquals(3,div.getPriority());
    }

    @Test
    public void isLeftAssociative_RequestAssociativity_RightAssociative() {
        assertTrue(div.isLeftAssociative());
    }

}