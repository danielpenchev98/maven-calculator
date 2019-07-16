package calculator.inputControl.RPParserTriggers;

import java.util.Stack;

/**
 * Interface used for ReversePolishNotationParser
 */
public interface ReversePolishComponentTrigger {
    /**
     * Function which represents an particular activity and used during the iteration of all components of the equation
     * @param container - used specifically for operator-triggers
     * @param equation - used for building the ReversePolishNotation equation
     */
    void trigger(final Stack<ReversePolishComponentTrigger> container,final StringBuilder equation);
}
