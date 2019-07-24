package calculator.inputprocessing;

import calculator.exceptions.InvalidEquationException;
import calculator.exceptions.InvalidParameterException;

import java.util.Arrays;
import java.util.List;

public class EquationStructureValidator {

    public void validateEquationStructure(final List<String> formattedEquation) throws InvalidEquationException
    {
        if(formattedEquation==null) {
           throw new InvalidParameterException("validateEquation has received invalid parameter");
        }
        checkIfHasBracketImbalance(formattedEquation);

        String equation=convertToWholeStringFormat(formattedEquation);

        checkIfEmptyEquation(equation);
        checkIfHasSequentialNonBracketComponentsOfTheSameType(equation);
        checkIfHasAnOperatorAsBeginningOrEndingOfEquationScope(equation);
        checkIfHasEmptyBrackets(equation);
        checkIfHasMissingOperatorBeforeOrAfterBracket(equation);
    }

    private void checkIfHasBracketImbalance(final List<String> components) throws InvalidEquationException
    {
        int bracketBalance = 0;
        boolean hasError = false;
        for(String component:components)
        {
            if(component.equals("("))
            {
                bracketBalance++;
            }
            else if(component.equals(")"))
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

    private String convertToWholeStringFormat(final List<String> formattedEquation)
    {
       return Arrays.toString(formattedEquation.toArray()).replaceAll("\\[|\\]|,","");
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
