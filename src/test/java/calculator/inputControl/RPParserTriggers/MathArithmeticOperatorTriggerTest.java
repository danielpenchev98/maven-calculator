package calculator.inputControl.RPParserTriggers;

import calculator.computation.*;

import org.junit.Before;
import org.junit.Test;

import java.util.Stack;

import static org.junit.Assert.*;

public class MathArithmeticOperatorTriggerTest {

    private MathArithmeticOperatorTrigger operatorTrigger;

    private Stack<ReversePolishComponentTrigger> container;

    @Before
    public void setUp()
    {
       operatorTrigger=new MathArithmeticOperatorTrigger(new Multiplication());
        container=new Stack<>();
        container.push(new MathArithmeticOperatorTrigger(new Addition()));
        container.push(new OpeningBracketTrigger());
    }

    @Test
    public void trigger_MathArithmeticalOperatorWithLowerPriority_TransferSomeComponentsFromStackToEquation() {
        container.push(new MathArithmeticOperatorTrigger(new Power()));
        container.push(new MathArithmeticOperatorTrigger(new Power()));

        StringBuilder equation=new StringBuilder();

        operatorTrigger.trigger(container,equation);
        assertEquals("^ ^ ",equation.toString());
        assertEquals(3,container.size());
    }

    @Test
    public void trigger_MathArithmeticalOperatorWithGreaterPriority_TransferSomeComponentsFromStackToEquation() {
        container.push(new MathArithmeticOperatorTrigger(new Addition()));
        container.push(new MathArithmeticOperatorTrigger(new Addition()));

        StringBuilder equation=new StringBuilder();

        operatorTrigger.trigger(container,equation);
        assertEquals("",equation.toString());
        assertEquals(5,container.size());
    }

    @Test
    public void trigger_MathArithmeticalOperatorWithEqualPriority_TransferSomeComponentsFromStackToEquation() {
        container.push(new MathArithmeticOperatorTrigger(new Addition()));
        container.push(new MathArithmeticOperatorTrigger(new Multiplication()));

        StringBuilder equation=new StringBuilder();

        operatorTrigger.trigger(container,equation);
        assertEquals("* ",equation.toString());
        assertEquals(4,container.size());
    }

    @Test
    public void compareTo_TwoEqualPriorityOperatorTriggers() {
        assertEquals(0,operatorTrigger.compareTo(operatorTrigger));
    }

    @Test
    public void compareTo_TwoDifferentPriorityOperatorTriggers()
    {
        MathArithmeticOperatorTrigger greaterPriority = new MathArithmeticOperatorTrigger(new Power());
        assertEquals(-1,operatorTrigger.compareTo(greaterPriority));
    }
}