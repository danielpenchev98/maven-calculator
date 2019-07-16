package calculator.inputControl;


import calculator.computation.*;
import calculator.exceptions.InvalidOperatorException;

import java.util.EmptyStackException;
import java.util.Stack;

//TODO shorten the names of functions - the beginning of several functions is the same "addOperatorFromContainer...."

public class ReversePolishNotationParser {

    private Stack<MathOperator> operatorContainer;
    private StringBuilder reversedPolishEquation;
    private EquationValidator checker;
    private MathOperatorFactory operatorFactory;


    public ReversePolishNotationParser(final EquationValidator validator,final MathOperatorFactory factory)
    {
        operatorContainer=new Stack<>();
        reversedPolishEquation=new StringBuilder();
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
     * @param equation infix notation
     * @return reverse polish notation
     */
    public String formatFromInfixToReversedPolishNotation(final String equation) throws EmptyStackException, InvalidOperatorException
    {
        String[] components = getIndividualComponents(equation);
        for(String component:components)
        {
            if(checker.isValidNumber(component))
            {
                addComponentToEquation(component);
            }
            else
            {
                processOperator(component);
            }
        }
        addAllOperatorsLeftInTheContainerToEquation();

        return reversedPolishEquation.toString().trim();
    }

    private void processOperator(final String component) throws InvalidOperatorException, EmptyStackException
    {
        MathOperator currOperation = operatorFactory.createOperation(component);

        if (currOperation instanceof MathArithmeticOperator)
        {
            addOperatorsFromContainerDependingOnPriorityAndAssociativity((MathArithmeticOperator) currOperation);
            operatorContainer.add(currOperation);
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
        reversedPolishEquation.append(component).append(" ");
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

    private String[] getIndividualComponents(final String equation)
    {
        return equation.split(" ");
    }

}
