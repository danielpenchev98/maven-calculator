package calculator.computation;

import calculator.exceptions.InvalidComponentException;
import calculator.exceptions.InvalidEquationException;

import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;

public class ReversePolishCalculationAlgorithm {

    private Stack<EquationComponent> supplier;

    public ReversePolishCalculationAlgorithm()
    {
        supplier=new Stack<>();
    }

    /**
     * Help function which implements the logic of Reversed Polish Notation for calculating an eqiation
     * @param components - array of Strings, consisting items, representing a component of an equation
     * @return result of the equation
     * @throws Exception - error during the reverse polish notation calculation
     */
    public double calculateEquation(final List<EquationComponent> components) throws EmptyStackException, InvalidEquationException,InvalidComponentException
    {

        handleComponents(components);

        if(supplier.size()!=1)
        {
            throw new InvalidEquationException("Invalid equation. Logical error. There aren't enough operators");
        }

        return Double.valueOf(((NumberComponent)supplier.pop()).getValue());
    }

    private void handleComponents(final List<EquationComponent> components) throws InvalidComponentException
    {
        for (EquationComponent component : components) {
            if (component instanceof NumberComponent)
            {
                supplier.add(component);
            }
            else if (component instanceof MathArithmeticOperator)
            {
                executeOperation(component);
            }
            else
            {
                throw new InvalidComponentException("Invalid component of RPN found during execution of the algorithm");
            }
        }
    }

    //TODO i dont need computational machine - i can use the operator method directly

    private void executeOperation(final EquationComponent operator) throws EmptyStackException
    {
        double leftNumber = Double.valueOf(((NumberComponent)supplier.pop()).getValue());
        double rightNumber = Double.valueOf(((NumberComponent)supplier.pop()).getValue());
        double result = ((MathArithmeticOperator)operator).compute(leftNumber,rightNumber);
        supplier.add(new NumberComponent(String.valueOf(result)));
    }
}