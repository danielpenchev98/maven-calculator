package calculator.inputControl.parserTriggers;

import calculator.computation.MathArithmeticOperatorFactory;
import calculator.exceptions.InvalidOperatorException;
import calculator.inputControl.EquationValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class ReversePolishComponentTriggerFactoryTest {

    @InjectMocks
    private ReversePolishComponentTriggerFactory triggerFactory;

    @Mock
    private EquationValidator validator;

    @Mock
    private  MathArithmeticOperatorFactory arithmeticFactory;


    @Test
    public void createTrigger_StringRepresentingLegitNumberAsParam_CreateNumberTrigger() throws InvalidOperatorException {
        Mockito.when(validator.isValidNumber("102.90")).thenReturn(true);
        ReversePolishComponentTrigger trigger=triggerFactory.createTrigger("102.90");
        assertTrue(trigger instanceof NumberTrigger);
    }

    @Test
    public void createTrigger_StringRepresentingClosingBracket_CreateClosingBracketTrigger() throws InvalidOperatorException
    {
        Mockito.when(validator.isValidNumber(")")).thenReturn(false);
        ReversePolishComponentTrigger trigger=triggerFactory.createTrigger(")");
        assertTrue(trigger instanceof ClosingBracketTrigger);
    }

    @Test
    public void createTrigger_StringRepresentingOpeningBracket_CreateOpeningBracketTrigger() throws InvalidOperatorException
    {
        Mockito.when(validator.isValidNumber("(")).thenReturn(false);
        ReversePolishComponentTrigger trigger=triggerFactory.createTrigger("(");
        assertTrue(trigger instanceof OpeningBracketTrigger);
    }

    @Test(expected = InvalidOperatorException.class)
    public void createTrigger_StringRepresentingIllegalArithmeticOperator_InvalidOperatorExceptionThrown() throws InvalidOperatorException
    {
        Mockito.when(validator.isValidNumber("#")).thenReturn(false);
        Mockito.when(arithmeticFactory.createArithmeticOperation("#")).thenThrow(new InvalidOperatorException("Invalid operation"));
        triggerFactory.createTrigger("#");
    }

    //TODO should i add test for LegalArithmeticOperatorSymbol

}