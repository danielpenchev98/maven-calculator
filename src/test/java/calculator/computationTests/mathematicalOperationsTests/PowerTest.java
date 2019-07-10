package calculator.computationTests.mathematicalOperationsTests;

import calculator.computation.Power;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PowerTest {

    private Power pow;

    private final double DELTA=0.0001d;

    @Before
    public void setUp()
    {
        pow=new Power();
    }

    @Test
    public void compute_SimplePowerOfaNumber_NormalResult() {
        assertEquals(169,pow.compute(13,2),DELTA);
    }

    @Test
    public void compute_AccuracyTest()
    {
        assertEquals(36.4621596072,pow.compute(Math.PI,Math.PI),DELTA);
    }

    @Test
    public void getPriority_RequestPriorityOfOperator_GetPriority() {
        assertEquals(4,pow.getPriority());
    }

    @Test
    public void isLeftAssociative_RequestAssociativity_RightAssociative() {
        assertFalse(pow.isLeftAssociative());
    }
}