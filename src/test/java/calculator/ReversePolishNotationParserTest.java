package calculator;

import calculator.exceptions.ReversePolishNotationParser;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ReversePolishNotationParserTest {

    private ReversePolishNotationParser parserRPN;

    @Before
    public void setUp()
    {
        parserRPN=new ReversePolishNotationParser();
    }

    @Test
    public void formatToReversedPolishNotation_SimpleEquationWithBrackets_RPNFormat() {
        String realResult=parserRPN.formatFromInfixToReversedPolishNotation("15 + 10 * 2");
        assertEquals("15 10 2 * +",realResult);
    }

    @Test
    public void formatToReversedPolishNotation_EquationWithBrackets_RPNFormat()
    {
        String realResult=parserRPN.formatFromInfixToReversedPolishNotation("( ( -1 ) * 20 ) / ( ( ( -10 ) ) )");
        assertEquals("-1 20 * -10 /",realResult);
    }
}