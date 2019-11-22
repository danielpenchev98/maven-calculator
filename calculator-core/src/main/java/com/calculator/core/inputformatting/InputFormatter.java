package com.calculator.core.inputformatting;

import com.calculator.core.exceptions.InvalidComponentException;
import com.calculator.core.exceptions.InvalidExpressionException;
import com.calculator.core.operators.ExpressionComponent;

import java.util.List;

public interface InputFormatter {
    List<ExpressionComponent> doFormat(final String expression) throws InvalidComponentException, InvalidExpressionException;
}
