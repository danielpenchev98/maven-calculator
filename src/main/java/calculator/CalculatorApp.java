package calculator;


import calculator.computation.ComputationalMachine;
import calculator.container.OutOfItemsException;
import calculator.inputControl.EquationValidator;
import calculator.inputControl.PrimalParser;
import calculator.inputControl.InvalidTypeOfEquationComponent;

/**
 * Class which represents the calculator as a whole - uses the 3 main abstractions :
 * PrimalParser - used as a input formatter,
 * NumberSupplier - storage, used for calculating the equation,
 * ComputationalMachine - used for calculation the operations
 */
class CalculatorApp {


    /**
     * Functions which formats and calculates the equation
     * @param equation - user input
     */
    void processEquationAndCalculateResult(final String equation)
    {
        PrimalParser parser= PrimalParser.getInstance();
        String[] splitInput;
        /*try {
            splitInput=parseInput(equation,parser);
        }
        catch(InvalidTypeOfEquationComponent invalidType)
        {
            System.out.println("Invalid equation. The input should consists of numbers and supported mathematical operations");
            return;
        }
        catch(NullPointerException nullPtr)
        {
            System.out.println(nullPtr.getMessage());
            return;
        }*/

        String formattedInput=parser.formatInput(equation);
        EquationValidator validator=new EquationValidator();
        if(!validator.isValidEquation(formattedInput))
        {
            System.out.println(" Invalid equation");
            return;
        }
        splitInput=formattedInput.split(" ");


        ComputationalMachine calculator= ComputationalMachine.getInstance();
        ReversePolishCalculationAlgorithm algorithm=ReversePolishCalculationAlgorithm.getInstance(calculator,parser);
        try {
            int finalResult=algorithm.calculateEquation(splitInput);
            System.out.printf("%d\n",finalResult);
        }
        catch (OutOfItemsException noItems)
        {
            System.out.println("Invalid equation ");
        }
        catch(Exception logicalError) {
            System.out.println(logicalError.getMessage());
        }

    }
}
