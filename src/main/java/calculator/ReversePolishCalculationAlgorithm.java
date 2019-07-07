package calculator;

import calculator.computation.ComputationalMachine;
import calculator.computation.MathComponentType;
import calculator.container.NumberSupplier;
import calculator.inputControl.InputParser;

import java.util.List;

public class ReversePolishCalculationAlgorithm {

    private static volatile ReversePolishCalculationAlgorithm uniqueInstance;

    /**
     * Used as an abstraction - increases the modifiability - if the algorithm for validation of the input changes, then InoutParser wont notice
     */
    private ComputationalMachine calculator;

    private InputParser parser;

    private ReversePolishCalculationAlgorithm(final ComputationalMachine machine,final InputParser inputModificator)
    {
        calculator=machine;
        parser=inputModificator;
    }

    /**
     * Part of the Singleton pattern
     * @return unique instance of InputParser class
     */
    public static ReversePolishCalculationAlgorithm getInstance(ComputationalMachine machine,InputParser parser)
    {
        if(uniqueInstance==null)
        {
            synchronized (ReversePolishCalculationAlgorithm.class)
            {
                if(uniqueInstance==null)
                {
                    uniqueInstance=new ReversePolishCalculationAlgorithm(machine,parser);
                }

            }
        }
        return uniqueInstance;
    }
    /**
     * Help function which implements the logic of Reversed Polish Notation for calculating an eqiation
     * @param splitInput - array of Strings, constisting of items, representing a component of an equation
     * @return result of the equation
     * @throws Exception - error during the reverse polish notation calculation
     */
    public int calculateEquation(final /*List<EquationComponent>*/String[] splitInput) throws Exception
    {
        NumberSupplier<String> supplier=new NumberSupplier<>();
        for (/*EquationComponent*/String component : splitInput) {
            MathComponentType type = parser.getTypeOfComponent(component);
            if (type == MathComponentType.NUMBER)
            {
                supplier.addItem(component);
            }
            else
            {
                List<String> numbers = supplier.receiveListOfItems(2);
                int result = calculator.computeAction(component, Integer.valueOf(numbers.get(0)), Integer.valueOf(numbers.get(1)));
                supplier.addItem(String.valueOf(result));
            }
           //component.modifyContainer(supplier);
        }
        if(supplier.numberOfItemsAvailable()!=1)
        {
            throw new Exception("Invalid equation. Logical error. There arent enough operators");
        }

        return Integer.valueOf(supplier.receiveListOfItems(1).get(0));
    }

}
