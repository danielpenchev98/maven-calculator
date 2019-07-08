package calculator.inputControl;


import calculator.computation.MathComponentType;

public class EquationValidator {

    public boolean isValidEquation(final String equation)
    {
        String[] equationComponents=equation.split(" ");
        for(String component:equationComponents)
        {
            if(!isValidComponent(component))
            {
                return false;
            }
        }

        if(!hasBracketBalance(equation))
        {
            return false;
        }

        return !(hasNonOperatorBeforeBracket(equation)||hasNonOperatorAfterBracket(equation)||hasNonNumbersOnlyBetweenBrackets(equation)||hasSequentialOperators(equation));
    }
    /**
     * @param component equation component
     * @return the validity of the component
     */
    private boolean isValidComponent(final String component)
    {
        return isValidNumber(component)||isValidOperator(component)||isBracket(component);
    }

    /**
     * @param component - equation component
     * @return - if "component" is a number
     */
    private boolean isValidNumber(final String component) {
        return component.matches("^[-+]?[0-9]+$");
    }

    /**
     * @param component - equation component
     * @return - if "component" is an operator
     */
    private boolean isValidOperator(final String component) {
        return component.matches("^[-+*/]$");
    }

    private boolean isBracket(final String component) { return component.matches("^[)(]$");}

    public boolean hasBracketBalance(String input)
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

    public boolean hasSequentialOperators(String equation)
    {
        return equation.matches(".*([-+/*]+[ ]*[-+/*]+|[0-9]+[ ]+[0-9]+).*");
    }
    public boolean hasNonNumbersOnlyBetweenBrackets(String equation)
    {
        return equation.matches(".*[(][^0-9]*[)].*");
    }
    public boolean hasNonOperatorBeforeBracket(String equation)
    {
        return equation.matches(".*[^-+/*)(] [(].*");
    }
    public boolean hasNonOperatorAfterBracket(String equation)
    {
        return equation.matches(".*[)] [^-+/*)(].*");
    }

    /**
     * @param component - component of the equation
     * @return the type of the component
     */
    public MathComponentType getTypeOfComponent(final String component) throws InvalidTypeOfEquationComponent
    {
        if(isValidNumber(component))
        {
            return MathComponentType.NUMBER;
        }
        else if(isValidOperator(component))
        {
            return MathComponentType.OPERATOR;
        }
        throw new InvalidTypeOfEquationComponent("Unsupported component has been found");
    }

}
