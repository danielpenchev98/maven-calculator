package com.calculator.core.operators;

import com.calculator.core.exceptions.InvalidComponentException;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.*;

public class EquationComponentProviderTest {

    private EquationComponentProvider provider;

    @Before
    public void setUp()
    {
        provider=new EquationComponentProvider();
    }


    @Test
    public void createComponent_CreateAdditionObject_ObjectCreated() throws InvalidComponentException
    {
        EquationComponent component = provider.getComponent("+");
        assertThat(component,is(instanceOf(Addition.class)));
    }

    @Test
    public void createComponent_CreateSubtractionObject_ObjectCreated() throws InvalidComponentException
    {
        EquationComponent component = provider.getComponent("-");
        assertThat(component,is(instanceOf(Subtraction.class)));
    }

    @Test
    public void createComponent_CreateMultiplicationObject_ObjectCreated() throws InvalidComponentException
    {
        EquationComponent component = provider.getComponent("*");
        assertThat(component,is(instanceOf(Multiplication.class)));
    }

    @Test
    public void createComponent_CreateDivisionObject_ObjectCreated() throws InvalidComponentException
    {
        EquationComponent component = provider.getComponent("/");
        assertThat(component,is(instanceOf(Division.class)));
    }

    @Test
    public void createComponent_CreatePowerObject_ObjectCreated() throws InvalidComponentException
    {
        EquationComponent component = provider.getComponent("^");
        assertThat(component,is(instanceOf(Power.class)));
    }

    @Test
    public void createComponent_CreateOpeningBracketObject_ObjectCreated() throws InvalidComponentException
    {
        EquationComponent component = provider.getComponent("(");
        assertThat(component,is(instanceOf(OpeningBracket.class)));
    }

    @Test
    public void createComponent_CreateClosingBracketObject_ObjectCreated() throws InvalidComponentException
    {
        EquationComponent component = provider.getComponent(")");
        assertThat(component,is(instanceOf(ClosingBracket.class)));
    }

    @Test
    public void createComponent_CreateNumberComponentObject_ObjectCreated() throws InvalidComponentException
    {
        EquationComponent component=provider.getComponent("101.101");
        assertThat(component,is(instanceOf(NumberComponent.class)));
    }

    @Test (expected = InvalidComponentException.class)
    public void createComponent_CreateIllegalOperationObject_InvalidParameterExceptionThrown() throws InvalidComponentException
    {
        provider.getComponent("illegal");
    }
}