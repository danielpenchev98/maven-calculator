package calculator.inputControl.RPParserTriggersTest;

import calculator.computation.MathArithmeticOperatorFactory;
import calculator.exceptions.InvalidOperatorException;
import calculator.inputControl.EquationValidator;

public class ReversePolishComponentTriggerFactory {

    private EquationValidator checker;

    private MathArithmeticOperatorFactory operatorFactory;

    public ReversePolishComponentTriggerFactory(EquationValidator validator,MathArithmeticOperatorFactory factory)
    {
        checker=validator;
        operatorFactory=factory;
    }

    public ReversePolishComponentTrigger createTrigger(final String component) throws InvalidOperatorException {

        if(checker.isValidNumber(component))
        {
            return new NumberTrigger(component);
        }
        else
        {
            return createOperatorTrigger(component);
        }

    }

    private ReversePolishComponentTrigger createOperatorTrigger(final String component) throws InvalidOperatorException
    {
        switch(component)
        {
            case "(": return new OpeningBracketTrigger();
            case ")": return new ClosingBracketTrigger();
            default: return new MathArithmeticOperatorTrigger(operatorFactory.createArithmeticOperation(component));
        }
    }
}
