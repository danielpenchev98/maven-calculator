package com.calculator.core.inputformatting;

import com.calculator.core.operators.EquationComponent;
import com.calculator.core.operators.EquationComponentProvider;
import com.calculator.core.exceptions.InvalidComponentException;
import com.calculator.core.exceptions.InvalidEquationException;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Class which requires for formatting of the user input and identifying the components of an equation
 */
public class EquationFormatter implements InputFormatter {

    private final EquationStructureValidator structureValidator;

    private final EquationComponentProvider componentProvider;

    private final static String SEPARATOR_OF_COMPONENTS = " ";

    public EquationFormatter() {
        this(new EquationStructureValidator(), new EquationComponentProvider());
    }

    EquationFormatter(final EquationStructureValidator validator, final EquationComponentProvider factory) {
        structureValidator = validator;
        componentProvider = factory;
    }

    public List<EquationComponent> doFormat(final String equation) throws InvalidComponentException, InvalidEquationException {

        structureValidator.validateEquationStructure(equation);
        List<String> components = extractComponents(equation);
        return convertToEquationComponentObjects(components);
    }

    private String fixFormatOfEquation(final String equation) {
        String equationWithPaddingBetweenComponents = addSeparatorBetweenComponents(equation);
        return removeSeparatorBetweenTheNumberAndItsSign(equationWithPaddingBetweenComponents);
    }

    private List<String> extractComponents(final String equation) {
        String reformattedEquation = fixFormatOfEquation(equation);
        String[] components = reformattedEquation.split(SEPARATOR_OF_COMPONENTS);
        return Arrays.asList(components);
    }

    private String removeJunkSpaces(final String equation) {
        final String junkSpacesRegex="[ ]+";
        return equation.replaceAll(junkSpacesRegex, " ");
    }

    private String addSeparatorBetweenComponents(final String equation) {

        final String componentsRegex="([^0-9.a-zA-Z ]|[0-9.a-zA-Z]+)";
        final String componentsPaddedWithSeparator="$1"+SEPARATOR_OF_COMPONENTS;

        String equationWithPossiblyJunkSpaces = equation.replaceAll(componentsRegex, componentsPaddedWithSeparator);

        return removeJunkSpaces(equationWithPossiblyJunkSpaces);

    }

    private String removeSeparatorBetweenTheNumberAndItsSign(final String equation) {

        final String separatorBetweenNumberAndItsSign="(\\("+SEPARATOR_OF_COMPONENTS+ "|^)-"+SEPARATOR_OF_COMPONENTS+"([0-9]+)";
        final String removedSeparatorBetweenNumberAndItsSign="$1-$2";

        return equation.replaceAll(separatorBetweenNumberAndItsSign, removedSeparatorBetweenNumberAndItsSign);
    }

    private List<EquationComponent> convertToEquationComponentObjects(final List<String> components) throws InvalidComponentException {
        List<EquationComponent> formattedComponents = new LinkedList<>();
        for (String component : components) {
            formattedComponents.add(componentProvider.getComponent(component));
        }
        return formattedComponents;
    }


}
