package com.calculator.core.inputformatting;

import com.calculator.core.operators.ExpressionComponent;
import com.calculator.core.operators.ExpressionComponentProvider;
import com.calculator.core.exceptions.InvalidComponentException;
import com.calculator.core.exceptions.InvalidExpressionException;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Class which requires for formatting of the user input and identifying the components of an expression
 */
public class ExpressionFormatter implements InputFormatter {

    private final ExpressionStructureValidator structureValidator;

    private final ExpressionComponentProvider componentProvider;

    private final static String SEPARATOR_OF_COMPONENTS = " ";

    public ExpressionFormatter() {
        this(new ExpressionStructureValidator(), new ExpressionComponentProvider());
    }

    ExpressionFormatter(final ExpressionStructureValidator validator, final ExpressionComponentProvider factory) {
        structureValidator = validator;
        componentProvider = factory;
    }

    public List<ExpressionComponent> doFormat(final String expression) throws InvalidComponentException, InvalidExpressionException {
        structureValidator.validateExpressionStructure(expression);
        List<String> components = extractComponents(expression);
        return convertToExpressionComponentObjects(components);
    }

    private List<String> extractComponents(final String expression) {
        String reformattedExpression = fixFormatOfExpression(expression);
        String[] components = reformattedExpression.split(SEPARATOR_OF_COMPONENTS);
        return Arrays.asList(components);
    }

    private String fixFormatOfExpression(final String expression) {
        String expressionWithPaddingBetweenComponents = addSeparatorBetweenComponents(expression);
        return removeSeparatorBetweenTheNumberAndItsSign(expressionWithPaddingBetweenComponents);
    }

    private String addSeparatorBetweenComponents(final String expression) {
        final String componentsRegex="([^0-9.a-zA-Z ]|[0-9.a-zA-Z]+)";
        final String componentsPaddedWithSeparator="$1"+SEPARATOR_OF_COMPONENTS;
        String expressionWithPossiblyJunkSpaces = expression.replaceAll(componentsRegex, componentsPaddedWithSeparator);
        return removeJunkSpaces(expressionWithPossiblyJunkSpaces);
    }

    private String removeJunkSpaces(final String expression) {
        final String junkSpacesRegex=" +";
        return expression.replaceAll(junkSpacesRegex, " ");
    }

    private String removeSeparatorBetweenTheNumberAndItsSign(final String expression) {
        final String separatorBetweenNumberAndItsSign="(\\("+SEPARATOR_OF_COMPONENTS+ "|^)-"+SEPARATOR_OF_COMPONENTS+"([0-9]+)";
        final String removedSeparatorBetweenNumberAndItsSign="$1-$2";
        return expression.replaceAll(separatorBetweenNumberAndItsSign, removedSeparatorBetweenNumberAndItsSign);
    }

    private List<ExpressionComponent> convertToExpressionComponentObjects(final List<String> components) throws InvalidComponentException {
        List<ExpressionComponent> formattedComponents = new LinkedList<>();
        for (String component : components) {
            formattedComponents.add(componentProvider.getComponent(component));
        }
        return formattedComponents;
    }


}
