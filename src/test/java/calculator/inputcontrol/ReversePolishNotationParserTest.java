package calculator.inputcontrol;

import calculator.computation.*;
import calculator.exceptions.InvalidComponentException;
import org.junit.Before;
import org.junit.BeforeClass;
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
public class ReversePolishNotationParserTest {

    private ReversePolishNotationParser parserRPN;

    private NumberComponent firstNumber;

    private NumberComponent secondNumber;

    private NumberComponent thirdNumber;

    private OpeningBracket openingBracket;

    private ClosingBracket closingBracket;

    @Mock
    private Multiplication multiplication;

    @Mock
    private Addition addition;

    @Mock
    private Power power;

    @Before
    public void setUp()
    {
        parserRPN=new ReversePolishNotationParser();
        firstNumber=new NumberComponent("15");
        secondNumber=new NumberComponent("10");
        thirdNumber=new NumberComponent("2");
        openingBracket=new OpeningBracket();
        closingBracket=new ClosingBracket();
    }

    @Test
    public void formatToReversedPolishNotation_EquationWithoutBrackets_RPNFormat()  {

        Mockito.when(addition.getPriority()).thenReturn(2);
        Mockito.when(multiplication.getPriority()).thenReturn(3);

        List<EquationComponent> input=new LinkedList<>(Arrays.asList(firstNumber,addition,secondNumber,multiplication,thirdNumber));
        List<EquationComponent> expected=new LinkedList<>(Arrays.asList(firstNumber,secondNumber,thirdNumber,multiplication,addition));
        List<EquationComponent> realResult=parserRPN.formatFromInfixToReversedPolishNotation(input);

        assertEquals(expected,realResult);
    }

    @Test
    public void formatToReversedPolishNotation_EquationWithEqualPriorityLeftAssociativeOperators_RPNFormat()
    {
        Mockito.when(multiplication.getPriority()).thenReturn(3);
        Mockito.when(multiplication.isLeftAssociative()).thenReturn(true);

        List<EquationComponent> input=new LinkedList<>(Arrays.asList(firstNumber,multiplication,thirdNumber,multiplication,secondNumber));
        List<EquationComponent> expected=new LinkedList<>(Arrays.asList(firstNumber,thirdNumber,multiplication,secondNumber,multiplication));
        List<EquationComponent> realResult = parserRPN.formatFromInfixToReversedPolishNotation(input);

        assertEquals(expected,realResult);
    }

    @Test
    public void formatToReversedPolishNotation_EquationWithBrackets_RPNFormat()
    {

        List<EquationComponent> input=new LinkedList<>(Arrays.asList(openingBracket,firstNumber,addition,secondNumber,closingBracket,multiplication,thirdNumber));
        List<EquationComponent> expected=new LinkedList<>(Arrays.asList(firstNumber,secondNumber,addition,thirdNumber,multiplication));
        List<EquationComponent> realResult=parserRPN.formatFromInfixToReversedPolishNotation(input);

        assertEquals(expected,realResult);
    }

    @Test
    public void formatToReversedPolishNotation_EquationWithLeftAndRightAssociativeOperators_RPNFormat()
    {
        Mockito.when(multiplication.getPriority()).thenReturn(3);
        Mockito.when(power.getPriority()).thenReturn(4);

        List<EquationComponent> input=new LinkedList<>(Arrays.asList(thirdNumber,multiplication,thirdNumber,power,thirdNumber));
        List<EquationComponent> expected=new LinkedList<>(Arrays.asList(thirdNumber,thirdNumber,thirdNumber,power,multiplication));
        List<EquationComponent> result=parserRPN.formatFromInfixToReversedPolishNotation(input);

        assertEquals(expected,result);
    }
}