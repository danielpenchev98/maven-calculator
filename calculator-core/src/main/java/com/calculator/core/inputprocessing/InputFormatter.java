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

    private final EquationStructureValidator structureValidator;

    private final EquationComponentFactory componentFactory;

    private final String COMPONENTSEPARATOR = " ";

    public InputFormatter() {
        this(new EquationStructureValidator(), new EquationComponentFactory());
    }

    InputFormatter(final EquationStructureValidator validator, final EquationComponentFactory factory) {
        structureValidator = validator;
        componentFactory = factory;
    }

    public List<EquationComponent> doFormat(final String equation) throws InvalidComponentException, InvalidEquationException {

        String reformattedEquation = fixFormatOfEquation(equation);
        structureValidator.validateEquationStructure(reformattedEquation);
        List<String> components = extractComponents(reformattedEquation);
        return convertToEquationComponentObjects(components);
    }

    private String fixFormatOfEquation(final String equation) {
        String equationWithPaddingBetweenComponents = addSeparatorAfterEveryComponent(equation);
        return removeSeparatorBetweenTheNumberAndItsSign(equationWithPaddingBetweenComponents);
    }

    private List<String> extractComponents(final String equation) {
        String[] components = equation.split(COMPONENTSEPARATOR);
        return Arrays.asList(components);
    }

    private String removeJunkSpaces(final String equation) {
        final String sequentialSpacesRegex="[ ]+";
        return equation.replaceAll(sequentialSpacesRegex, COMPONENTSEPARATOR).trim();
    }

    private String addSeparatorAfterEveryComponent(final String equation) {

        final String componentsRegex="([0-9]+([.][0-9]*)*|[^0-9.a-zA-Z ]|[a-zA-Z]+)";
        final String componentPaddedWithSeparator="$1"+COMPONENTSEPARATOR;

        String equationWithPossiblyJunkSpaces = equation.replaceAll(componentsRegex, componentPaddedWithSeparator);
        return removeJunkSpaces(equationWithPossiblyJunkSpaces);
    }

    private String removeSeparatorBetweenTheNumberAndItsSign(final String equation) {

        final String separatorBetweenNumberAndItsSign="([(]"+COMPONENTSEPARATOR+ "|^)-"+COMPONENTSEPARATOR+"([0-9]+)";
        final String removedSeparatorBetweenNumberAndItsSign="$1-$2";

        return equation.replaceAll(separatorBetweenNumberAndItsSign, removedSeparatorBetweenNumberAndItsSign);
    }

    private List<EquationComponent> convertToEquationComponentObjects(final List<String> components) throws InvalidComponentException {
        List<EquationComponent> formattedComponents = new LinkedList<>();
        for (String component : components) {
            formattedComponents.add(componentFactory.createComponent(component));
        }
        return formattedComponents;
    }


}