package calculator.inputControl.RPParserTriggers;

import calculator.computation.Addition;
import org.junit.Test;

import java.util.Stack;

import static org.junit.Assert.*;

public class ClosingBracketTriggerTest {

    @Test
    public void trigger_TransferComponentsFromContainerToEquationWhileCurrentOperatorIsntOpeningBracket() {

        Stack<ReversePolishComponentTrigger> container=new Stack<>();
        container.push(new MathArithmeticOperatorTrigger(new Addition()));
        container.push(new OpeningBracketTrigger());
        container.push(new MathArithmeticOperatorTrigger(new Addition()));
        container.push(new MathArithmeticOperatorTrigger(new Addition()));

        StringBuilder equation=new StringBuilder();

        ReversePolishComponentTrigger currentTrigger=new ClosingBracketTrigger();
        currentTrigger.trigger(container,equation);

        assertEquals("+ + ",equation.toString());
    }
}