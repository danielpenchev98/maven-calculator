package calculator.computationTests.mathematicalOperationsTests;

import calculator.computation.Multiplication;
import calculator.exceptions.OverFlowException;
import calculator.exceptions.UnderFlowException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MultiplicationTest {

    private Multiplication mult;
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
        int realResult=mult.compute(10,-12);
        assertEquals(-120,realResult);
    }

    @Test(expected = OverFlowException.class)
    public void Multiplication_TwoPositiveIntegersAsParams_OverFlowExceptionThrown()
    {
        int temp=2000000000;
        mult.compute(temp, temp);
    }

    @Test(expected = UnderFlowException.class)
    public void Multiplication_TwoNegativeIntegersAsParams_UnderFlowExceptionThrown()
    {
        int temp=-2000000000;
        mult.compute(temp,-temp);
    }
}