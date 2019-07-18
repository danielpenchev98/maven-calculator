package calculator;

import calculator.exceptions.DivisionByZeroException;
import calculator.exceptions.InvalidEquationException;
import org.junit.Before;
import org.junit.Test;

public class CalculatorAppIT {

    private CalculatorApp app;

    @Before
    public void setUp()
    {
        app=new CalculatorApp();
    }

    @Test(expected = InvalidEquationException.class)
    public void calculateResult_EquationWithMissingBracket_Illegal() throws Exception
    {
        app.calculateResult("1+(( 23*4) - (-11)");
    }

    @Test(expected = InvalidEquationException.class)
    public void calculateResult_EquationWithASequenceOfNumbers_Illegal() throws Exception
    {
        app.calculateResult("1 / ( 5-(-2 3 + 5)/10");
    }

    @Test(expected = InvalidEquationException.class)
    public void calculateResult_EquationWithASequenceOfOperators_Illegal() throws Exception
    {
        app.calculateResult("( 1 + 2 *( 10/5/2/1 ) ^^3)");
    }

    @Test(expected = DivisionByZeroException.class)
    public void calculateResult_DivisionOnZero_Illegal() throws Exception
    {
        app.calculateResult("(1 *10 + 4 )*0/(-5 + 4 - (-1 ))");
    }

    @Test(expected = InvalidEquationException.class)
    public void calculateResult_EquationWithMissingOperator_Illegal() throws  Exception
    {
        app.calculateResult("8/ (((10 - 11/2 )))( -1 / 3 )");
    }

    @Test(expected = InvalidEquationException.class)
    public void calculateResult_EmptyEquation_Illegal() throws Exception
    {
        app.calculateResult("");
    }

    @Test
    public void calculateResult_BracketsOnly_Illegal() throws Exception
    {
        app.calculateResult("(()()(()))");
    }

}
