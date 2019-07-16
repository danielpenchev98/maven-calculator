package calculator.inputControl.RPParserTriggers;

import java.util.Stack;

public class OpeningBracketTrigger implements ReversePolishComponentTrigger {

    @Override
    public void trigger(final Stack<ReversePolishComponentTrigger> container, final StringBuilder equation) {
        container.add(this);
    }
}
