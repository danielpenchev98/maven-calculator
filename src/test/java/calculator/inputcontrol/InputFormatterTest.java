package calculator.inputcontrol;

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

    // TODO refactor tests
    @Test
    public void formatInput_InputWithManyJunkSpaces_FormattedInput() {
        assertEquals( "1.0 + 2",parser.doFormat("1.0 +     2"));
    }

    @Test
    public void formatInput_InputWithoutAnySpaces_FormattedInput()
    {
        assertEquals("( 1 + 2 )",parser.doFormat("(1+2)"));
    }

    @Test
    public void formatInput_InputWithSpaceBetweenNumberAndItsSign_FormattedInput()
    {
        assertEquals("-1.0 + ( -1 )",parser.doFormat("-1.0 + ( -1 )"));
    }

}