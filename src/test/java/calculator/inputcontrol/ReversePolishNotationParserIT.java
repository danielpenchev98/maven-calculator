package calculator.inputcontrol;

import calculator.computation.*;
import calculator.exceptions.InvalidComponentException;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class ReversePolishNotationParserIT {

    private ReversePolishNotationParser parserRPN;

    private final NumberComponent firstNumber=new NumberComponent("15");

    private final NumberComponent secondNumber=new NumberComponent("10");

    private final NumberComponent thirdNumber=new NumberComponent("2");

    private final Addition addition=new Addition();

    private final Multiplication multiplication=new Multiplication();

    private final OpeningBracket openingBracket=new OpeningBracket();

    private final ClosingBracket closingBracket=new ClosingBracket();

    @Before
    public void setUp()
    {
        parserRPN=new ReversePolishNotationParser();
    }



    @Test
    public void formatToReversedPolishNotation_EquationWithoutBrackets_RPNFormat()  {

        List<EquationComponent> input=new LinkedList<>(Arrays.asList(firstNumber,addition,secondNumber,multiplication,thirdNumber));
        List<EquationComponent> expected=new LinkedList<>(Arrays.asList(firstNumber,secondNumber,thirdNumber,multiplication,addition));
        List<EquationComponent> realResult=parserRPN.formatFromInfixToReversedPolishNotation(input);

        assertEquals(expected,realResult);
    }

    @Test
    public void formatToReversedPolishNotation_EquationWithEqualPriorityLeftAssociativeOperators_RPNFormat()
    {
        List<EquationComponent> input=new LinkedList<>(Arrays.asList(firstNumber,multiplication,secondNumber,multiplication,thirdNumber));
        List<EquationComponent> expected=new LinkedList<>(Arrays.asList(firstNumber,secondNumber,multiplication,thirdNumber,multiplication));
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
    public void formatToReversedPolishNotation_EquationProductOfTwoExpressionsEachInBracket_RPNFormat()
    {
        List<EquationComponent> input=new LinkedList<>(Arrays.asList(openingBracket,
                                                                       secondNumber,
                                                                           addition,
                                                                        firstNumber,
                                                                     closingBracket,
                                                                     multiplication,
                                                                     openingBracket,
                                                                        thirdNumber,
                                                                           addition,
                                                                       secondNumber,
                                                                     closingBracket));

        List<EquationComponent> expected=new LinkedList<>(Arrays.asList(secondNumber,firstNumber,addition,thirdNumber,secondNumber,addition,multiplication));
        List<EquationComponent> realResult=parserRPN.formatFromInfixToReversedPolishNotation(input);

        assertEquals(expected,realResult);
    }

    @Test
    public void formatToReversedPolishNotation_EquationWithLeftAndRightAssociativeOperators_RPNFormat(){
        List<EquationComponent> input=new LinkedList<>(Arrays.asList(thirdNumber,multiplication,thirdNumber,new Power(),thirdNumber));
        List<EquationComponent> expected=new LinkedList<>(Arrays.asList(thirdNumber,thirdNumber,thirdNumber,new Power(),multiplication));
        List<EquationComponent> result=parserRPN.formatFromInfixToReversedPolishNotation(input);

        assertEquals(expected,result);
    }



}