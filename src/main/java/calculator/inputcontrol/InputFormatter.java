package calculator.inputcontrol;


import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Class which requires for formatting of the user input and identifying the components of an equation
 */
public class InputFormatter {

    /**
     * @param equation - unformatted input
     * @return formatted input
     */
    public List<String> doFormat(final String equation) {
        final String componentSeparator=" ";
        String firstStep = addSpaceAfterEveryComponent(equation);
        String secondStep = removeJunkSpaces(firstStep);
        String[] components = removeSpaceBetweenTheNumberAndItsSign(secondStep).split(componentSeparator);
        return new LinkedList<>(Arrays.asList(components));
    }


    private String removeJunkSpaces(String equation)
    {
        return equation.replaceAll("[ ]+"," ").trim();
    }

    private String addSpaceAfterEveryComponent(String equation)
    {
        return equation.replaceAll("([0-9]+([.][0-9]*)?|[^0-9a-zA-Z ]|[a-zA-Z]+)","$1 ");
    }

    private String removeSpaceBetweenTheNumberAndItsSign(String equation)
    {
        return equation.replaceAll("([(] |^)- ([0-9]+)","$1-$2");
    }

}