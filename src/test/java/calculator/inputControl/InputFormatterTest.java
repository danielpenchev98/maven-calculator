package calculator.inputControl;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class InputFormatterTest {

    private InputFormatter parser;

    @Before
    public void setUp()
    {
      parser= new InputFormatter();
    }


    @Test
    public void formatInput_InputWithManyJunkSpaces_FormattedInput() {
        String equation=" 1.0 +2    * (  -   1)/ 5";
        assertArrayEquals( "1.0 + 2 * ( -1 ) / 5".split(" "),parser.doFormat(equation));
    }

    @Test
    public void formatInput_InputWithoutAnySpaces_FormattedInput()
    {
        String equation="1+2*(-1.059)/5";
        assertArrayEquals("1 + 2 * ( -1.059 ) / 5".split(" "),parser.doFormat(equation));
    }

    @Test
    public void formatInput_InputWithSpaceBetweenNumberAndItsSign_FormattedInput()
    {
        String equation="- 1.0 + ( - 1 * ( - 109 ) )";
        assertArrayEquals("-1.0 + ( -1 * ( -109 ) )".split(" "),parser.doFormat(equation));
    }

}