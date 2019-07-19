package calculator.inputcontrol;

import calculator.exceptions.*;

import java.util.Arrays;
import java.util.List;


/**
 * The main idea is to find the errors in the equation, which cannot be detected during the RPN calculation
 */
public class EquationValidator {


    public void validateEquation(final List<String> formattedEquation) throws InvalidComponentException, InvalidEquationException
    {
        validateEquationStructure(formattedEquation);
        validateComponents(formattedEquation);
    }

    public boolean isValidNumber(final String component) {
        return component.matches("^[-+]?[0-9]+([.][0-9]+)?$");
    }

    public boolean isValidArithmeticOperator(final String component) {
        return component.matches("^[-+*/^]$");
    }


    private void validateComponents(final List<String> components) throws InvalidComponentException
    {
        for(String component:components)
        {
            if(!isValidNumber(component)&&!isValidArithmeticOperator(component)&&!isBracket(component))
            {
                throw new InvalidComponentException("An illegal equation component has been found");
            }
        }
    }

    private void validateEquationStructure(final List<String> formattedEquation) throws InvalidEquationException
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
        else if(hasNonNumbersOnlyBetweenBrackets(equation))
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

    private boolean hasSequentialTypeOfComponents(String equation)
    {
        return equation.matches(".*([-+/*^]+[ ]*[-+/*^]+|[0-9a-zA-Z]+[ ]+[0-9a-zA-Z]+).*");
    }

    private boolean hasNonNumbersOnlyBetweenBrackets(String equation)
    {
        return equation.matches(".*[(][^0-9a-zA-Z]*[)].*");
    }

    private boolean hasNonOperatorBeforeBracket(String equation)
    {
        return equation.matches(".*[0-9a-zA-Z)] [(].*");
    }

    private boolean hasNonOperatorAfterBracket(String equation)
    {
        return equation.matches(".*[)] [0-9a-zA-Z(].*");
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
