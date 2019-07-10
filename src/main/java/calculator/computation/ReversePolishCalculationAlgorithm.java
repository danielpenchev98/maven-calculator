package calculator.computation;

import calculator.container.ComponentSupplier;
import calculator.inputControl.EquationValidator;

import java.util.List;

public class ReversePolishCalculationAlgorithm {

    /**
     * Used as an abstraction - increases the modifiability - if the algorithm for validation of the input changes, then InoutParser wont notice
     */
    private ComputationalMachine calculator;

    private EquationValidator validator;

    //TODO do i need to pass Equation validator?????
    public ReversePolishCalculationAlgorithm(final ComputationalMachine machine,final EquationValidator validatingLogic)
    {
        calculator=machine;
        validator=validatingLogic;
    }

    /**
     * Help function which implements the logic of Reversed Polish Notation for calculating an eqiation
     * @param splitInput - array of Strings, constisting of items, representing a component of an equation
     * @return result of the equation
     * @throws Exception - error during the reverse polish notation calculation
     */
    public double calculateEquation(final String[] splitInput) throws Exception
    {
        ComponentSupplier supplier=new ComponentSupplier();
        for (String component : splitInput) {
            if (validator.isValidNumber(component))
            {
                supplier.addItem(component);
            }
            else
            {
                List<String> numbers = supplier.receiveListOfNextItems(2);
                double result = calculator.computeAction(component, Double.valueOf(numbers.get(0)), Double.valueOf(numbers.get(1)));
                supplier.addItem(String.valueOf(result));
            }
        }
        if(supplier.numberOfItemsAvailable()!=1)
        {
            System.out.println("Fuck");
            throw new Exception("Invalid equation. Logical error. There aren't enough operators");
        }

        return Double.valueOf(supplier.receiveNextItem());
    }

}
