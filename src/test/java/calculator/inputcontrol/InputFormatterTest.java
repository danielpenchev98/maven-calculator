package calculator.inputcontrol;

import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

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
        List<String> expected=new LinkedList<>();
        expected.add("1.0");
        expected.add("+");
        expected.add("2");

        assertEquals( expected,parser.doFormat("1.0 +     2"));
    }

    @Test
    public void formatInput_InputWithoutAnySpaces_FormattedInput()
    {
        List<String> expected=new LinkedList<>();
        expected.add("(");
        expected.add("1");
        expected.add("+");
        expected.add("2");
        expected.add(")");

        assertEquals(expected,parser.doFormat("(1+2)"));
    }

    @Test
    public void formatInput_InputWithSpaceBetweenNumberAndItsSign_FormattedInput()
    {
        List<String> expected=new LinkedList<>();
        expected.add("-1.0");
        expected.add("+");
        expected.add("(");
        expected.add("-1");
        expected.add(")");

        assertEquals(expected,parser.doFormat("-1.0 + ( -1 )"));
    }

}