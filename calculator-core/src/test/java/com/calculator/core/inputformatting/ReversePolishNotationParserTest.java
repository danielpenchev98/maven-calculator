package com.calculator.core.inputformatting;

import com.calculator.core.calculation.ReversePolishNotationParser;
import com.calculator.core.operators.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class ReversePolishNotationParserTest {

    private ReversePolishNotationParser parserRPN;

    @Mock
    private Multiplication multiplication;

    @Mock
    private Addition addition;


    private NumberComponent firstNumber;
    private NumberComponent secondNumber;
    private NumberComponent thirdNumber;
    private OpeningBracket openingBracket;
    private ClosingBracket closingBracket;


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
    public void formatToReversedPolishNotation_ExpressionWithoutBrackets_RPNFormat()  {

        Mockito.when(addition.getPriority()).thenReturn(2);
        Mockito.when(multiplication.getPriority()).thenReturn(3);

        List<ExpressionComponent> input=Arrays.asList(firstNumber,addition,secondNumber,multiplication,thirdNumber);
        List<ExpressionComponent> expected=Arrays.asList(firstNumber,secondNumber,thirdNumber,multiplication,addition);
        List<ExpressionComponent> realResult=parserRPN.formatFromInfixToReversedPolishNotation(input);

        assertThat(realResult,is(expected));
    }

    @Test
    public void formatToReversedPolishNotation_ExpressionWithEqualPriorityLeftAssociativeOperators_RPNFormat()
    {
        Mockito.when(multiplication.getPriority()).thenReturn(3);
        Mockito.when(multiplication.isLeftAssociative()).thenReturn(true);

        List<ExpressionComponent> input=Arrays.asList(firstNumber,multiplication,thirdNumber,multiplication,secondNumber);
        List<ExpressionComponent> expected=Arrays.asList(firstNumber,thirdNumber,multiplication,secondNumber,multiplication);
        List<ExpressionComponent> realResult = parserRPN.formatFromInfixToReversedPolishNotation(input);

        assertThat(realResult,is(expected));
    }

    @Test
    public void formatToReversedPolishNotation_ExpressionWithBrackets_RPNFormat()
    {

        List<ExpressionComponent> input=Arrays.asList(openingBracket,firstNumber,addition,secondNumber,closingBracket,multiplication,thirdNumber);
        List<ExpressionComponent> expected=Arrays.asList(firstNumber,secondNumber,addition,thirdNumber,multiplication);
        List<ExpressionComponent> realResult=parserRPN.formatFromInfixToReversedPolishNotation(input);

        assertThat(realResult,is(expected));
    }

}