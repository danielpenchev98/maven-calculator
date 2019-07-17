package calculator.inputControl;

import calculator.exceptions.*;

/**
 * The main idea is to find the errors in the equation, which cannot be detected during the RPN calculation
 */
public class EquationValidator {

   /* private PrimalParser formatter;

    public EquationValidator()
    {
        this.formatter=new PrimalParser();
    }

    public EquationValidator(final PrimalParser formatter)
    {
        this.formatter=formatter;
    }*/

    public void validateEquation(final String equation) throws InvalidTypeOfEquationComponent, InvalidEquationException
    {

        //String[] equationComponents=formatter.formatInput(equation);
        String[] splitInput=equation.split(" ");

        if(equation.equals(""))
        {
            throw new InvalidEquationException("Empty equation");
        }

        for(String component:splitInput)
        {
            if(!isValidNumber(component)&&!isValidArithmeticOperator(component)&&!isBracket(component))
            {
                throw new InvalidTypeOfEquationComponent("An illegal equation component has been found");
            }
        }


        if(!hasBracketBalance(equation))
        {
            throw new InvalidEquationException("The equation is missing brackets");
        }
        else if(hasSequentialOperatorsOrNumbers(equation))
        {
            throw new InvalidEquationException("Sequential operators has been found");
        }
        else if(hasNonOperatorBeforeBracket(equation)||hasNonOperatorAfterBracket(equation))
        {
            throw new InvalidEquationException("There should be a operator between a number and opening bracket or between a closing bracket and a number in that order");
        }
        else if(hasNonNumbersOnlyBetweenBrackets(equation))
        {
            throw new InvalidEquationException("There should be at least one number in the brackets");
        }
    }


    //^[-+]?[1-9][0-9]*([.][0-9]+)?$ - if numbers beginning with zero are illegal
    public boolean isValidNumber(final String component) {
       return component.matches("^[-+]?[0-9]+([.][0-9]+)?$");
    }

    public boolean isValidArithmeticOperator(final String component) {
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
