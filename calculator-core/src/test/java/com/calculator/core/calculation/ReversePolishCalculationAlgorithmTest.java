package com.calculator.core.calculation;

import com.calculator.core.exceptions.InvalidEquationException;
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
import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

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
    private Power power;

    @Before
    public void setUp() {
        algorithm=new ReversePolishCalculationAlgorithm(parser);
    }

    @Test(expected = EmptyStackException.class)
    public void calculationEquation_EquationWithMissingNumber_OutOfItemsExceptionThrown() throws Exception
    {
        List<EquationComponent> input=Arrays.asList(firstNumber,addition,secondNumber,addition);

        Mockito.when(parser.formatFromInfixToReversedPolishNotation(input))
                .thenReturn(Arrays.asList(firstNumber,secondNumber,addition,addition));

        Mockito.when(firstNumber.getValue()).thenReturn("2.0");
        Mockito.when(secondNumber.getValue()).thenReturn("3.0");
        Mockito.when(addition.compute(2.0,3.0)).thenReturn(5.0);

        algorithm.calculateEquation(input);
    }

    @Test(expected = InvalidEquationException.class)
    public void calculationEquation_EquationWithMissingOperator_MissingOperatorExceptionThrown() throws Exception
    {
        List<EquationComponent> input=Arrays.asList(firstNumber,addition,firstNumber,secondNumber);

        Mockito.when(parser.formatFromInfixToReversedPolishNotation(input))
                .thenReturn(Arrays.asList(firstNumber,firstNumber,secondNumber,addition));

        Mockito.when(firstNumber.getValue()).thenReturn("2.0");
        Mockito.when(secondNumber.getValue()).thenReturn("3.0");
        Mockito.when(addition.compute(2.0,3.0)).thenReturn(5.0);

        algorithm.calculateEquation(input);
    }

    @Test(expected = InvalidParameterException.class)
    public void calculationEquation_EquationWithInvalidOperator_InvalidOperatorException() throws Exception
    {
        ClosingBracket closingBracket=new ClosingBracket();
        List<EquationComponent> input=Arrays.asList(firstNumber,closingBracket,secondNumber);

        Mockito.when(parser.formatFromInfixToReversedPolishNotation(input))
                .thenReturn(Arrays.asList(firstNumber,secondNumber,closingBracket));

        algorithm.calculateEquation(input);
    }

    @Test
    public void calculationEquation_LegalEquation() throws Exception
    {
        List<EquationComponent> input=Arrays.asList(secondNumber,addition,secondNumber,power,secondNumber);

        Mockito.when(parser.formatFromInfixToReversedPolishNotation(input))
                .thenReturn(Arrays.asList(secondNumber,secondNumber,secondNumber,power,addition));

        Mockito.when(secondNumber.getValue()).thenReturn("3.0");
        Mockito.when(power.compute(3.0,3.0)).thenReturn(27.0);
        Mockito.when(addition.compute(3.0,27.0)).thenReturn(30.0);

        assertThat(algorithm.calculateEquation(input),is(30.0));
    }
}