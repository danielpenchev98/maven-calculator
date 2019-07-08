package calculator.inputControl;


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
    private EquationValidator checkMechanism;

    private InputParser()
    {
        checkMechanism=new EquationValidator();
    }

    /**
     * Part of the Singleton pattern
     * @return unique instance of InputParser class
     */
    public static InputParser getInstance()
    {
        if(uniqueInstance==null)
        {
            synchronized (InputParser.class)
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
     * @param input - unformatted input
     * @return formatted input
     * @throws InvalidTypeOfEquationComponent - if there exists and illegal component in the equation
     */
    public String[] formatAndValidateInput(final String input) throws InvalidTypeOfEquationComponent
    {

        /*if(!checkBrackets(input))
        {
            throw new MissingBracketException("Missing Bracket");
        }*/
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



    public String removeJunkSpaces(String equation)
    {

        return equation.replaceAll("[ ]+"," ").replaceAll("^ | $","");
    }
    public String addSpaceAfterEveryComponent(String equation)
    {
        return equation.replaceAll("([0-9]+)","$1 ").replaceAll("([^0-9 ])","$1 ");
    }

    public String removeSpaceBetweenTheNumberAndItsSign(String equation)
    {
        return equation.replaceAll("[(] - ([0-9]+)","$1 -$2").replaceAll("^- ([0-9]+)","-$1");
    }
    /**
     * @param component - component of the equation
     * @return the type of the component
     */
    public MathComponentType getTypeOfComponent(final String component) throws InvalidTypeOfEquationComponent
    {
        if(checkMechanism.isValidNumber(component))
        {
            return MathComponentType.NUMBER;
        }
        else if(checkMechanism.isValidOperator(component))
        {
            return MathComponentType.OPERATOR;
        }
        throw new InvalidTypeOfEquationComponent("Unsupported component has been found");
    }

}