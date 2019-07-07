package calculator.inputControl;


public class InputValidator {

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

}
