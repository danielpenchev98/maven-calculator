package calculator.inputControl;

/**
 * Class, responsible for validating the input
 */
class InputValidator {

    /**
     * Function which determines the type of component
     * @param component equation component
     * @return the type of component
     */
    boolean validateComponent(final String component)
    {
        return isValidNumber(component)||isValidOperator(component);
    }

    /**
     * @param component - equation component
     * @return - if "component" is a number
     */
    boolean isValidNumber(final String component)
    {
        return component.matches("^-*[0-9]+$");
    }

    /**
     * @param component - equation component
     * @return - if "component" is an operator
     */
    boolean isValidOperator(final String component)
    {
        return component.matches("^[-+*/]$");
    }
}
