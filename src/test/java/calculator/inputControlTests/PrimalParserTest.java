package calculator.inputControlTests;

import calculator.computation.MathComponentType;
import calculator.inputControl.PrimalParser;
import calculator.inputControl.InvalidTypeOfEquationComponent;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

//Dont know if these are all the tests.
public class PrimalParserTest {

    private PrimalParser parser;

    @Before
    public void setUp()
    {
      parser= PrimalParser.getInstance();
    }
    @After
    public void tearDown()
    {
        parser=null;
    }

    @Test
    public void getInstance_UniqueInstanceExist_ReferenceToThatUniqueInstance() {
        assertSame(parser, PrimalParser.getInstance());
    }

    //region processInputTests
    @Test
    public void formatInput_InputWithManyJunkSpaces_FormattedInput() {
        String equation=" 1 +2    * (  -   1)/ 5";
        assertEquals( "1 + 2 * ( -1 ) / 5",parser.formatInput(equation));
    }

    @Test
    public void formatInput_InputWithoutAnySpaces_FormattedInput()
    {
        String equation="1+2*(-1)/5";
        assertEquals("1 + 2 * ( -1 ) / 5",parser.formatInput(equation));
    }

    @Test
    public void formatInput_InputWithSpaceBetweenNumberAndItsSign_FormattedInput()
    {
        String equation="- 1 + ( - 1 * ( - 109 ) )";
        assertEquals("-1 + ( -1 * ( -109 ) )",parser.formatInput(equation));
    }

}