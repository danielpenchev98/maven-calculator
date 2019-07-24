package com.calculator.core.inputprocessing;

import com.calculator.core.computation.*;

import java.util.EmptyStackException;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class ReversePolishNotationParser {

    private Stack<EquationComponent> operatorContainer;

    private List<EquationComponent> reversedPolishEquation;

    public ReversePolishNotationParser() {
        operatorContainer = new Stack<>();
        reversedPolishEquation = new LinkedList<>();
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
     *
     * @param components infix notation
     * @return reverse polish notation
     */
    public List<EquationComponent> formatFromInfixToReversedPolishNotation(final List<EquationComponent> components) throws EmptyStackException {
        for (EquationComponent component : components) {
            processComponent(component);
        }
        addAllOperatorsLeftInTheContainerToEquation();

        return reversedPolishEquation;
    }

    private void processComponent(final EquationComponent component) {

        if (component instanceof NumberComponent)
        {
            addComponentToEquation(component);
        }
        else if (component instanceof MathArithmeticOperator)
        {
            addOperatorsFromContainerDependingOnPriorityAndAssociativity((MathArithmeticOperator) component);
            addComponentToContainer(component);
        }
        else if (component instanceof ClosingBracket)
        {
            addOperatorsFromContainerToEquationTillOpeningBracketIsFound();
        }
        else
        {
            addComponentToContainer(component);
        }
    }

    private void addComponentToEquation(final EquationComponent component) {
        reversedPolishEquation.add(component);
    }

    private void addComponentToContainer(final EquationComponent component) {
        operatorContainer.push(component);
    }

    private boolean hasSpareOperators() {
        return operatorContainer.size() > 0;
    }

    private void addOperatorsFromContainerDependingOnPriorityAndAssociativity(final MathArithmeticOperator component) {
        while (hasSpareOperators() && shouldTransferOperatorsFromContainerToEquation(component)) {

            addComponentToEquation(operatorContainer.pop());

        }
    }

    private boolean shouldTransferOperatorsFromContainerToEquation(final MathArithmeticOperator component) {
        if (operatorContainer.peek() instanceof OpeningBracket) {
            return false;
        }

        MathArithmeticOperator nextOperatorInContainer = (MathArithmeticOperator) operatorContainer.peek();
        int differenceInPriority = calculateDifferenceInPriority(nextOperatorInContainer, component);
        final boolean firstCondition = differenceInPriority > 0;
        final boolean secondCondition = differenceInPriority == 0 && nextOperatorInContainer.isLeftAssociative();

        return firstCondition || secondCondition;
    }

    private int calculateDifferenceInPriority(final MathArithmeticOperator leftOperator, final MathArithmeticOperator rightOperator) {
        return leftOperator.getPriority() - rightOperator.getPriority();
    }


    private void addOperatorsFromContainerToEquationTillOpeningBracketIsFound() {

        while (hasSpareOperators() && isNotOpeningBracket(operatorContainer.peek())) {
            addComponentToEquation(operatorContainer.pop());
        }
        operatorContainer.pop();
    }


    private boolean isNotOpeningBracket(final EquationComponent component)
    {
        return !(component instanceof OpeningBracket);
    }

    private void addAllOperatorsLeftInTheContainerToEquation() {

        while (hasSpareOperators()) {
            addComponentToEquation(operatorContainer.pop());
        }
    }
}