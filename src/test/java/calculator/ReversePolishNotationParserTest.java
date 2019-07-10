package calculator;

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
    public void formatToReversedPolishNotation_SimpleEquationWithBrackets_RPNFormat() throws OutOfItemsException {
        String realResult=parserRPN.formatFromInfixToReversedPolishNotation("15 + 10 * 2");
        assertEquals("15 10 2 * +",realResult);
    }

    @Test
    public void formatToReversedPolishNotation_EquationWithBrackets_RPNFormat() throws OutOfItemsException
    {
        String realResult=parserRPN.formatFromInfixToReversedPolishNotation("( ( -1 ) * 20 ) / ( ( ( -10 ) ) )");
        assertEquals("-1 20 * -10 /",realResult);
    }

    @Test
    public void formatToReversedPolishNotation_EquationWithLeftAndRightAssociativeOperators_RPNFormat() throws OutOfItemsException
    {
        String result=parserRPN.formatFromInfixToReversedPolishNotation("3 + 4 * 2 / ( 1 − 5 ) ^ 2 ^ 3");
                assertEquals("3 4 2 * 1 5 − 2 3 ^ ^ / +",result);
    }

}