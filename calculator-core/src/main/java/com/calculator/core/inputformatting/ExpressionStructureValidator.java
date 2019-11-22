package com.calculator.core.inputformatting;

import com.calculator.core.exceptions.InvalidExpressionException;
import com.calculator.core.exceptions.InvalidParameterException;

class ExpressionStructureValidator {

    void validateExpressionStructure(final String expression) throws InvalidExpressionException {
        if (expression == null) {
            throw new InvalidParameterException("validateExpression has received invalid parameter");
        }

        checkEmptyExpression(expression);
        checkBracketImbalance(expression);
        checkSequentialNonBracketComponentsOfTheSameType(expression);
        checkOperatorAsBeginningOrEndingOfExpressionScope(expression);
        checkEmptyBrackets(expression);
        checkMissingOperatorBeforeOrAfterBracket(expression);
    }

    private void checkBracketImbalance(final String expression) throws InvalidExpressionException {
        int bracketBalance = 0;
        boolean hasError = false;
        for (char symbol : expression.toCharArray()) {
            if (symbol == '(') {
                bracketBalance++;
            } else if (symbol == ')') {
                if (bracketBalance == 0) {
                    hasError = true;
                    break;
                }
                bracketBalance--;
            }

        }
        if (hasError || bracketBalance != 0) {
            throw new InvalidExpressionException("Missing or misplaced brackets");
        }
    }

    private void checkEmptyExpression(final String expression) throws InvalidExpressionException {
        if (hasEmptyExpression(expression)) {
            throw new InvalidExpressionException("Empty expression");
        }
    }

    private boolean hasEmptyExpression(final String expression) {
        return expression.trim().isEmpty();
    }

    private void checkSequentialNonBracketComponentsOfTheSameType(final String expression) throws InvalidExpressionException {
        if (hasSequentialNonBracketComponentsOfTheSameType(expression)) {
            throw new InvalidExpressionException("Sequential components of the same type");
        }
    }

    private boolean hasSequentialNonBracketComponentsOfTheSameType(final String expression) {
        return expression.matches(".*([0-9a-zA-Z.] +[0-9a-zA-Z.]+|.*[^0-9a-zA-Z.)( ] *[^0-9a-zA-Z.)( ]).*");
    }

    private void checkOperatorAsBeginningOrEndingOfExpressionScope(final String expression) throws InvalidExpressionException {
        if (hasAnOperatorAsBeginningOrEndingOfExpressionScope(expression)) {
            throw new InvalidExpressionException("Scope of expression ending or beginning with an operator");
        }
    }

    private boolean hasAnOperatorAsBeginningOrEndingOfExpressionScope(final String expression) {
        return expression.matches("(^|.*\\() *([^-0-9a-zA-Z.)( ]|- *[^0-9a-zA-Z. ]).*|.*[^0-9a-zA-Z.)( ] *(\\)|$)");
    }

    private void checkEmptyBrackets(final String expression) throws InvalidExpressionException {
        if (hasEmptyBrackets(expression)) {
            throw new InvalidExpressionException("Empty brackets");
        }
    }

    private boolean hasEmptyBrackets(final String expression) {
        return expression.matches(".*\\( *\\).*");
    }

    private void checkMissingOperatorBeforeOrAfterBracket(final String expression) throws InvalidExpressionException {
        if (hasMissingOperatorBeforeOrAfterBracket(expression)) {
            throw new InvalidExpressionException("Missing operator between a number and an opening bracket or a closing bracket and a number");
        }
    }

    private boolean hasMissingOperatorBeforeOrAfterBracket(final String expression) {
        return expression.matches(".*[0-9a-zA-Z.)] *\\(.*|.*\\) *[0-9a-zA-Z.(].*");
    }

}
