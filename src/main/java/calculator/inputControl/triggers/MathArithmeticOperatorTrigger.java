package calculator.inputControl.triggers;

import calculator.computation.MathArithmeticOperator;

import java.util.Stack;

public class MathArithmeticOperatorTrigger implements ReversePolishComponentTrigger, Comparable<MathArithmeticOperatorTrigger> {

    private MathArithmeticOperator wrappedOperator;

    public MathArithmeticOperatorTrigger(final MathArithmeticOperator operator)
    {
        wrappedOperator=operator;
    }

    @Override
    public void trigger(final Stack<ReversePolishComponentTrigger> container, final StringBuilder equation) {

        while ( hasSpareOperators(container)&& (container.peek()) instanceof MathArithmeticOperatorTrigger ) {

            MathArithmeticOperatorTrigger nextOperatorInContainer =(MathArithmeticOperatorTrigger) container.peek();
            final boolean lowerPriority=hasLowerPriority(nextOperatorInContainer);
            final boolean equalPriorityAndLeftAssociative = hasEqualPriority(nextOperatorInContainer) && nextOperatorInContainer.wrappedOperator.isLeftAssociative();

            if( lowerPriority || equalPriorityAndLeftAssociative ) {
                container.pop();
                addComponentToEquation(nextOperatorInContainer.getSymbol(),equation);
                continue;
            }
            break;
        }
        container.add(this);
    }

    private boolean hasSpareOperators(Stack<ReversePolishComponentTrigger> container)
    {
        return container.size()!=0;
    }

    @Override
    public int compareTo(MathArithmeticOperatorTrigger toCompare) {
        return wrappedOperator.getPriority()-toCompare.wrappedOperator.getPriority();
    }

    public String getSymbol()
    {
        return wrappedOperator.getSymbol();
    }

    private boolean hasLowerPriority(final MathArithmeticOperatorTrigger previousOperator)
    {
        return this.compareTo(previousOperator)<0;
    }

    private boolean hasEqualPriority(final MathArithmeticOperatorTrigger previousOperator)
    {
        return this.compareTo(previousOperator)==0;
    }
    private void addComponentToEquation(final String component,final StringBuilder equation)
    {
        equation.append(component).append(" ");
    }

}
