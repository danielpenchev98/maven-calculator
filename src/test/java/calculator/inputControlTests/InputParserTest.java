package calculator.inputControlTests;

import calculator.computation.MathComponentType;
import calculator.inputControl.InputParser;
import calculator.inputControl.InvalidTypeOfEquationComponent;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

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



}