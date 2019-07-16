package calculator.inputControl.RPParserTriggers;

import java.util.Stack;

public class NumberTrigger implements ReversePolishComponentTrigger {

    private String value;

    public NumberTrigger(String component)
    {
        value=component;
    }

    @Override
    public void trigger(Stack<ReversePolishComponentTrigger> container, StringBuilder equation) {
        addNumberToEquation(equation);
    }

    private void addNumberToEquation(final StringBuilder equation)
    {
        equation.append(value).append(" ");
    }

}
