package com.calculator.core.inputformatting;

import com.calculator.core.operators.*;
import com.calculator.core.exceptions.InvalidComponentException;
import com.calculator.core.exceptions.InvalidEquationException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(MockitoJUnitRunner.class)
public class EquationFormatterTest {

    private EquationFormatter formatter;

    @Mock
    private EquationStructureValidator validator;

    @Mock
    private EquationComponentProvider provider;

    private NumberComponent firstNumber;
    private NumberComponent secondNumber;
    private Addition addition;
    private OpeningBracket openingBracket;
    private ClosingBracket closingBracket;


    @Before
    public void setUp() {
        formatter = new EquationFormatter(validator, provider);
        firstNumber = new NumberComponent("-1.0");
        secondNumber = new NumberComponent("2.0");
        addition = new Addition();
        openingBracket = new OpeningBracket();
        closingBracket = new ClosingBracket();
    }

    @Test(expected = InvalidEquationException.class)
    public void doFormat_EmptyInput_Illegal() throws Exception {
        Mockito.doThrow(new InvalidEquationException("Empty equation")).when(validator).validateEquationStructure("");
        formatter.doFormat("");
    }

    @Test
    public void doFormat_InputWithManyJunkSpaces_FormattedInput() throws Exception {

        Mockito.when(provider.getComponent("-1.0")).thenReturn(firstNumber);
        Mockito.when(provider.getComponent("2.0")).thenReturn(secondNumber);
        Mockito.when(provider.getComponent("+")).thenReturn(addition);

        List<EquationComponent> expected = Arrays.asList(firstNumber, addition, secondNumber);
        assertThat(formatter.doFormat("-1.0 +     2.0"), is(expected));

        Mockito.verify(provider, times(3)).getComponent(ArgumentMatchers.anyString());
        Mockito.verify(validator, times(1)).validateEquationStructure(ArgumentMatchers.anyString());
    }

    @Test
    public void doFormat_InputWithoutAnySpaces_FormattedInput() throws Exception {
        Mockito.when(provider.getComponent("(")).thenReturn(openingBracket);
        Mockito.when(provider.getComponent("-1.0")).thenReturn(firstNumber);
        Mockito.when(provider.getComponent("+")).thenReturn(addition);
        Mockito.when(provider.getComponent("2.0")).thenReturn(secondNumber);
        Mockito.when(provider.getComponent(")")).thenReturn(closingBracket);

        List<EquationComponent> expected = Arrays.asList(openingBracket, firstNumber, addition, secondNumber, closingBracket);
        assertThat(formatter.doFormat("(-1.0+2.0)"), is(expected));

        Mockito.verify(provider, times(5)).getComponent(ArgumentMatchers.anyString());
        Mockito.verify(validator, times(1)).validateEquationStructure(ArgumentMatchers.anyString());
    }

    @Test
    public void doFormat_InputWithSpaceBetweenNumberAndItsSign_FormattedInput() throws Exception {
        Mockito.when(provider.getComponent("2.0")).thenReturn(secondNumber);
        Mockito.when(provider.getComponent("+")).thenReturn(addition);
        Mockito.when(provider.getComponent("(")).thenReturn(openingBracket);
        Mockito.when(provider.getComponent("-1.0")).thenReturn(firstNumber);
        Mockito.when(provider.getComponent(")")).thenReturn(closingBracket);

        List<EquationComponent> expected = Arrays.asList(secondNumber, addition, openingBracket, firstNumber, closingBracket);
        assertThat(formatter.doFormat("2.0 + ( - 1.0 )"), is(expected));

        Mockito.verify(provider, times(5)).getComponent(ArgumentMatchers.anyString());
        Mockito.verify(validator, times(1)).validateEquationStructure(ArgumentMatchers.anyString());
    }

    @Test(expected = InvalidComponentException.class)
    public void doFormat_InputWithIllegalComponent_ExceptionThrown() throws Exception {
        Mockito.when(provider.getComponent("-1.0")).thenReturn(firstNumber);
        Mockito.when(provider.getComponent("+")).thenReturn(addition);
        Mockito.when(provider.getComponent("127.0.0.1")).thenThrow(new InvalidComponentException("Invalid component"));

        formatter.doFormat("-1.0+127.0.0.1");

        Mockito.verify(provider, times(3)).getComponent(ArgumentMatchers.anyString());
        Mockito.verify(validator, times(1)).validateEquationStructure(ArgumentMatchers.anyString());
    }

    @Test(expected = InvalidEquationException.class)
    public void doFormat_EquationWithIllegalStructure__ExceptionThrown() throws Exception {
        Mockito.doThrow(new InvalidEquationException("Invalid equation")).when(validator).validateEquationStructure("-1.0 2.0 +");
        formatter.doFormat("-1.0 2.0 +");

        Mockito.verify(provider, Mockito.never()).getComponent(ArgumentMatchers.anyString());
        Mockito.verify(validator, times(1)).validateEquationStructure(ArgumentMatchers.anyString());
    }
}