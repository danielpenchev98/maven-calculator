package calculator.inputcontrol;

import calculator.exceptions.InvalidComponentException;

import java.util.List;

public class ComponentValidator {

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

    //TODO should determine whether the Factory method will check if its a valid operator - supported, or to have
    // the method isValidArithmetic Operator, because if i have then, every time i add new operators, i should change the regex
    public boolean isValidArithmeticOperator(final String component)
    {
        return component.matches("^[-+/*^]$");
    }

    private boolean isBracket(final String component) {
        return component.matches("^[)(]$");
    }

}
