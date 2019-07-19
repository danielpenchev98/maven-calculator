package calculator.inputcontrol;

import calculator.exceptions.InvalidComponentException;

import java.util.List;

public class EquationComponentValidator {

    public void validateComponents(final List<String> components) throws InvalidComponentException
    {
        for(String component:components)
        {
            if(!isValidNumber(component)&&!isValidArithmeticOperator(component)&&!isBracket(component))
            {
                throw new InvalidComponentException("An illegal equation component has been found");
            }
        }
    }

    public boolean isValidNumber(final String component) {
        return component.matches("^[-+]?[0-9]+([.][0-9]+)?$");
    }

    public boolean isValidArithmeticOperator(final String component) {
        return component.matches("^[-+*/^]$");
    }

    private boolean isBracket(final String component) {
        return component.matches("^[)(]$");
    }

}
