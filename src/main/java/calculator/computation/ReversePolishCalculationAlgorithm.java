package calculator.computation;

import calculator.exceptions.InvalidEquationException;
import calculator.exceptions.InvalidParameterException;
import calculator.inputcontrol.ComponentValidator;

import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;

public class ReversePolishCalculationAlgorithm {

    private ComputationalMachine calculator;
    private ComponentValidator validator;
    private Stack<String> supplier;


    public ReversePolishCalculationAlgorithm(final ComputationalMachine machine,final ComponentValidator validatingLogic)
    {
        calculator=machine;
        validator=validatingLogic;
        supplier=new Stack<>();
    }

    /**
     * Help function which implements the logic of Reversed Polish Notation for calculating an eqiation
     * @param components - array of Strings, consisting items, representing a component of an equation
     * @return result of the equation
     * @throws Exception - error during the reverse polish notation calculation
     */
    public double calculateEquation(final List<String> components) throws EmptyStackException, InvalidEquationException
    {

        //TODO make polymorphism with Enums, maybe
        handleComponents(components);

        if(supplier.size()!=1)
        {
            throw new InvalidEquationException("Invalid equation. Logical error. There aren't enough operators");
        }

        return Double.valueOf(supplier.pop());
    }

    private void handleComponents(final List<String> components) throws InvalidParameterException
    {
        for (String component : components) {
            if (validator.isValidNumber(component))
            {
                supplier.add(component);
            }
            else
            {
                executeOperation(component);
            }
        }
    }

    private void executeOperation(final String operator) throws InvalidParameterException, EmptyStackException
    {
        double leftNumber = Double.valueOf(supplier.pop());
        double rightNumber = Double.valueOf(supplier.pop());
        double result = calculator.computeAction(operator,rightNumber,leftNumber);
        supplier.add(String.valueOf(result));
    }

}
