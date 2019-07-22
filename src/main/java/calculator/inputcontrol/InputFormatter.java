package calculator.inputcontrol;


import calculator.computation.EquationComponent;
import calculator.computation.EquationComponentFactory;
import calculator.exceptions.InvalidComponentException;
import calculator.exceptions.InvalidEquationException;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Class which requires for formatting of the user input and identifying the components of an equation
 */
public class InputFormatter {

    private EquationStructureValidator structureValidator;

    private EquationComponentFactory componentFactory;

    public InputFormatter(final EquationStructureValidator validator, final EquationComponentFactory factory)
    {
        structureValidator=validator;
        componentFactory=factory;
    }

    public List<EquationComponent> doFormat(final String equation) throws InvalidComponentException, InvalidEquationException {

        List<String> components= new LinkedList<>(Arrays.asList(extractComponents(equation)));
        structureValidator.validateEquationStructure(components);
        return convertToEquationComponentObjects(components);
    }

    private String[] extractComponents(final String equation)
    {
        final String componentSeparator=" ";
        String firstStep = addSpaceAfterEveryComponent(equation);
        String secondStep = removeJunkSpaces(firstStep);
        return removeSpaceBetweenTheNumberAndItsSign(secondStep).split(componentSeparator);
    }

    private String removeJunkSpaces(String equation)
    {
        return equation.replaceAll("[ ]+"," ").trim();
    }

    private String addSpaceAfterEveryComponent(String equation)
    {
        return equation.replaceAll("([0-9]+([.][0-9]*)*|[^0-9.a-zA-Z ]|[a-zA-Z]+)","$1 ");
    }

    private String removeSpaceBetweenTheNumberAndItsSign(String equation)
    {
        return equation.replaceAll("([(] |^)- ([0-9]+)","$1-$2");
    }

    private List<EquationComponent> convertToEquationComponentObjects(final List<String> components) throws InvalidComponentException
    {
        List<EquationComponent> formattedComponents=new LinkedList<>();
        for(String component:components)
        {
            formattedComponents.add(componentFactory.createComponent(component));
        }
        return formattedComponents;
    }


}