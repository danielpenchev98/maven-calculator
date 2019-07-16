package calculator.inputControl.RPParserTriggers;


import java.util.Stack;

public class ClosingBracketTrigger implements ReversePolishComponentTrigger {

    @Override
    public void trigger(final Stack<ReversePolishComponentTrigger> container, final StringBuilder equation) {

        while(hasSpareOperators(container)&& isMathArithmeticOperatorTrigger(container.peek()))
        {
            addMathOperatorToEquation(container.pop(),equation);
        }
        container.pop();
    }

    private boolean hasSpareOperators(Stack<ReversePolishComponentTrigger> container)
    {
        return container.size()!=0;
    }

    private boolean isMathArithmeticOperatorTrigger(final ReversePolishComponentTrigger trigger)
    {
        return trigger instanceof MathArithmeticOperatorTrigger;
    }

    private void addMathOperatorToEquation(final ReversePolishComponentTrigger operatorTrigger,final StringBuilder equation)
    {
        String operatorSymbol=((MathArithmeticOperatorTrigger)operatorTrigger).getSymbol();
        equation.append(operatorSymbol).append(" ");
    }

}
