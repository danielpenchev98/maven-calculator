package calculator.inputControl.parserTriggers;

import calculator.computation.Addition;
import calculator.computation.Multiplication;
import calculator.computation.Power;
import org.junit.Before;
import org.junit.Test;

import java.util.Stack;

import static org.junit.Assert.*;

public class MathArithmeticOperatorTriggerIT {

    private MathArithmeticOperatorTrigger operatorMultiplicationTrigger;

    private MathArithmeticOperatorTrigger operatorAdditionTrigger;

    private MathArithmeticOperatorTrigger operatorPowerTrigger;

    private Stack<ReversePolishComponentTrigger> container;

    @Before
    public void setUp(){
        container=new Stack<>();
        operatorAdditionTrigger=new MathArithmeticOperatorTrigger(new Addition());
        operatorPowerTrigger=new MathArithmeticOperatorTrigger(new Power());
        container.add(operatorAdditionTrigger);
        container.add(new OpeningBracketTrigger());

        operatorMultiplicationTrigger=new MathArithmeticOperatorTrigger(new Multiplication());
    }

    @Test
    public void trigger_MathArithmeticalOperatorWithLowerPriority_TransferSomeComponentsFromStackToEquation() {

        container.push(operatorPowerTrigger);
        container.push(operatorPowerTrigger);

        StringBuilder equation=new StringBuilder();

        operatorMultiplicationTrigger.trigger(container,equation);
        assertEquals("^ ^ ",equation.toString());
        assertEquals(3,container.size());
    }

    @Test
    public void trigger_MathArithmeticalOperatorWithGreaterPriority_TransferSomeComponentsFromStackToEquation() {

        container.push(operatorAdditionTrigger);
        container.push(operatorAdditionTrigger);

        StringBuilder equation=new StringBuilder();

        operatorMultiplicationTrigger.trigger(container,equation);
        assertEquals("",equation.toString());
        assertEquals(5,container.size());
    }

    @Test
    public void trigger_MathArithmeticalOperatorWithEqualPriority_TransferSomeComponentsFromStackToEquation() {

        container.push(operatorAdditionTrigger);
        container.push(operatorMultiplicationTrigger);

        StringBuilder equation=new StringBuilder();

        operatorMultiplicationTrigger.trigger(container,equation);
        assertEquals("* ",equation.toString());
        assertEquals(4,container.size());
    }

    @Test
    public void compareTo_TwoEqualPriorityOperatorTriggers() {
        assertEquals(0,operatorMultiplicationTrigger.compareTo(operatorMultiplicationTrigger));
    }

    @Test
    public void compareTo_TwoDifferentPriorityOperatorTriggers()
    {
        assertEquals(-1,operatorMultiplicationTrigger.compareTo(operatorPowerTrigger));
    }
}