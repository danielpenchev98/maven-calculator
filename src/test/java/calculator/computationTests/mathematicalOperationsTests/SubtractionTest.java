package calculator.computationTests.mathematicalOperationsTests;

import calculator.computation.Subtraction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SubtractionTest {

    private Subtraction sub;

    private final double DELTA=0.0001d;

    @Before
    public void setUp()
    {
        sub=new Subtraction();
    }

    @After
    public void tearDown()
    {
        sub=null;
    }

    @Test
    public void Subtract_TwoPositiveIntegersAsParams_NoExceptionThrown() {
        double realResult=sub.compute(-10,-12);
        assertEquals(2,realResult,DELTA);
    }

    @Test
    public void getPriority_RequestPriorityOfOperator_GetPriority() {
        assertEquals(2,sub.getPriority());
    }

    @Test
    public void isLeftAssociative_RequestAssociativity_RightAssociative() {
        assertTrue(sub.isLeftAssociative());
    }

}