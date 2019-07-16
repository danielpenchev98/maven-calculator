package calculator.inputControl.RPParserTriggers;

import java.util.Stack;

public interface ReversePolishComponentTrigger {
    void trigger(final Stack<ReversePolishComponentTrigger> container,final StringBuilder equation);
}
