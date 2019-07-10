package calculator.computationTests.mathematicalOperationsTests;

import calculator.computation.Multiplication;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MultiplicationTest {

    private Multiplication mult;

    private final double DELTA=0.0001d;

    @Before
    public void setUp() {
        mult=new Multiplication();
    }

    @After
    public void tearDown(){
        mult=null;
    }

    @Test
    public void Multiplication_TwoPositiveAndNegativeIntegersAsParams_NoExceptionThrown() {
        double realResult=mult.compute(10,-12);
        assertEquals(-120,realResult,DELTA);
    }

    @Test
    public void getPriority_RequestPriorityOfOperator_GetPriority() {
        assertEquals(3,mult.getPriority());
    }

    @Test
    public void isLeftAssociative_RequestAssociativity_RightAssociative() {
        assertTrue(mult.isLeftAssociative());
    }


}