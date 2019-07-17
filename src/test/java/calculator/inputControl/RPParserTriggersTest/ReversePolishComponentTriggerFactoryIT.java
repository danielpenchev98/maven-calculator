package calculator.inputControl.RPParserTriggersTest;

import calculator.computation.MathArithmeticOperatorFactory;
import calculator.exceptions.InvalidOperatorException;
import calculator.inputControl.EquationValidator;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ReversePolishComponentTriggerFactoryIT {

    private ReversePolishComponentTriggerFactory triggerFactory;

    @Before
    public void setUp()
    {
        triggerFactory = new ReversePolishComponentTriggerFactory(new EquationValidator(),new MathArithmeticOperatorFactory());
    }

    @Test
    public void createTrigger_StringRepresentingLegitNumberAsParam_CreateNumberTrigger() throws InvalidOperatorException {
        ReversePolishComponentTrigger trigger=triggerFactory.createTrigger("102.90");
        assertTrue(trigger instanceof NumberTrigger);
    }

    @Test
    public void createTrigger_StringRepresentingClosingBracket_CreateClosingBracketTrigger() throws InvalidOperatorException
    {
        ReversePolishComponentTrigger trigger=triggerFactory.createTrigger(")");
        assertTrue(trigger instanceof ClosingBracketTrigger);
    }

    @Test
    public void createTrigger_StringRepresentingOpeningBracket_CreateOpeningBracketTrigger() throws InvalidOperatorException
    {
        ReversePolishComponentTrigger trigger=triggerFactory.createTrigger("(");
        assertTrue(trigger instanceof OpeningBracketTrigger);
    }

    @Test(expected = InvalidOperatorException.class)
    public void createTrigger_StringRepresentingIllegalArithmeticOperator_InvalidOperatorExceptionThrown() throws InvalidOperatorException
    {
        triggerFactory.createTrigger("#");
    }
}