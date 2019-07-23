package calculator.inputcontrol;

import calculator.exceptions.InvalidEquationException;
import calculator.exceptions.InvalidParameterException;

import java.util.Arrays;
import java.util.List;

public class EquationStructureValidator {

    public void validateEquationStructure(final List<String> formatterEquation) throws InvalidEquationException
    {
        if(formatterEquation==null)
        {
            throw new InvalidParameterException("validateEquationStructure has receive null reference");
        }

        int flag=0;
        for(String component:formatterEquation)
        {
            if(component.matches("[0-9.]*$"))
            {
                if(flag==1)
                {
                    throw new InvalidEquationException("Invalid equation");
                }
                flag=0;
            }
            else if(component.matches("^[-+/*^]$"))
            {
                if(flag==0)
                {
                    throw new InvalidEquationException("Invalid equation");
                }
                flag=1;
            }
        }
    }
    /*
    public  void validateEquationStructure(final List<String> formattedEquation) throws InvalidEquationException
    {
        String message=null;
        if(hasBracketDisbalance(formattedEquation))
        {
            message = "The equation is missing brackets";
        }

        String equation= Arrays.toString(formattedEquation.toArray()).replaceAll("\\[|\\]|,","");

        if(equation.equals(""))
        {
            message = "Empty equation";
        }
        else if(hasSequentialTypeOfComponents(equation))
        {
            message = "Sequential operators has been found";
        }
        else if(hasNonOperatorBeforeOrAfterBracket(equation))
        {
            message = "There should be an operator between a number and an opening bracket or between a closing bracket and a number in that order";
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

    private boolean hasNonOperatorBeforeOrAfterBracket(final String equation)
    {
        return equation.matches(".*[0-9.a-zA-Z)] [(].*|.*[)] [0-9.a-zA-Z(].*");
    }

    private boolean hasOperatorAfterOpeningOrBeforeClosingBracket(final String equation)
    {
        return equation.matches(".* [^0-9.a-zA-Z)(] [)].*|.*[(] [^0-9.a-zA-Z)(] .*");
    }

    private boolean hasBracketDisbalance(final List<String> equation)
    {
        int bracketBalance=0;

        for(String item:equation)
        {
            if(item.equals("("))
            {
                bracketBalance++;
            }
            else if(item.equals(")"))
            {
                if(bracketBalance==0)
                {
                    return true;
                }
                bracketBalance--;
            }
        }
        return bracketBalance!=0;
    }
    */
}
