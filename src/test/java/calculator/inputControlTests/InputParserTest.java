package calculator.inputControlTests;

import calculator.computation.MathComponentType;
import calculator.inputControl.InputParser;
import calculator.inputControl.InvalidTypeOfEquationComponent;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

//Dont know if these are all the tests.
public class InputParserTest {

    private InputParser parser;

    @Before
    public void setUp()
    {
      parser=InputParser.getInstance();
    }
    @After
    public void tearDown()
    {
        parser=null;
    }

    @Test
    public void getInstance_UniqueInstanceExist_ReferenceToThatUniqueInstance() {
        assertSame(parser,InputParser.getInstance());
    }

    //region processInputTests
    @Test
    public void processInput_ValidEquation_FormattedInput() throws InvalidTypeOfEquationComponent {
        String equation="15 7 1 1 * + -";
        String[] splitInput=equation.split(" ");
        assertArrayEquals(splitInput,parser.processInput(equation));
    }

    @Test(expected = InvalidTypeOfEquationComponent.class)
    public void processInput_InvalidEquation_InvalidTypeOfEquationComponentThrown() throws InvalidTypeOfEquationComponent
    {
        String equation="15 7 1 1 % + -";
        parser.processInput(equation);
    }


    @Test
    public void getTypeOfComponent_StringRepresentingNumberAsParam_IdentifyAsNumber()
    {
          assertEquals(MathComponentType.NUMBER,parser.getTypeOfComponent("1234"));
    }

    @Test
    public void getTypeOfComponent_StringRepresentingOperationAsParam_IdentifyAsOperation()
    {
        assertEquals(MathComponentType.OPERATOR,parser.getTypeOfComponent("*"));
    }

    @Test
    public void getTypeOfComponent_StringRepresentingIllegalComponentAsParam_IdentifyAsIllegal()
    {
        assertEquals(MathComponentType.INVALID,parser.getTypeOfComponent("invalid"));
    }

}