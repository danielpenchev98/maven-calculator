package calculator.computation;

import calculator.container.ComponentSupplier;
import calculator.exceptions.InvalidOperatorException;
import calculator.exceptions.MissingOperatorException;
import calculator.exceptions.OutOfItemsException;
import calculator.inputControl.EquationValidator;

import java.util.List;

public class ReversePolishCalculationAlgorithm {

    private ComputationalMachine calculator;
    private EquationValidator validator;
    private ComponentSupplier<String> supplier;

    public ReversePolishCalculationAlgorithm(final ComputationalMachine machine,final EquationValidator validatingLogic,final ComponentSupplier<String> supp)
    {
        calculator=machine;
        validator=validatingLogic;
        supplier=supp;
    }

    /**
     * Help function which implements the logic of Reversed Polish Notation for calculating an eqiation
     * @param splitInput - array of Strings, constisting of items, representing a component of an equation
     * @return result of the equation
     * @throws Exception - error during the reverse polish notation calculation
     */
    public double calculateEquation(final String[] splitInput) throws OutOfItemsException, MissingOperatorException, InvalidOperatorException
    {
        for (String component : splitInput) {
            if (validator.isValidNumber(component))
            {
                supplier.addItem(component);
            }
            else
            {
                executeOperation(component);
            }
        }

        if(supplier.numberOfItemsAvailable()!=1)
        {
            throw new MissingOperatorException("Invalid equation. Logical error. There aren't enough operators");
        }

        return Double.valueOf(supplier.receiveNextItem());
    }

    private void executeOperation(final String operator) throws InvalidOperatorException,OutOfItemsException
    {
        List<String> numbers = supplier.receiveListOfNextItems(2);
        double result = calculator.computeAction(operator, Double.valueOf(numbers.get(0)), Double.valueOf(numbers.get(1)));
        supplier.addItem(String.valueOf(result));
    }

}
