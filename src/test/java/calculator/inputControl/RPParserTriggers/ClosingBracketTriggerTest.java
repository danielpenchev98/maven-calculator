package calculator.inputControl.RPParserTriggers;

import calculator.computation.Addition;
import calculator.computation.MathArithmeticOperator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Stack;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class ClosingBracketTriggerTest {

    private MathArithmeticOperatorTrigger operatorTrigger;

    @Mock
    private MathArithmeticOperator addition;

    @Before
    public void setUp()
    {
        operatorTrigger=new MathArithmeticOperatorTrigger(addition);
        Mockito.when(operatorTrigger.getSymbol()).thenReturn("+");
    }

    @Test
    public void trigger_TransferComponentsFromContainerToEquationWhileCurrentOperatorIsntOpeningBracket() {

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