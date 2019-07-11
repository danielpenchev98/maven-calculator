package calculator.inputControl;


import calculator.computation.*;
import calculator.container.ComponentSupplier;
import calculator.exceptions.InvalidOperatorException;
import calculator.exceptions.OutOfItemsException;

public class ReversePolishNotationParser {

    private ComponentSupplier<MathOperator> operatorContainer;
    private StringBuilder reversedPolishEquation;
    private EquationValidator checker;


    public ReversePolishNotationParser()
    {

       operatorContainer=new ComponentSupplier();
        reversedPolishEquation=new StringBuilder();
        checker=new EquationValidator();
    }

    //TODO fix this documentation and to use Polymorphism
    /**
     * Shunting yard algorithm
     * Rules:
     * 1) If it's a number - add to equation
     * 2) If it's an opening bracket - add to operator stack
     * 3) if it's an operator :
     * 3.1) if the operator on the top of the stack has greater priority, add it to Equation and push the current operator
     * 3.2) if the operator on the top of the stack has lower or equal priority, add current one to stack
     * 4) if it's closing bracket - add everything from the stack to equation, till you find opening bracket in the stack
     * 5) In the end add everything from the stack to equation
     * @param equation infix notation
     * @return reverse polish notation
     */
    public String formatFromInfixToReversedPolishNotation(final String equation) throws OutOfItemsException, InvalidOperatorException
    {
        System.out.println(equation);

        String[] components=getIndividualComponents(equation);
        MathOperator currOperation;
        for(String component:components)
        {
            if(checker.isValidNumber(component))
            {
                addComponentToEquation(component);
            }
            else if (checker.isValidArithmeticOperator(component))
            {
                currOperation= MathOperatorFactory.createOperation(component);
                addOperatorsFromContainerDependingOnPriorityAndAssociativity((MathArithmeticOperator) currOperation);
                operatorContainer.addItem(currOperation);
            }
            else if(isClosingBracket(component))
            {
                addOperatorsFromContainerToEquationTillOpeningBracketIsFound();
            }
            else
            {
                currOperation=MathOperatorFactory.createOperation(component);
                operatorContainer.addItem(currOperation);
            }
        }
        addAllOperatorsLeftInTheContainerToEquation();
        return reversedPolishEquation.toString();
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

            MathArithmeticOperator nextOperatorInContainer=(MathArithmeticOperator) operatorContainer.receiveNextItem();
            final boolean condition1=hasLowerPriority(nextOperatorInContainer, component);
            final boolean condition2=hasEqualPriority(nextOperatorInContainer, component) && nextOperatorInContainer.isLeftAssociative();

            if(condition1||condition2) {
                addComponentToEquation(nextOperatorInContainer.getSymbol());
                continue;
            }
            break;
        }
    }

    private boolean isClosingBracket(final String component)
    {
        return component.equals(")");
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
