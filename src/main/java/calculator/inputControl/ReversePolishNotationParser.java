package calculator.inputControl;


import calculator.computation.MathOperation;
import calculator.computation.MathOperationFactory;
import calculator.container.ComponentSupplier;
import calculator.exceptions.InvalidOperatorException;
import calculator.exceptions.OutOfItemsException;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class ReversePolishNotationParser {

    private Map<String,OperatorSettings> priorityOfOperators;

    private ComponentSupplier<String> operatorContainer;
    private StringBuilder reversedPolishEquation;
    private EquationValidator checker;

    private class OperatorSettings
    {
        public int priority;
        public boolean isLeftAssociative;

        public OperatorSettings(final int prior, final boolean leftAssoci)
        {
            priority=prior;
            isLeftAssociative=leftAssoci;
        }
    }


    public ReversePolishNotationParser()
    {
       priorityOfOperators=new HashMap<String,OperatorSettings>(){{
            put("+", new OperatorSettings(2,true));
            put("-", new OperatorSettings(2,true));
            put("/", new OperatorSettings(3,true));
            put("*", new OperatorSettings(4,true));
            put("^",new OperatorSettings(4,false));
        }};
       operatorContainer=new ComponentSupplier<>();
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
    public String formatFromInfixToReversedPolishNotation(final String equation) throws OutOfItemsException
    {
        String[] components=getIndividualComponents(equation);
        for(String component:components)
        {
            if(checker.isValidNumber(component))
            {
                addComponentToEquation(component);
            }
            else if (checker.isValidArithmeticOperator(component))
            {
                addOperatorsFromContainerDependingOnPriorityAndAssociativity(component);
                operatorContainer.addItem(component);
            }
            else if(isClosingBracket(component))
            {
                addOperatorsFromContainerToEquationTillOpeningBracketIsFound();
            }
            else
            {
                operatorContainer.addItem(component);
            }
        }
        addAllOperatorsLeftInTheContainerToEquation();
        return reversedPolishEquation.toString();
    }


    private void addAllOperatorsLeftInTheContainerToEquation() throws OutOfItemsException {

        while(operatorContainer.numberOfItemsAvailable()!=0)
        {
            addComponentToEquation(operatorContainer.receiveNextItem());
        }
    }

    private void addOperatorsFromContainerToEquationTillOpeningBracketIsFound() throws OutOfItemsException {

        while(hasSpareOperators()&&!isOpeningBracket(operatorContainer.viewNextItem()))
        {
            addComponentToEquation(operatorContainer.receiveNextItem());
        }
        operatorContainer.removeNextItem();
    }

    private void addOperatorsFromContainerDependingOnPriorityAndAssociativity(final String component) throws OutOfItemsException
    {
        while (hasSpareOperators()&& checker.isValidArithmeticOperator(operatorContainer.viewNextItem())) {

            final boolean condition1=hasLowerPriority(operatorContainer.viewNextItem(), component);
            final boolean condition2=hasEqualPriority(operatorContainer.viewNextItem(), component) && isLeftAssoiciative(operatorContainer.viewNextItem());

            if(condition1||condition2) {
                addComponentToEquation(operatorContainer.receiveNextItem());
                continue;
            }
            break;
        }
    }

    private void addComponentToEquation(final String component)
    {
        reversedPolishEquation.append(component).append(" ");
    }

    private boolean hasLowerPriority(final String previousOperator,final String currentOperator)
    {
        return priorityOfOperators.get(previousOperator).priority > priorityOfOperators.get(currentOperator).priority;
    }

    private boolean hasEqualPriority(final String previousOperator,final String currentOperator)
    {
        return priorityOfOperators.get(previousOperator).priority == priorityOfOperators.get(currentOperator).priority;
    }

    private boolean isLeftAssoiciative(final String component)
    {
        return priorityOfOperators.get(component).isLeftAssociative;
    }

    private boolean isOpeningBracket(final String item)
    {
        return item.equals("(");
    }

    private boolean isClosingBracket(final String item)
    {
        return item.equals(")");
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
