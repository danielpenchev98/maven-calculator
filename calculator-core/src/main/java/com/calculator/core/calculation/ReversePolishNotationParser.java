package com.calculator.core.calculation;

import com.calculator.core.operators.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class ReversePolishNotationParser {

    private Stack<ExpressionComponent> operatorContainer;

    private List<ExpressionComponent> reversedPolishExpression;

    public ReversePolishNotationParser() {
        operatorContainer=new Stack<>();
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
     * @param expression in infix notation
     * @return expression in reverse polish notation
     */
    public List<ExpressionComponent> formatFromInfixToReversedPolishNotation(final List<ExpressionComponent> expression) {
        reversedPolishExpression=new LinkedList<>();

        for (ExpressionComponent component : expression) {
            processComponent(component);
        }
        addAllOperatorsLeftInTheContainerToEquation();

        return reversedPolishExpression;
    }

    private void processComponent(final ExpressionComponent component) {
        if (component instanceof NumberComponent) {
            addComponentToExpression(component);
        } else if (component instanceof ClosingBracket) {
            addOperatorsFromContainerToExpressionTillOpeningBracketIsFound();
        } else if (component instanceof MathArithmeticOperator) {
            addOperatorsToExpressionDependingOnPriorityAndAssociativity((MathArithmeticOperator) component);
        } else {
            addComponentToContainer(component);
        }

    }

    private void addComponentToExpression(final ExpressionComponent component) {
        reversedPolishExpression.add(component);
    }

    private void addComponentToContainer(final ExpressionComponent component) {
        operatorContainer.push(component);
    }

    private boolean hasSpareOperators() {
        return operatorContainer.size() > 0;
    }

    private void addOperatorsToExpressionDependingOnPriorityAndAssociativity(final MathArithmeticOperator component) {
        while (hasSpareOperators() && shouldTransferOperatorsFromContainerToExpression(component)) {
            addComponentToExpression(operatorContainer.pop());
        }
        addComponentToContainer(component);
    }

    private boolean shouldTransferOperatorsFromContainerToExpression(final MathArithmeticOperator component) {

        if (operatorContainer.peek() instanceof OpeningBracket) {
            return false;
        }
        MathArithmeticOperator nextOperatorInContainer = (MathArithmeticOperator) operatorContainer.peek();

        int differenceInPriority = calculateDifferenceInPriority(nextOperatorInContainer, component);
        final boolean currOperatorHasLowerPriority = differenceInPriority > 0;
        final boolean nextOperatorInContainerEqualPriorityAndLeftAssociative = differenceInPriority == 0 && nextOperatorInContainer.isLeftAssociative();

        return currOperatorHasLowerPriority || nextOperatorInContainerEqualPriorityAndLeftAssociative;
    }

    private int calculateDifferenceInPriority(final MathArithmeticOperator leftOperator, final MathArithmeticOperator rightOperator) {
        return leftOperator.getPriority() - rightOperator.getPriority();
    }

    private void addOperatorsFromContainerToExpressionTillOpeningBracketIsFound() {

        while (hasSpareOperators() && isNotOpeningBracket(operatorContainer.peek())) {
            addComponentToExpression(operatorContainer.pop());
        }
        operatorContainer.pop();
    }

    private boolean isNotOpeningBracket(final ExpressionComponent component) {
        return !(component instanceof OpeningBracket);
    }

    private void addAllOperatorsLeftInTheContainerToEquation() {

        while (hasSpareOperators()) {
            addComponentToExpression(operatorContainer.pop());
        }
    }
}