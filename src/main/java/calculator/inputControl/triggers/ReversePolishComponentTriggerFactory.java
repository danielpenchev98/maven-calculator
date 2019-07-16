package calculator.inputControl.triggers;

import calculator.computation.MathArithmeticOperatorFactory;
import calculator.exceptions.InvalidOperatorException;

public class ReversePolishComponentTriggerFactory {

    public ReversePolishComponentTrigger createTrigger(final String component) throws InvalidOperatorException {

        MathArithmeticOperatorFactory operatorFactory=new MathArithmeticOperatorFactory();

        switch(component)
        {
            case "(": return new OpeningBracketTrigger();
            case ")": return new ClosingBracketTrigger();
            default: return new MathArithmeticOperatorTrigger(operatorFactory.createArithmeticOperation(component));
        }
    }
}
