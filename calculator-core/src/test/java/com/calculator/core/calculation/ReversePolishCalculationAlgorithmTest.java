package com.calculator.core.calculation;

import com.calculator.core.exceptions.InvalidExpressionException;
import com.calculator.core.exceptions.InvalidParameterException;
import com.calculator.core.operators.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;


import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.List;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class ReversePolishCalculationAlgorithmTest {

    private ReversePolishCalculationAlgorithm algorithm;

    @Mock
    private ReversePolishNotationParser parser;

    @Mock
    private NumberComponent firstNumber;

    @Mock
    private NumberComponent secondNumber;

    @Mock
    private Addition addition;

    @Mock
    private Multiplication multiplication;


    @Before
    public void setUp() {
        algorithm = new ReversePolishCalculationAlgorithm(parser);
    }

    @Test(expected = EmptyStackException.class)
    public void calculationExpression_ExpressionWithMissingNumber_OutOfItemsExceptionThrown() throws Exception {
        List<ExpressionComponent> input = Arrays.asList(firstNumber, addition, secondNumber, addition);

        Mockito.when(parser.formatFromInfixToReversedPolishNotation(input))
                .thenReturn(Arrays.asList(firstNumber, secondNumber, addition, addition));

        Mockito.when(firstNumber.getValue()).thenReturn("2.0");
        Mockito.when(secondNumber.getValue()).thenReturn("3.0");
        Mockito.when(addition.compute(2.0, 3.0)).thenReturn(5.0);

        algorithm.calculateExpression(input);
    }

    @Test(expected = InvalidExpressionException.class)
    public void calculationExpression_ExpressionWithMissingOperator_MissingOperatorExceptionThrown() throws Exception {
        List<ExpressionComponent> input = Arrays.asList(firstNumber, addition, firstNumber, secondNumber);

        Mockito.when(parser.formatFromInfixToReversedPolishNotation(input))
                .thenReturn(Arrays.asList(firstNumber, firstNumber, secondNumber, addition));

        Mockito.when(firstNumber.getValue()).thenReturn("2.0");
        Mockito.when(secondNumber.getValue()).thenReturn("3.0");
        Mockito.when(addition.compute(2.0, 3.0)).thenReturn(5.0);

        algorithm.calculateExpression(input);
    }

    @Test(expected = InvalidParameterException.class)
    public void calculationExpression_ExpressionWithInvalidOperator_InvalidOperatorException() throws Exception {
        ClosingBracket closingBracket = new ClosingBracket();
        List<ExpressionComponent> input = Arrays.asList(firstNumber, closingBracket, secondNumber);

        Mockito.when(parser.formatFromInfixToReversedPolishNotation(input))
                .thenReturn(Arrays.asList(firstNumber, secondNumber, closingBracket));

        algorithm.calculateExpression(input);
    }

    @Test
    public void calculationExpression_LegalExpression() throws Exception {
        List<ExpressionComponent> input = Arrays.asList(secondNumber, addition, secondNumber, multiplication, secondNumber);

        Mockito.when(parser.formatFromInfixToReversedPolishNotation(input))
                .thenReturn(Arrays.asList(secondNumber, secondNumber, secondNumber, multiplication, addition));

        Mockito.when(secondNumber.getValue()).thenReturn("3.0");
        Mockito.when(multiplication.compute(3.0, 3.0)).thenReturn(9.0);
        Mockito.when(addition.compute(3.0, 9.0)).thenReturn(12.0);
        assertEquals(12.0, algorithm.calculateExpression(input), 0.001);
    }
}