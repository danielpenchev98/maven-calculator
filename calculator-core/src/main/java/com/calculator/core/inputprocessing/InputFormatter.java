package com.calculator.core.inputprocessing;


import com.calculator.core.computation.EquationComponent;
import com.calculator.core.computation.EquationComponentFactory;
import com.calculator.core.exceptions.InvalidComponentException;
import com.calculator.core.exceptions.InvalidEquationException;

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

        List<String> components= extractComponents(equation);
        structureValidator.validateEquationStructure(components);
        return convertToEquationComponentObjects(components);
    }

    private List<String> extractComponents(final String equation)
    {
        if(equation.equals(""))
        {
            return new LinkedList<>();
        }
        final String componentSeparator=" ";
        String equationWithPaddingBetweenComponents = addSpaceAfterEveryComponent(equation);
        String[] components = removeSpaceBetweenTheNumberAndItsSign(equationWithPaddingBetweenComponents).split(componentSeparator);
        return Arrays.asList(components);
    }

    private String removeJunkSpaces(final String equation)
    {
        return equation.replaceAll("[ ]+"," ").trim();
    }

    private String addSpaceAfterEveryComponent(final String equation)
    {
        String equationWithPossiblyJunkSpaces = equation.replaceAll("([0-9]+([.][0-9]*)*|[^0-9.a-zA-Z ]|[a-zA-Z]+)","$1 ");
        return removeJunkSpaces(equationWithPossiblyJunkSpaces);
    }

    private String removeSpaceBetweenTheNumberAndItsSign(final String equation)
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