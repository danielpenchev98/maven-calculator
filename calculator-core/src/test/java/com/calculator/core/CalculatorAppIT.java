package com.calculator.core;

import com.calculator.core.exceptions.DivisionByZeroException;
import com.calculator.core.exceptions.InvalidComponentException;
import com.calculator.core.exceptions.InvalidEquationException;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class CalculatorAppIT {

    private CalculatorApp app;

    @Before
    public void setUp()
    {
        app=new CalculatorApp();
    }

    @Test(expected = NullPointerException.class)
    public void calculateResult_NullReference_Illegal() throws Exception
    {
        app.calculateResult(null);
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

    @Test(expected = InvalidEquationException.class)
    public void calculateResult_BracketsOnly_Illegal() throws Exception
    {
        app.calculateResult("(()()(()))");
    }

    @Test(expected = InvalidComponentException.class)
    public void calculateResult_EquationWithInvalidNumber_Illegal() throws Exception
    {
        app.calculateResult("PI/2 * 6*(K)*PI");
    }

    @Test(expected = InvalidComponentException.class)
    public void calculateResult_EquationWithUnsupportedOperator_Illegal() throws Exception
    {
        app.calculateResult("(( 100 - 99) & (199))");
    }

    //TODO dont know if it should be InvalidEquationException or InvalidComponentException, because in the structure validator
    // i assume that everything except numbers/letters/normal brackets are possible operators and the factory method checks their validity
    @Test(expected = InvalidEquationException.class)
    public void calculateResult_EquationWithUnsupportedTypeOfBrackets_Illegal() throws Exception
    {
        app.calculateResult("[ (10 +7) ] / {20^100}");
    }

    @Test
    public void calculateResult_EquationForTestingPriority() throws Exception
    {
        double result=app.calculateResult("100/0^0");
        assertEquals(100.0,result);
    }

    @Test
    public void calculateResult_EquationForTestingAccuracy() throws Exception
    {
        double result=app.calculateResult("22/7");
        assertEquals(3.14285,result,0.0001);
    }

    @Test
    public void calculateResult_LongEquation() throws Exception
    {
        double result=app.calculateResult("((8^2/18)-(100*100)/1500)^((10))");
        assertEquals(84948.4030,result,0.001);
    }
}
