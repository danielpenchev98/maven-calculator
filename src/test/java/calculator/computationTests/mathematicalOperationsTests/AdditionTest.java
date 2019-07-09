package calculator.computationTests.mathematicalOperationsTests;

import calculator.computation.Addition;
import calculator.exceptions.OverFlowException;
import calculator.exceptions.UnderFlowException;
import org.junit.*;

import static org.junit.Assert.*;

public class AdditionTest {

    private Addition add;
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
        int realResult=add.compute(10,-12);
        assertEquals(-2,realResult);
    }

    @Test(expected = OverFlowException.class)
    public void Addition_TwoPositiveIntegersAsParams_OverFlowExceptionThrown()
    {
        int temp=2000000000;
        add.compute(temp, temp);
    }

    @Test(expected = UnderFlowException.class)
    public void Addition_TwoNegativeIntegersAsParams_UnderFlowExceptionThrown()
    {
        int temp=-2000000000;
        add.compute(temp,temp);
    }
}