package calculator.inputControl;

class InputValidator {
    public boolean validateComponent(final String component)
    {
        return isValidNumber(component)||isValidOperator(component);
    }

    public boolean isValidNumber(final String component)
    {
        return component.matches("^[0-9]+$");
    }
    public boolean isValidOperator(final String component)
    {
        return component.matches("^[-+*/]$");
    }
}
