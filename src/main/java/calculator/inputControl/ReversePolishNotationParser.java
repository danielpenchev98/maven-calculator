package calculator.inputControl;


import calculator.computation.*;
import calculator.container.ComponentSupplier;
import calculator.exceptions.InvalidOperatorException;
import calculator.exceptions.OutOfItemsException;

//TODO shorten the names of functions - the beginning of several functions is the same "addOperatorFromContainer...."

public class ReversePolishNotationParser {

    private ComponentSupplier<MathOperator> operatorContainer;
    private StringBuilder reversedPolishEquation;
    private EquationValidator checker;


    public ReversePolishNotationParser()
    {

        operatorContainer=new ComponentSupplier<>();
        reversedPolishEquation=new StringBuilder();
        checker=new EquationValidator();
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
    public String formatFromInfixToReversedPolishNotation(final String equation) throws OutOfItemsException, InvalidOperatorException
    {
        String[] components=getIndividualComponents(equation);
        for(String component:components)
        {
            if(checker.isValidNumber(component))
            {
                addComponentToEquation(component);
                 continue;
            }

            MathOperator currOperation= MathOperatorFactory.createOperation(component);

            if (currOperation instanceof MathArithmeticOperator)
            {
                addOperatorsFromContainerDependingOnPriorityAndAssociativity((MathArithmeticOperator) currOperation);
                operatorContainer.addItem(currOperation);
            }
            else if(currOperation instanceof ClosingBracket)
            {
                addOperatorsFromContainerToEquationTillOpeningBracketIsFound();
            }
            else
            {
                operatorContainer.addItem(currOperation);
            }
        }
        addAllOperatorsLeftInTheContainerToEquation();

        return reversedPolishEquation.toString().trim();
    }


    private void addAllOperatorsLeftInTheContainerToEquation() throws OutOfItemsException {

        while(operatorContainer.numberOfItemsAvailable()!=0)
        {
            addComponentToEquation(((MathArithmeticOperator)operatorContainer.receiveNextItem()).getSymbol());
        }
    }

    private void addOperatorsFromContainerToEquationTillOpeningBracketIsFound() throws OutOfItemsException {

        while(hasSpareOperators()&&!(operatorContainer.viewNextItem() instanceof OpeningBracket))
        {
            addComponentToEquation(((MathArithmeticOperator)operatorContainer.receiveNextItem()).getSymbol());
        }
        operatorContainer.removeNextItem();
    }

    private void addOperatorsFromContainerDependingOnPriorityAndAssociativity(final MathArithmeticOperator component) throws OutOfItemsException
    {
        while (hasSpareOperators()&& (operatorContainer.viewNextItem()) instanceof MathArithmeticOperator) {

            MathArithmeticOperator nextOperatorInContainer=(MathArithmeticOperator) operatorContainer.viewNextItem();
            final boolean lowerPriority=hasLowerPriority(nextOperatorInContainer, component);
            final boolean equalPriorityAndLeftAssociative=hasEqualPriority(nextOperatorInContainer, component) && nextOperatorInContainer.isLeftAssociative();

            if(lowerPriority||equalPriorityAndLeftAssociative) {
                operatorContainer.removeNextItem();
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
        return operatorContainer.numberOfItemsAvailable()>0;
    }

    private String[] getIndividualComponents(final String equation)
    {
        return equation.split(" ");
    }

}
