package calculator.inputControl;

import calculator.computation.ComputationalMachine;
import calculator.computation.MathComponentType;


//TO DO - add algorithm for converting normal notation to reversed polish notation

/**
 * Class which requires for formatting of the user input and identifying the components of an equation
 */
public class InputParser {
    private static volatile InputParser uniqueInstance;

    /**
     * Used as an abstraction - increases the modifiability - if the algorithm for validation of the input changes, then InoutParser wont notice
     */
    private InputValidator checkMechanism;

    private InputParser()
    {
        checkMechanism=new InputValidator();
    }

    /**
     * Part of the Singleton pattern
     * @return unique instance of InputParser class
     */
    public static InputParser getInstance()
    {
        if(uniqueInstance==null)
        {
            synchronized (ComputationalMachine.class)
            {
                if(uniqueInstance==null)
                {
                    uniqueInstance=new InputParser();
                }

            }
        }
        return uniqueInstance;
    }

    /**
     * Function for formatting the input into a suitable for computing format
     * @param input - unformatted input
     * @return formatted input
     * @throws InvalidTypeOfEquationComponent - if there exists and illegal component in the equation
     */
    public String[] processInput(final String input) throws InvalidTypeOfEquationComponent
    {
        String[] splitInput=input.split(" ");
        for(String item:splitInput)
        {
            if(!checkMechanism.validateComponent(item))
            {
                throw new InvalidTypeOfEquationComponent("The input should contain only numbers and the supported operators");
            }
        }
        return splitInput;
    }

    /**
     * @param component
     * @return
     */
    public MathComponentType getTypeOfComponent(final String component)
    {
        if(checkMechanism.isValidNumber(component))
        {
            return MathComponentType.NUMBER;
        }
        else if(checkMechanism.isValidOperator(component))
        {
            return MathComponentType.OPERATOR;
        }
        return MathComponentType.INVALID;
    }
}