package calculator.inputcontrol;

import calculator.computation.MathOperatorFactory;
import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.assertEquals;


public class ReversePolishNotationParserIT {

    private ReversePolishNotationParser parserRPN;
    private EquationValidator validator;
    private MathOperatorFactory operatorFactory;


    @Before
    public void setUp()
    {
        validator=new EquationValidator();
        operatorFactory=new MathOperatorFactory();
        parserRPN=new ReversePolishNotationParser(validator,operatorFactory);
    }



    @Test
    public void formatToReversedPolishNotation_EquationWithoutBrackets_RPNFormat() {
        String expected = "15 10 2 * +";
        String realResult=parserRPN.formatFromInfixToReversedPolishNotation("15 + 10 * 2");
        assertEquals(expected,realResult);
    }

    @Test
    public void formatToReversedPolishNotation_EquationWithEqualPriorityLeftAssociativeOperators_RPNFormat()
    {
        String expected = "1 2 / 3 *";
        String realResult = parserRPN.formatFromInfixToReversedPolishNotation("1 / 2 * 3");
        assertEquals(expected,realResult);
    }

    @Test
    public void formatToReversedPolishNotation_EquationWithBrackets_RPNFormat()
    {
        String realResult=parserRPN.formatFromInfixToReversedPolishNotation("( 15 + 10 ) * 2");
        assertEquals("15 10 + 2 *",realResult);
    }

    @Test
    public void formatToReversedPolishNotation_EquationWithMultipleNestedBrackets_RPNFormat()
    {
        String realResult=parserRPN.formatFromInfixToReversedPolishNotation("( ( ( 15 + 10 * 2 ) ) )");
        assertEquals("15 10 2 * +",realResult);
    }

    @Test
    public void formatToReversedPolishNotation_EquationProductOfTwoExpressionsEachInBracket_RPNFormat()
    {
        String realResult=parserRPN.formatFromInfixToReversedPolishNotation("( 1 + 2 ) * ( 3 + 4 )");
        assertEquals("1 2 + 3 4 + *",realResult);
    }

    @Test
    public void formatToReversedPolishNotation_EquationWithLeftAndRightAssociativeOperators_RPNFormat()
    {
        String expected="2 2 2 ^ /";
        String result=parserRPN.formatFromInfixToReversedPolishNotation("2 / 2 ^ 2");
        assertEquals(expected,result);
    }



}