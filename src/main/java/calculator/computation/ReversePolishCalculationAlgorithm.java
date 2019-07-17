package calculator.computation;

import calculator.exceptions.InvalidOperatorException;
import calculator.exceptions.MissingOperatorException;
import calculator.inputControl.EquationValidator;

import java.util.EmptyStackException;
import java.util.Stack;

public class ReversePolishCalculationAlgorithm {

    private ComputationalMachine calculator;
    private EquationValidator validator;
    private Stack<String> supplier;


    public ReversePolishCalculationAlgorithm(final ComputationalMachine machine,final EquationValidator validatingLogic)
    {
        calculator=machine;
        validator=validatingLogic;
        supplier=new Stack<>();
    }

    /**
     * Help function which implements the logic of Reversed Polish Notation for calculating an eqiation
     * @param formattedEquation - array of Strings, constisting of items, representing a component of an equation
     * @return result of the equation
     * @throws Exception - error during the reverse polish notation calculation
     */
    public double calculateEquation(final String formattedEquation) throws EmptyStackException, MissingOperatorException, InvalidOperatorException
    {
        String[] components=formattedEquation.split(" ");

        //TODO make polymorphism with Enums, maybe
        handleComponents(components);

        if(supplier.size()!=1)
        {
            throw new MissingOperatorException("Invalid equation. Logical error. There aren't enough operators");
        }

        return Double.valueOf(supplier.pop());
    }

    private void handleComponents(final String[] components) throws InvalidOperatorException
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

    private void executeOperation(final String operator) throws InvalidOperatorException, EmptyStackException
    {
        double leftNumber = Double.valueOf(supplier.pop());
        double rightNumber = Double.valueOf(supplier.pop());
        double result = calculator.computeAction(operator,rightNumber,leftNumber);
        supplier.add(String.valueOf(result));
    }

}
