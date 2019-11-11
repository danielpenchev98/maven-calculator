package com.calculator.core.inputformatting;

import com.calculator.core.exceptions.InvalidEquationException;
import com.calculator.core.exceptions.InvalidParameterException;

class EquationStructureValidator {

    void validateEquationStructure(final String equation) throws InvalidEquationException {
        if (equation == null) {
            throw new InvalidParameterException("validateEquation has received invalid parameter");
        }

        checkEmptyEquation(equation);
        checkBracketImbalance(equation);
        checkSequentialNonBracketComponentsOfTheSameType(equation);
        checkOperatorAsBeginningOrEndingOfEquationScope(equation);
        checkEmptyBrackets(equation);
        checkMissingOperatorBeforeOrAfterBracket(equation);
    }

    private void checkBracketImbalance(final String equation) throws InvalidEquationException {
        int bracketBalance = 0;
        boolean hasError = false;
        for (char symbol : equation.toCharArray()) {
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
            throw new InvalidEquationException("Missing or misplaced brackets");
        }
    }

    private void checkEmptyEquation(final String equation) throws InvalidEquationException {
        if (hasEmptyEquation(equation)) {
            throw new InvalidEquationException("Empty equation");
        }
    }

    private boolean hasEmptyEquation(final String equation) {
        return equation.trim().isEmpty();
    }

    private void checkSequentialNonBracketComponentsOfTheSameType(final String equation) throws InvalidEquationException {
        if (hasSequentialNonBracketComponentsOfTheSameType(equation)) {
            throw new InvalidEquationException("Sequential components of the same type");
        }
    }

    private boolean hasSequentialNonBracketComponentsOfTheSameType(final String equation) {
        return equation.matches(".*([0-9a-zA-Z.] +[0-9a-zA-Z.]+|.*[^0-9a-zA-Z.)( ] *[^0-9a-zA-Z.)( ]).*");
    }

    private void checkOperatorAsBeginningOrEndingOfEquationScope(final String equation) throws InvalidEquationException {
        if (hasAnOperatorAsBeginningOrEndingOfEquationScope(equation)) {
            throw new InvalidEquationException("Scope of equation ending or beginning with an operator");
        }
    }

    private boolean hasAnOperatorAsBeginningOrEndingOfEquationScope(final String equation) {
        return equation.matches("(^|.*\\() *([^-0-9a-zA-Z.)( ]|- *[^0-9a-zA-Z. ]).*|.*[^0-9a-zA-Z.)( ] *(\\)|$)");
    }

    private void checkEmptyBrackets(final String equation) throws InvalidEquationException {
        if (hasEmptyBrackets(equation)) {
            throw new InvalidEquationException("Empty brackets");
        }
    }

    private boolean hasEmptyBrackets(final String equation) {
        return equation.matches(".*\\( *\\).*");
    }

    private void checkMissingOperatorBeforeOrAfterBracket(final String equation) throws InvalidEquationException {
        if (hasMissingOperatorBeforeOrAfterBracket(equation)) {
            throw new InvalidEquationException("Missing operator between a number and an opening bracket or a closing bracket and a number");
        }
    }

    private boolean hasMissingOperatorBeforeOrAfterBracket(final String equation) {
        return equation.matches(".*[0-9a-zA-Z.)] *\\(.*|.*\\) *[0-9a-zA-Z.(].*");
    }

}
