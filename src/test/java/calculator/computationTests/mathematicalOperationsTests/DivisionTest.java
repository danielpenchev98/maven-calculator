package calculator.computationTests.mathematicalOperationsTests;

import calculator.computation.Division;
import org.junit.Test;


public class DivisionTest {

    @Test(expected=ArithmeticException.class)
    public void Division_TwoPositiveIntegersAsParams_ArithmeticExceptionThrown()
    {
        Division div=new Division();
        div.compute(100,0);
    }


}