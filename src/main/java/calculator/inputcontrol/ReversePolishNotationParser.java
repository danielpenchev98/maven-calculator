package calculator.inputcontrol;

import calculator.computation.*;
import calculator.exceptions.InvalidComponentException;

import java.util.EmptyStackException;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class ReversePolishNotationParser {

    private Stack<EquationComponent> operatorContainer;

    private List<EquationComponent> reversedPolishEquation;

    public ReversePolishNotationParser()
    {
        operatorContainer=new Stack<>();
        reversedPolishEquation=new LinkedList<>();

    }

    /**
     * Shunting yard algorithm
     * Rules:
     * 1) If it's a number - add to equation
     * 2) If it's an opening bracket - add to operator stack
     * 3) if it's an operator and:
     * 3.1) if the operator on the top of the stack has greater priority, add it to Equation and push the current operator
     * 3.2) if the operator on the top of the stack has lower or equal priority and is left associative, add current one to stack
     * 4) if it's closing bracket - add operators from the stack to equation, till you find opening bracket in the stack
     * 5) In the end add everything from the stack to equation
     * @param components infix notation
     * @return reverse polish notation
     */

    public List<EquationComponent> formatFromInfixToReversedPolishNotation(final List<EquationComponent> components) throws EmptyStackException, InvalidComponentException
    {
        for(EquationComponent component:components)
        {
            if(component instanceof NumberComponent)
            {
                addComponentToEquation(component);
            }
            else if (component instanceof MathArithmeticOperator)
            {
                addOperatorsFromContainerDependingOnPriorityAndAssociativity((MathArithmeticOperator) component);
                operatorContainer.add( component);
            }
            else if(component instanceof ClosingBracket)
            {
                addOperatorsFromContainerToEquationTillOpeningBracketIsFound();
            }
            else
            {
                operatorContainer.add(component);
            }
        }
        addAllOperatorsLeftInTheContainerToEquation();

        return reversedPolishEquation;
    }

    private void addAllOperatorsLeftInTheContainerToEquation() {

        while( hasSpareOperators() )
        {
            addComponentToEquation(operatorContainer.pop());
        }
    }

    private void addOperatorsFromContainerToEquationTillOpeningBracketIsFound(){

        while(hasSpareOperators()&&!(operatorContainer.peek() instanceof OpeningBracket))
        {
            addComponentToEquation(operatorContainer.pop());
        }
        operatorContainer.pop();
    }

    private void addOperatorsFromContainerDependingOnPriorityAndAssociativity(final MathArithmeticOperator component)
    {
        while ( hasSpareOperators()&& (operatorContainer.peek()) instanceof MathArithmeticOperator ) {

            MathArithmeticOperator nextOperatorInContainer =(MathArithmeticOperator) operatorContainer.peek();
            final boolean lowerPriority=hasLowerPriority(nextOperatorInContainer, component);
            final boolean equalPriorityAndLeftAssociative = hasEqualPriority(nextOperatorInContainer, component) && nextOperatorInContainer.isLeftAssociative();

            if( lowerPriority || equalPriorityAndLeftAssociative ) {
                operatorContainer.pop();
                addComponentToEquation(nextOperatorInContainer);
                continue;
            }
            break;
        }
    }

    private void addComponentToEquation(final EquationComponent component)
    {
        reversedPolishEquation.add(component);
    }

    private boolean hasLowerPriority(final MathArithmeticOperator previousOperator,final MathArithmeticOperator currentOperator)
    {
        return previousOperator.getPriority() > currentOperator.getPriority();
    }

    private boolean hasEqualPriority(final MathArithmeticOperator previousOperator,final MathArithmeticOperator currentOperator)
    {
        return previousOperator.getPriority() == currentOperator.getPriority();
    }

    private boolean hasSpareOperators()
    {
        return operatorContainer.size()>0;
    }
}