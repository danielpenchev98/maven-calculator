package calculator.inputcontrol;

import calculator.exceptions.InvalidEquationException;

import java.util.Arrays;
import java.util.List;

public class EquationStructureValidator {

    public  void validateEquationStructure(final List<String> formattedEquation) throws InvalidEquationException
    {
        String equation= Arrays.toString(formattedEquation.toArray()).replaceAll("\\[|\\]|,","");

        String message=null;
        if(equation.equals(""))
        {
            message = "Empty equation";
        }
        else if(!hasBracketBalance(equation))
        {
            message = "The equation is missing brackets";
        }
        else if(hasSequentialTypeOfComponents(equation))
        {
            message = "Sequential operators has been found";
        }
        else if(hasNonOperatorBeforeBracket(equation)||hasNonOperatorAfterBracket(equation))
        {
            message = "There should be a operator between a number and opening bracket or between a closing bracket and a number in that order";
        }
        else if(hasEmptyBrackets(equation))
        {
            message = "There should be at least one number in the brackets";
        }
        else if(hasOperatorAfterOpeningOrBeforeClosingBracket(equation))
        {
            message="There shouldn't be an operator before closing or after opening bracket";
        }

        if(message!=null)
        {
            throw new InvalidEquationException(message);
        }
    }


    private boolean hasSequentialTypeOfComponents(final String equation)
    {
        return equation.matches(".*([^0-9.a-zA-Z() ]+[ ]*[^0-9.a-zA-Z() ]+|[0-9.a-zA-Z]+[ ]+[0-9.a-zA-Z]+).*");
    }

    private boolean hasEmptyBrackets(final String equation)
    {
        return equation.matches(".*[(] [)].*");
    }

    private boolean hasNonOperatorBeforeBracket(final String equation)
    {
        return equation.matches(".*[0-9.a-zA-Z)] [(].*");
    }

    private boolean hasOperatorAfterOpeningOrBeforeClosingBracket(final String equation)
    {
        return equation.matches(".* [^0-9.a-zA-Z)(] [)].*|.*[(] [^0-9.a-zA-Z)(] .*");
    }

    private boolean hasNonOperatorAfterBracket(final String equation)
    {
        return equation.matches(".*[)] [0-9.a-zA-Z(].*");
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
