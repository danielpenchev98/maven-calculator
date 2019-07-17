package calculator.inputControl.RPParserTriggersTest;

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

        while ( hasSpareOperators(container) && isMathArithmeticOperatorTrigger(container.peek())) {

            MathArithmeticOperatorTrigger nextTriggerInContainer = (MathArithmeticOperatorTrigger) container.peek();
            final boolean lowerPriority = hasLowerPriority(nextTriggerInContainer);
            final boolean equalPriorityAndLeftAssociative = hasEqualPriority(nextTriggerInContainer) && nextTriggerInContainer.wrappedOperator.isLeftAssociative();

            if( lowerPriority || equalPriorityAndLeftAssociative ) {
                container.pop();
                addMathOperatorToEquation( nextTriggerInContainer, equation );
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


    //TODO should refactor it - maybe
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
    private void addMathOperatorToEquation(final MathArithmeticOperatorTrigger operatorTrigger,final StringBuilder equation)
    {
        String operatorSymbol=operatorTrigger.getSymbol();
        equation.append(operatorSymbol).append(" ");
    }

    private boolean isMathArithmeticOperatorTrigger(final ReversePolishComponentTrigger trigger)
    {
        return trigger instanceof MathArithmeticOperatorTrigger;
    }


}
