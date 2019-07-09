package calculator.inputControl;

/**
 * Class which requires for formatting of the user input and identifying the components of an equation
 */
public class PrimalParser {

    /**
     * @param equation - unformatted input
     * @return formatted input
     * @throws InvalidTypeOfEquationComponent - if there exists and illegal component in the equation
     */
    public String formatInput(final String equation) {
        String firstStep=addSpaceAfterEveryComponent(equation);
        String secondStep=removeJunkSpaces(firstStep);
        return removeSpaceBetweenTheNumberAndItsSign(secondStep);
    }

    private String removeJunkSpaces(String equation)
    {
        return equation.replaceAll("[ ]+"," ").replaceAll("^ | $","");
    }

    private String addSpaceAfterEveryComponent(String equation)
    {
        return equation.replaceAll("([0-9]+|[^0-9 ])","$1 ");
    }

    private String removeSpaceBetweenTheNumberAndItsSign(String equation)
    {
        return equation.replaceAll("([(] |^)- ([0-9]+)","$1-$2");
    }

}