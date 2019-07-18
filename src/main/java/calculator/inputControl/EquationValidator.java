package calculator.inputControl;

import calculator.exceptions.*;


/**
 * The main idea is to find the errors in the equation, which cannot be detected during the RPN calculation
 */
public class EquationValidator {


    public void validateEquation(final String formattedEquation,final String separator) throws InvalidTypeOfEquationComponent, InvalidEquationException
    {
        validateEquationStructure(formattedEquation);
        validateComponents(formattedEquation,separator);
    }

    //If if-else in RPAlgorithm is replaced with polymorphism then these two functions will be private
    public boolean isValidNumber(final String component) {
        return component.matches("^[-+]?[0-9]+([.][0-9]+)?$");
    }

    public boolean isValidArithmeticOperator(final String component) {
        return component.matches("^[-+*/^]$");
    }


    private void validateComponents(final String formattedEquation,final String separator) throws InvalidTypeOfEquationComponent
    {
        String[] components=formattedEquation.split(separator);
        for(String component:components)
        {
            if(!isValidNumber(component)&&!isValidArithmeticOperator(component)&&!isBracket(component))
            {
                throw new InvalidTypeOfEquationComponent("An illegal equation component has been found");
            }
        }
    }

    private void validateEquationStructure(final String formattedEquation) throws InvalidEquationException
    {
        String message=null;
        if(formattedEquation.equals(""))
        {
            message = "Empty equation";
        }
        else if(!hasBracketBalance(formattedEquation))
        {
            message = "The equation is missing brackets";
        }
        else if(hasSequentialOperatorsOrNumbers(formattedEquation))
        {
            message = "Sequential operators has been found";
        }
        else if(hasNonOperatorBeforeBracket(formattedEquation)||hasNonOperatorAfterBracket(formattedEquation))
        {
            message = "There should be a operator between a number and opening bracket or between a closing bracket and a number in that order";
        }
        else if(hasNonNumbersOnlyBetweenBrackets(formattedEquation))
        {
            message = "There should be at least one number in the brackets";
        }

        if(message!=null)
        {
            throw new InvalidEquationException(message);
        }
    }

    //^[-+]?[1-9][0-9]*([.][0-9]+)?$ - if numbers beginning with zero are illegal
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
