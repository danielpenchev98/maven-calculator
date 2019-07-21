package calculator.inputcontrol;

import calculator.exceptions.InvalidComponentException;
import calculator.exceptions.InvalidEquationException;
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
    public void doFormat_InputWithManyJunkSpaces_FormattedInput() throws Exception {
        List<String> expected=new LinkedList<>();
        expected.add("1.0");
        expected.add("+");
        expected.add("2");

        assertEquals( expected,parser.doFormat("1.0 +     2"));
    }

    @Test
    public void doFormat_InputWithoutAnySpaces_FormattedInput() throws Exception
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
    public void doFormat_InputWithSpaceBetweenNumberAndItsSign_FormattedInput() throws Exception
    {
        List<String> expected=new LinkedList<>();
        expected.add("-1.0");
        expected.add("+");
        expected.add("(");
        expected.add("-1");
        expected.add(")");

        assertEquals(expected,parser.doFormat("-1.0 + ( -1 )"));
    }

    @Test(expected = InvalidComponentException.class)
    public void doFormat_InputWithIllegalComponent_ExceptionThrown() throws Exception
    {
        parser.doFormat("-1.0+127.0.0.1");
    }

    @Test(expected = InvalidEquationException.class)
    public void doFormat_EquationWithIllegalStructure__ExceptionThrown() throws Exception
    {
        parser.doFormat("-1.0 2.0 +");
    }

}