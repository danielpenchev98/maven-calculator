package calculator.inputcontrol;

import calculator.computation.*;

import java.util.EmptyStackException;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class ReversePolishNotationParser {

    private Stack<EquationComponent> operatorContainer;

    private List<String> reversedPolishEquation;

    private ComponentValidator checker;

    private EquationComponentFactory operatorFactory;

    public ReversePolishNotationParser(final ComponentValidator validator,final EquationComponentFactory factory)
    {
        operatorContainer=new Stack<>();
        reversedPolishEquation=new LinkedList<>();
        checker=validator;
        operatorFactory=factory;
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

    public List<String> formatFromInfixToReversedPolishNotation(final List<String> components) throws EmptyStackException
    {
        for(String component:components)
        {
            if(checker.isValidNumber(component))
            {
                addComponentToEquation(component);
                continue;
            }

            EquationComponent currOperation= operatorFactory.createComponent(component);

            if (currOperation instanceof MathArithmeticOperator)
            {
                addOperatorsFromContainerDependingOnPriorityAndAssociativity((MathArithmeticOperator) currOperation);
                operatorContainer.add( currOperation);
            }
            else if(currOperation instanceof ClosingBracket)
            {
                addOperatorsFromContainerToEquationTillOpeningBracketIsFound();
            }
            else
            {
                operatorContainer.add(currOperation);
            }
        }
        addAllOperatorsLeftInTheContainerToEquation();

        return reversedPolishEquation;
    }

    private void addAllOperatorsLeftInTheContainerToEquation() {

        while( hasSpareOperators() )
        {
            addComponentToEquation(((MathArithmeticOperator)operatorContainer.pop()).getSymbol());
        }
    }

    private void addOperatorsFromContainerToEquationTillOpeningBracketIsFound(){

        while(hasSpareOperators()&&!(operatorContainer.peek() instanceof OpeningBracket))
        {
            addComponentToEquation(((MathArithmeticOperator)operatorContainer.pop()).getSymbol());
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
                addComponentToEquation(nextOperatorInContainer.getSymbol());
                continue;
            }
            break;
        }
    }

    private void addComponentToEquation(final String component)
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