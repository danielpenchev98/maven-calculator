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
        assertArrayEquals(splitInput,parser.formatAndValidateInput(equation));
    }

    @Test(expected = InvalidTypeOfEquationComponent.class)
    public void processInput_InvalidEquation_InvalidTypeOfEquationComponentThrown() throws InvalidTypeOfEquationComponent
    {
        String equation="15 7 1 1 % + -";
        parser.formatAndValidateInput(equation);
    }


    @Test
    public void getTypeOfComponent_StringRepresentingNumberAsParam_IdentifyAsNumber() throws InvalidTypeOfEquationComponent
    {
          assertEquals(MathComponentType.NUMBER,parser.getTypeOfComponent("1234"));
    }

    @Test
    public void getTypeOfComponent_StringRepresentingOperationAsParam_IdentifyAsOperation() throws InvalidTypeOfEquationComponent
    {
        assertEquals(MathComponentType.OPERATOR,parser.getTypeOfComponent("*"));
    }

    @Test(expected = InvalidTypeOfEquationComponent.class)
    public void getTypeOfComponent_StringRepresentingIllegalComponentAsParam_InvalidTypeOfEquationComponentThrown() throws InvalidTypeOfEquationComponent
    {
        parser.getTypeOfComponent("illegal");
    }

    @Test
    public void addSpaceAfterEveryComponent_EquationWIthMissingSpaces_FormattedEquation()
    {
        assertEquals("1  + 2 * ( 3 -   1 ) *  5 ",parser.addSpaceAfterEveryComponent("1 +2*(3-  1)* 5"));
    }

    @Test
    public void specialRemoveOfSpaces()
    {
        assertEquals("1 + 2 * ( -1 )",parser.removeSpaceBetweenTheNumberAndItsSign("1 + 2 * ( - 1 )"));
    }

    @Test
    public void specialRemoveSpaces2()
    {
        assertEquals("-1 + 2",parser.removeSpaceBetweenTheNumberAndItsSign("- 1 + 2"));
    }

    @Test
    public void removeJunkSpaces()
    {
        assertEquals("1 + 2 * 4",parser.removeJunkSpaces(" 1 +   2 * 4 "));
    }

}