package calculator.inputcontrol;

import calculator.computation.*;
import calculator.exceptions.InvalidComponentException;
import calculator.exceptions.InvalidEquationException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class InputFormatterTest {

    private InputFormatter parser;
    @Mock
    private EquationStructureValidator validator;

    @Mock
    private EquationComponentFactory factory;

    private final NumberComponent firstNumber=new NumberComponent("-1.0");
    private  final NumberComponent secondNumber=new NumberComponent("2.0");
    private final Addition addition=new Addition();
    private final OpeningBracket openingBracket=new OpeningBracket();
    private final ClosingBracket closingBracket=new ClosingBracket();

    @Before
    public void setUp()
    {
        parser= new InputFormatter(validator,factory);
    }

    @Test
    public void doFormat_InputWithManyJunkSpaces_FormattedInput() throws Exception {

        Mockito.when(factory.createComponent("-1.0")).thenReturn(firstNumber);
        Mockito.when(factory.createComponent("2.0")).thenReturn(secondNumber);
        Mockito.when(factory.createComponent("+")).thenReturn(addition);

        List<EquationComponent> expected=new LinkedList<>(Arrays.asList(firstNumber,addition,secondNumber));
        assertEquals( expected,parser.doFormat("-1.0 +     2.0"));
    }

    @Test
    public void doFormat_InputWithoutAnySpaces_FormattedInput() throws Exception
    {
        Mockito.when(factory.createComponent("(")).thenReturn(openingBracket);
        Mockito.when(factory.createComponent("-1.0")).thenReturn(firstNumber);
        Mockito.when(factory.createComponent("+")).thenReturn(addition);
        Mockito.when(factory.createComponent("2.0")).thenReturn(secondNumber);
        Mockito.when(factory.createComponent(")")).thenReturn(closingBracket);

        List<EquationComponent> expected=new LinkedList<>(Arrays.asList(openingBracket,firstNumber,addition,secondNumber,closingBracket));
        assertEquals(expected,parser.doFormat("(-1.0+2.0)"));
    }

    @Test
    public void doFormat_InputWithSpaceBetweenNumberAndItsSign_FormattedInput() throws Exception
    {
        Mockito.when(factory.createComponent("2.0")).thenReturn(secondNumber);
        Mockito.when(factory.createComponent("+")).thenReturn(addition);
        Mockito.when(factory.createComponent("(")).thenReturn(openingBracket);
        Mockito.when(factory.createComponent("-1.0")).thenReturn(firstNumber);
        Mockito.when(factory.createComponent(")")).thenReturn(closingBracket);

        List<EquationComponent> expected=new LinkedList<>(Arrays.asList(secondNumber,addition,openingBracket,firstNumber,closingBracket));
        assertEquals(expected,parser.doFormat("2.0 + ( - 1.0 )"));
    }

    @Test(expected = InvalidComponentException.class)
    public void doFormat_InputWithIllegalComponent_ExceptionThrown() throws Exception
    {
        Mockito.when(factory.createComponent("-1.0")).thenReturn(firstNumber);
        Mockito.when(factory.createComponent("+")).thenReturn(addition);
        Mockito.when(factory.createComponent("127.0.0.1")).thenThrow(new InvalidComponentException("Invalid component"));

        parser.doFormat("-1.0+127.0.0.1");
    }

    @Test(expected = InvalidEquationException.class)
    public void doFormat_EquationWithIllegalStructure__ExceptionThrown() throws Exception
    {
        Mockito.doThrow(new InvalidEquationException("Invalid equation")).when(validator).validateEquationStructure(Arrays.asList("-1.0","2.0","+"));
        parser.doFormat("-1.0 2.0 +");
    }
}