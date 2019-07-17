package calculator.inputControlTests;

import calculator.inputControl.PrimalParser;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PrimalParserTest {

    private PrimalParser parser;

    @Before
    public void setUp()
    {
      parser= new PrimalParser();
    }

    @Test
    public void formatInput_InputWithManyJunkSpaces_FormattedInput() {
        String equation=" 1.0 +2    * (  -   1)/ 5";
        assertEquals( "1.0 + 2 * ( -1 ) / 5",parser.formatInput(equation));
    }

    @Test
    public void formatInput_InputWithoutAnySpaces_FormattedInput()
    {
        String equation="1+2*(-1.059)/5";
        assertEquals("1 + 2 * ( -1.059 ) / 5",parser.formatInput(equation));
    }

    @Test
    public void formatInput_InputWithSpaceBetweenNumberAndItsSign_FormattedInput()
    {
        String equation="- 1.0 + ( - 1 * ( - 109 ) )";
        assertEquals("-1.0 + ( -1 * ( -109 ) )",parser.formatInput(equation));
    }



}