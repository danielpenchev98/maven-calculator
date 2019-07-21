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
    private ComponentValidator componentValidator;


    public InputFormatter()
    {
        structureValidator=new EquationStructureValidator();
        componentValidator=new ComponentValidator();
    }

    public InputFormatter(final EquationStructureValidator structureValidator,final ComponentValidator componentValidator)
    {
        this.structureValidator=structureValidator;
        this.componentValidator=componentValidator;
    }
    /**
     * @param equation - unformatted input
     * @return formatted input
     */
    public List<EquationComponent> doFormat(final String equation) throws InvalidComponentException, InvalidEquationException {

        List<String> stringComponents= new LinkedList<>(Arrays.asList(getComponentsAsStrings(equation)));

        validateInput(stringComponents);

        return covertToEquationComponents(stringComponents);
    }

    private String[] getComponentsAsStrings(final String equation)
    {
        final String componentSeparator=" ";
        String firstStep = addSpaceAfterEveryComponent(equation);
        String secondStep = removeJunkSpaces(firstStep);
        return removeSpaceBetweenTheNumberAndItsSign(secondStep).split(componentSeparator);
    }

    private void validateInput(final List<String> unformattedComponents) throws InvalidEquationException,InvalidComponentException
    {
        //to Replace componentValidator
        componentValidator.validateComponents(unformattedComponents);

        structureValidator.validateEquationStructure(unformattedComponents);
    }

    private List<EquationComponent> covertToEquationComponents(final List<String> stringComponents) throws InvalidComponentException
    {
        List<EquationComponent> components=new LinkedList<>();
        EquationComponentFactory factory=new EquationComponentFactory();
        for(String component:stringComponents)
        {
            components.add(factory.createComponent(component));
        }
        return components;
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