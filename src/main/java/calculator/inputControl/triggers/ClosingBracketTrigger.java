package calculator.inputControl.triggers;

import java.util.Stack;

public class ClosingBracketTrigger implements ReversePolishComponentTrigger {
    @Override
    public void trigger(final Stack<ReversePolishComponentTrigger> container, final StringBuilder equation) {

        while(hasSpareOperators(container)&&!(container.peek() instanceof OpeningBracketTrigger))
        {
            addComponentToEquation(((MathArithmeticOperatorTrigger)container.pop()).getSymbol(),equation);
        }
        container.pop();
    }

    private boolean hasSpareOperators(Stack<ReversePolishComponentTrigger> container)
    {
        return container.size()!=0;
    }

    private void addComponentToEquation(final String component,final StringBuilder equation)
    {
       equation.append(component).append(" ");
    }
}
