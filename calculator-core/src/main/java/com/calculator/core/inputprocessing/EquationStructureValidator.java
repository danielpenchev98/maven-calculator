package com.calculator.core.inputprocessing;

import com.calculator.core.exceptions.InvalidEquationException;
import com.calculator.core.exceptions.InvalidParameterException;

public class EquationStructureValidator {

    public void validateEquationStructure(final String equation) throws InvalidEquationException
    {
        if(equation==null) {
           throw new InvalidParameterException("validateEquation has received invalid parameter");
        }

        checkIfHasBracketImbalance(equation);
        checkIfEmptyEquation(equation);
        checkIfHasSequentialNonBracketComponentsOfTheSameType(equation);
        checkIfHasAnOperatorAsBeginningOrEndingOfEquationScope(equation);
        checkIfHasEmptyBrackets(equation);
        checkIfHasMissingOperatorBeforeOrAfterBracket(equation);
    }

    private void checkIfHasBracketImbalance(final String equation) throws InvalidEquationException
    {
        int bracketBalance = 0;
        boolean hasError = false;
        for(char symbol:equation.toCharArray())
        {
            if (symbol == '(')
            {
                bracketBalance++;
            }
            else if (symbol == ')')
            {
                if(bracketBalance == 0)
                {
                    hasError = true;
                }
                bracketBalance--;
            }
        }
        if(hasError || bracketBalance != 0)
        {
            throw new InvalidEquationException("Equation with missing or misplaced brackets");
        }
    }

    private void checkIfEmptyEquation(final String equation) throws InvalidEquationException
    {
        if(hasEmptyEquation(equation))
        {
            throw new InvalidEquationException("Empty equation");
        }
    }

    private boolean hasEmptyEquation(final String equation)
    {
        return equation.matches("");
    }

    private void checkIfHasSequentialNonBracketComponentsOfTheSameType(final String equation) throws InvalidEquationException
    {
        if(hasSequentialNonBracketComponentsOfTheSameType(equation))
        {
            throw new InvalidEquationException("Sequential components of the same type");
        }
    }

    private boolean hasSequentialNonBracketComponentsOfTheSameType(final String equation)
    {
        return equation.matches(".*([0-9a-zA-Z.]+ [0-9a-zA-Z.]+|.*[^0-9a-zA-Z.)( ] [^0-9a-zA-Z.)( ]).*");
    }

    private void checkIfHasAnOperatorAsBeginningOrEndingOfEquationScope(final String equation) throws InvalidEquationException
    {
        if(hasAnOperatorAsBeginningOrEndingOfEquationScope(equation))
        {
            throw new InvalidEquationException("Scope of equation ending or beginning with an operator");
        }
    }

    private boolean hasAnOperatorAsBeginningOrEndingOfEquationScope(final String equation)
    {
        return equation.matches("(^|.*\\( )[^0-9a-zA-Z.)( ] .*|.*[^0-9a-zA-Z.)( ]( \\)|$)");
    }

    private void checkIfHasEmptyBrackets(final String equation) throws InvalidEquationException
    {
        if(hasEmptyBrackets(equation))
        {
            throw new InvalidEquationException("Empty brackets");
        }
    }

    private boolean hasEmptyBrackets(final String equation)
    {
        return equation.matches(".*\\( \\).*");
    }

    private void checkIfHasMissingOperatorBeforeOrAfterBracket(final String equation) throws InvalidEquationException
    {
        if(hasMissingOperatorBeforeOrAfterBracket(equation))
        {
            throw new InvalidEquationException("Missing operator between a number and an opening bracket or a closing bracket and a number");
        }
    }

    private boolean hasMissingOperatorBeforeOrAfterBracket(final String equation)
    {
        return equation.matches(".*[0-9a-zA-Z.)] \\(.*|.*\\) [0-9a-zA-Z.(].*");
    }

}
