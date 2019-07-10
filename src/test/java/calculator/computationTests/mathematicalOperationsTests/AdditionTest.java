package calculator.computationTests.mathematicalOperationsTests;

import calculator.computation.Addition;
import org.junit.*;

import static org.junit.Assert.*;

public class AdditionTest {

    private Addition add;

    private final double DELTA=0.0001d;

    @Before
    public void setUp()
    {
        add=new Addition();
    }
    @After
    public void tearDown()
    {
        add=null;
    }

    @Test
    public void Addition_PositiveAndNegativeIntegersAsParams_NoExceptionThrown() {
        double realResult=add.compute(10,-12);
        assertEquals(-2,realResult,DELTA);
    }

    @Test
    public void getPriority_RequestPriorityOfOperator_GetPriority() {
        assertEquals(2,add.getPriority());
    }

    @Test
    public void isLeftAssociative_RequestAssociativity_RightAssociative() {
        assertTrue(add.isLeftAssociative());
    }

}