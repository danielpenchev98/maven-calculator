package calculator.inputControl.RPParserTriggersTest;

import calculator.computation.Addition;
import org.junit.Before;
import org.junit.Test;

import java.util.Stack;

import static org.junit.Assert.*;

public class ClosingBracketTriggerIT {

    private MathArithmeticOperatorTrigger operatorTrigger;

    @Before
    public void setUp() {
        operatorTrigger=new MathArithmeticOperatorTrigger(new Addition());
    }

    @Test
    public void trigger() {

        Stack<ReversePolishComponentTrigger> container=new Stack<>();
        container.push(operatorTrigger);
        container.push(new OpeningBracketTrigger());
        container.push(operatorTrigger);
        container.push(operatorTrigger);

        StringBuilder equation=new StringBuilder();

        ReversePolishComponentTrigger currentTrigger=new ClosingBracketTrigger();
        currentTrigger.trigger(container,equation);

        assertEquals("+ + ",equation.toString());
    }
}