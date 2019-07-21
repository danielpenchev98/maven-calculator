package calculator.computation;

import calculator.exceptions.InvalidComponentException;
import calculator.exceptions.InvalidParameterException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class EquationComponentFactoryTest {

    private EquationComponentFactory factory;

    @Before
    public void setUp()
    {
        factory=new EquationComponentFactory();
    }


    @Test
    public void createComponent_CreateAdditionObject_ObjectCreated() throws InvalidComponentException
    {
        EquationComponent component = factory.createComponent("+");
        assertTrue(component instanceof Addition);
    }

    @Test
    public void createComponent_CreateSubtractionObject_ObjectCreated() throws InvalidComponentException
    {
        EquationComponent component = factory.createComponent("-");
        assertTrue(component instanceof Subtraction);
    }

    @Test
    public void createComponent_CreateMultiplicationObject_ObjectCreated() throws InvalidComponentException
    {
        EquationComponent component = factory.createComponent("*");
        assertTrue(component instanceof Multiplication);
    }

    @Test
    public void createComponent_CreateDivisionObject_ObjectCreated() throws InvalidComponentException
    {
        EquationComponent component = factory.createComponent("/");
        assertTrue(component instanceof Division);
    }

    @Test
    public void createComponent_CreatePowerObject_ObjectCreated() throws InvalidComponentException
    {
        EquationComponent component = factory.createComponent("^");
        assertTrue(component instanceof Power);
    }

    @Test
    public void createComponent_CreateOpeningBracketObject_ObjectCreated() throws InvalidComponentException
    {
        EquationComponent component = factory.createComponent("(");
        assertTrue(component instanceof OpeningBracket);
    }

    @Test
    public void createComponent_CreateClosingBracketObject_ObjectCreated() throws InvalidComponentException
    {
        EquationComponent component = factory.createComponent(")");
        assertTrue(component instanceof ClosingBracket);
    }

    @Test
    public void createComponent_CreateNumberComponentObject_ObjectCreated() throws InvalidComponentException
    {
        EquationComponent component=factory.createComponent("101.101");
        assertTrue(component instanceof NumberComponent);
    }

    @Test (expected = InvalidComponentException.class)
    public void createComponent_CreateIllegalOperationObject_InvalidParameterExceptionThrown() throws InvalidComponentException
    {
        factory.createComponent("illegal");
    }
}