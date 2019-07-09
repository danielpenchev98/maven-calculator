package calculator.computationTests.mathematicalOperationsTests;

import calculator.exceptions.OverFlowException;
import calculator.computation.Subtraction;
import calculator.exceptions.UnderFlowException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SubtractionTest {

    private Subtraction sub;
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
        int realResult=sub.compute(-10,-12);
        assertEquals(2,realResult);
    }

    @Test(expected = OverFlowException.class)
    public void Subtract_sumTwoPositiveIntegersAsParams_OverFlowExceptionThrown()
    {
        int temp=2000000000;
        sub.compute(temp, -temp);
    }

    @Test(expected = UnderFlowException.class)
    public void Subtract_TwoNegativeIntegersAsParams_UnderFlowExceptionThrown()
    {
        int temp=-2000000000;
        sub.compute(temp,-temp);
    }

}