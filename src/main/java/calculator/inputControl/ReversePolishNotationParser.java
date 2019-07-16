package calculator.inputControl;


import calculator.computation.*;
import calculator.exceptions.InvalidOperatorException;
import calculator.inputControl.triggers.MathArithmeticOperatorTrigger;
import calculator.inputControl.triggers.ReversePolishComponentTrigger;
import calculator.inputControl.triggers.ReversePolishComponentTriggerFactory;

import java.util.EmptyStackException;
import java.util.Stack;

//TODO shorten the names of functions - the beginning of several functions is the same "addOperatorFromContainer...."

public class ReversePolishNotationParser {

    private Stack<ReversePolishComponentTrigger> operatorContainer;
    private StringBuilder reversedPolishEquation;
    private EquationValidator checker;
    private ReversePolishComponentTriggerFactory triggerFactory;


    public ReversePolishNotationParser(final EquationValidator validator,final ReversePolishComponentTriggerFactory factory)
    {
        operatorContainer=new Stack<>();
        reversedPolishEquation=new StringBuilder();
        checker=validator;
        triggerFactory=factory;
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
                continue;
            }

            ReversePolishComponentTrigger currOperation=triggerFactory.createTrigger(component);

            currOperation.trigger(operatorContainer,reversedPolishEquation);

        }
        addAllOperatorsLeftInTheContainerToEquation();

        return reversedPolishEquation.toString().trim();
    }


    private void addAllOperatorsLeftInTheContainerToEquation() {

        while( hasSpareOperators() )
        {
            addComponentToEquation(((MathArithmeticOperatorTrigger)operatorContainer.pop()).getSymbol());
        }
    }


    private void addComponentToEquation(final String component)
    {
        reversedPolishEquation.append(component).append(" ");
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
