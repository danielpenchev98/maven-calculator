package calculator.inputControl;

import calculator.computation.ComputationalMachine;
import calculator.computation.MathComponentType;

public class InputParser {
    private static volatile InputParser uniqueInstance;
    private InputValidator checkMechanism;

    private InputParser()
    {
        checkMechanism=new InputValidator();
    }

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

    // formatting the input
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

    //getting the type of the component of the equation - number or operator
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