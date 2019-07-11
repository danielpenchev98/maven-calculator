package calculator.inputControlTests;

import calculator.exceptions.InvalidOperatorException;
import calculator.exceptions.OutOfItemsException;
import calculator.inputControl.ReversePolishNotationParser;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ReversePolishNotationParserTest {

    private ReversePolishNotationParser parserRPN;

    @Before
    public void setUp()
    {
        parserRPN=new ReversePolishNotationParser();
    }


    @Test
    public void formatToReversedPolishNotation_EquationWithBrackets_RPNFormat() throws OutOfItemsException, InvalidOperatorException
    {
        String realResult=parserRPN.formatFromInfixToReversedPolishNotation("( 15 + 10 ) * 2");
        assertEquals("15 10 + 2 *",realResult);
    }

    @Test
    public void formatToReversedPolishNotation_EquationWithoutBrackets_RPNFormat() throws OutOfItemsException, InvalidOperatorException {
        String realResult=parserRPN.formatFromInfixToReversedPolishNotation("15 + 10 * 2");
        assertEquals("15 10 2 * +",realResult);
    }

    @Test
    public void formatToReversedPolishNotation_EquationWithMultipleNestedBrackets_RPNFormat() throws OutOfItemsException, InvalidOperatorException
    {
        String realResult=parserRPN.formatFromInfixToReversedPolishNotation("( ( ( 15 + 10 * 2 ) ) )");
        assertEquals("15 10 2 * +",realResult);
    }

    @Test
    public void formatToReversedPolishNotation_EquationProductOfTwoExpressionsEachInBracket_RPNFormat()  throws OutOfItemsException, InvalidOperatorException
    {
        String realResult=parserRPN.formatFromInfixToReversedPolishNotation("( 1 + 2 ) * ( 3 + 4 )");
        assertEquals("1 2 + 3 4 + *",realResult);
    }

    @Test
    public void formatToReversedPolishNotation_EquationWithLeftAndRightAssociativeOperators_RPNFormat() throws OutOfItemsException, InvalidOperatorException
    {
        String result=parserRPN.formatFromInfixToReversedPolishNotation("3 + 4 * 2 / 2 ^ ( 1 - 5 ) ^ 3");
                assertEquals("3 4 2 * 2 1 5 - 3 ^ ^ / +",result);
    }

}