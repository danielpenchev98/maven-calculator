package calculator.inputControl;


public class EquationValidator {

    /**
     * @param component equation component
     * @return the validity of the component
     */
    public boolean validateComponent(final String component)
    {
        return isValidNumber(component)||isValidOperator(component);
    }

    /**
     * @param component - equation component
     * @return - if "component" is a number
     */
    public boolean isValidNumber(final String component) {
        return component.matches("^[-+]?[0-9]+$");
    }

    /**
     * @param component - equation component
     * @return - if "component" is an operator
     */
    public boolean isValidOperator(final String component) {
        return component.matches("^[-+*/]$");
    }

    public boolean checkBrackets(String input)
    {
        input.replaceAll("[^)(]","");
        int checkFlag=0;
        for(char item:input.toCharArray())
        {
            if(item=='(')
            {
                checkFlag++;
            }
            else if(checkFlag==0)
            {
                return false;
            }
            else
            {
                checkFlag--;
            }
        }
        if(checkFlag!=0)
        {
            return false;
        }
        return true;
    }

    public boolean checkForSequentialOperators(String equation)
    {
        return equation.matches(".*([-+/*]+[ ]*[-+/*]+|[0-9]+[ ]+[0-9]).*");
    }
    public boolean checkBetweenBrackets(String equation)
    {
        return equation.matches("[(].*[0-9].*[)]");
    }
    public boolean checkBeforeBracket(String equation)
    {
        return equation.matches("[^-+/*] [(]");
    }
    public boolean checkAfterBracket(String equation)
    {
        return equation.matches("[)] [^-+/*]");
    }

}
