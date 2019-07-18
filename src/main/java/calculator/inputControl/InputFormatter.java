package calculator.inputControl;

import calculator.exceptions.InvalidTypeOfEquationComponent;

/**
 * Class which requires for formatting of the user input and identifying the components of an equation
 */
public class InputFormatter {

    private final String COMPONENTSEPARATOR=" ";
    /**
     * @param equation - unformatted input
     * @return formatted input
     * @throws InvalidTypeOfEquationComponent - if there exists and illegal component in the equation
     */
    public String doFormat(final String equation) {
        String firstStep = addSpaceAfterEveryComponent(equation);
        String secondStep = removeJunkSpaces(firstStep);
        return removeSpaceBetweenTheNumberAndItsSign(secondStep);
    }

    public String getComponentSeparator()
    {
        return COMPONENTSEPARATOR;
    }

    private String removeJunkSpaces(String equation)
    {
        return equation.replaceAll("[ ]+"," ").replaceAll("^ | $","");
    }

    private String addSpaceAfterEveryComponent(String equation)
    {
        return equation.replaceAll("([0-9]+([.][0-9]*)?|[^0-9 ])","$1 ");
    }

    private String removeSpaceBetweenTheNumberAndItsSign(String equation)
    {
        return equation.replaceAll("([(] |^)- ([0-9]+)","$1-$2");
    }

}