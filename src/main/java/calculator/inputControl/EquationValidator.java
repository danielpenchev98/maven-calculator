package calculator.inputControl;

import calculator.exceptions.*;

/**
 * The main idea is to find the errors in the equation, which cannot be detected during the RPN calculation
 */
public class EquationValidator {

    public void validateEquation(final String equation) throws EmptyEquationException, InvalidTypeOfEquationComponent, MissingBracketException, OperatorMisplacementException, MissingNumberException
    {
        String[] equationComponents=equation.split(" ");
        if(equationComponents.length<=1&&equation.equals(""))
        {
            throw new EmptyEquationException("Empty equation");
        }

        for(String component:equationComponents)
        {
            if(!isValidNumber(component)&&!isValidOperator(component)&&!isBracket(component))
            {
                System.out.println(component);
               throw new InvalidTypeOfEquationComponent("An illegal equation component has been found");
            }
        }
        if(!hasBracketBalance(equation))
        {
            throw new MissingBracketException("The equation is missing brackets");
        }
        else if(hasSequentialOperatorsOrNumbers(equation))
        {
            throw new OperatorMisplacementException("Sequential operators has been found");
        }
        else if(hasNonOperatorBeforeBracket(equation)||hasNonOperatorAfterBracket(equation))
        {
            throw new OperatorMisplacementException("There should be a operator between a number and opening bracket or between a closing bracket and a number in that order");
        }
        else if(hasNonNumbersOnlyBetweenBrackets(equation))
        {
            throw new MissingNumberException("There should be at least one number in the brackets");
        }
    }

    public boolean isValidNumber(final String component) {
        return component.matches("^[-+]?[0-9]+$");
    }

    public boolean isValidOperator(final String component) {
        return component.matches("^[-+*/^]$");
    }

    private boolean isBracket(final String component) {
        return component.matches("^[)(]$");
    }

    private boolean hasSequentialOperatorsOrNumbers(String equation)
    {
        return equation.matches(".*([-+/*^]+[ ]*[-+/*^]+|[0-9]+[ ]+[0-9]+).*");
    }

    private boolean hasNonNumbersOnlyBetweenBrackets(String equation)
    {
        return equation.matches(".*[(][^0-9]*[)].*");
    }

    private boolean hasNonOperatorBeforeBracket(String equation)
    {
        return equation.matches(".*[^-+/*(^] [(].*");
    }

    private boolean hasNonOperatorAfterBracket(String equation)
    {
        return equation.matches(".*[)] [^-+/*)^].*");
    }

    private boolean hasBracketBalance(String input)
    {
        input.replaceAll("[^)(]","");

        int bracketBalance=0;

        for(char item:input.toCharArray())
        {
            if(item=='(')
            {
                bracketBalance++;
            }
            else if(item==')')
            {
                if(bracketBalance==0)
                {
                    return false;
                }
                bracketBalance--;
            }
        }
        return bracketBalance==0;
    }

}
