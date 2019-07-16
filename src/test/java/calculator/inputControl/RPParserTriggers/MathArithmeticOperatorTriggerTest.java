package calculator.inputControl.RPParserTriggers;

import calculator.computation.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Stack;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class MathArithmeticOperatorTriggerTest {


    private MathArithmeticOperatorTrigger operatorAdditionTrigger;

    private MathArithmeticOperatorTrigger operatorMultplicationTrigger;

    private MathArithmeticOperatorTrigger operatorPowerTrigger;

    @Mock
    private Stack<ReversePolishComponentTrigger> container;

    @Mock
    private MathArithmeticOperator addition;

    @Mock
    private MathArithmeticOperator multiplication;

    @Mock
    private MathArithmeticOperator power;



    @Before
    public void setUp()
    {
        operatorAdditionTrigger=new MathArithmeticOperatorTrigger(addition);
        operatorMultplicationTrigger=new MathArithmeticOperatorTrigger(multiplication);
        operatorPowerTrigger=new MathArithmeticOperatorTrigger(power);

        container=new Stack<>();
        container.push(operatorAdditionTrigger);
        container.push(new OpeningBracketTrigger());

        Mockito.when(addition.getPriority()).thenReturn(2);
        Mockito.when(multiplication.getPriority()).thenReturn(3);
        Mockito.when(power.getPriority()).thenReturn(4);

    }

    @Test
    public void trigger_MathArithmeticalOperatorWithLowerPriority_TransferSomeComponentsFromStackToEquation() {

        Mockito.when(power.getSymbol()).thenReturn("^");

        container.push(operatorPowerTrigger);
        container.push(operatorPowerTrigger);

        StringBuilder equation=new StringBuilder();

        operatorMultplicationTrigger.trigger(container,equation);
        assertEquals("^ ^ ",equation.toString());
        assertEquals(3,container.size());
    }

    @Test
    public void trigger_MathArithmeticalOperatorWithGreaterPriority_TransferSomeComponentsFromStackToEquation() {

        container.push(operatorAdditionTrigger);
        container.push(operatorAdditionTrigger);

        StringBuilder equation=new StringBuilder();

        operatorMultplicationTrigger.trigger(container,equation);
        assertEquals("",equation.toString());
        assertEquals(5,container.size());
    }

    @Test
    public void trigger_MathArithmeticalOperatorWithEqualPriority_TransferSomeComponentsFromStackToEquation() {

        Mockito.when(multiplication.getSymbol()).thenReturn("*");
        Mockito.when(multiplication.isLeftAssociative()).thenReturn(true);


        container.push(operatorAdditionTrigger);
        container.push(operatorMultplicationTrigger);

        StringBuilder equation=new StringBuilder();

        operatorMultplicationTrigger.trigger(container,equation);
        assertEquals("* ",equation.toString());
        assertEquals(4,container.size());
    }

    @Test
    public void compareTo_TwoEqualPriorityOperatorTriggers() {
        assertEquals(0,operatorMultplicationTrigger.compareTo(operatorMultplicationTrigger));
    }

    @Test
    public void compareTo_TwoDifferentPriorityOperatorTriggers()
    {
        assertEquals(-1,operatorMultplicationTrigger.compareTo(operatorPowerTrigger));
    }
}