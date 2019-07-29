package com.calculator.core.inputformatting;

import com.calculator.core.exceptions.InvalidComponentException;
import com.calculator.core.exceptions.InvalidEquationException;
import com.calculator.core.operators.EquationComponent;

import java.util.List;

public interface InputFormatter {
    List<EquationComponent> doFormat(final String equation) throws InvalidComponentException, InvalidEquationException;
}
