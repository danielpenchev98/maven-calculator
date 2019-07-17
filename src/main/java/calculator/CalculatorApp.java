package calculator;


import calculator.exceptions.InvalidEquationException;
import calculator.inputControl.*;

/**
 * Class which represents the calculator as a whole - uses the 3 main abstractions :
 * InputFormatter - used as a input formatter,

 * ComputationalMachine - used for calculation the operations* NumberSupplier - storage, used for calculating the equation,
 */
class CalculatorApp {

    private EquationValidator validator;

    public CalculatorApp()
    {
        this.validator=new EquationValidator();
    }

     CalculatorApp(EquationValidator validator)
     {
         this.validator=validator;
     }
    /**
     * Functions which formats and calculates the equation
     * @param equation - user input
     */
    public double calculateResult(final String equation) throws Exception {
        if(equation.equals(""))
        {
            throw new InvalidEquationException("empty equation");
        }

        InputFormatter parser= new InputFormatter();
        String formattedInput=parser.doFormat(equation);
        String separator=parser.getComponentSeparator();


        validator.validateEquation(formattedInput,separator);

         return 0;
     /*   InputFormatter parser= new InputFormatter();
        String formattedInput=parser.formatInput(equation);



        MathArithmeticOperatorFactory arithmeticOperatorFactory=new MathArithmeticOperatorFactory();

        ReversePolishComponentTriggerFactory operatorFactory=new ReversePolishComponentTriggerFactory(validator,arithmeticOperatorFactory);
        ReversePolishNotationParser specialParser=new ReversePolishNotationParser(operatorFactory);
        String reversePolishFormatEquation;
        try {
            reversePolishFormatEquation = specialParser.formatFromInfixToReversedPolishNotation(formattedInput);
        }
        catch (EmptyStackException problemWithReversePolishParser)
        {
            //TODO should save it in log file for the developers - it shouldn't even happen at this point
            System.out.println("Please try again");
            return;
        }
        catch(InvalidOperatorException exc)
        {
            //should save it in log file for the developers
            //this error should even happen at this point of the program
            System.out.println("Please try again");
            return;
        }

        ComputationalMachine calculator= new ComputationalMachine(arithmeticOperatorFactory);
        ReversePolishCalculationAlgorithm algorithm=new ReversePolishCalculationAlgorithm(calculator,validator);

        try {
            double finalResult=algorithm.calculateEquation(reversePolishFormatEquation);
            System.out.println(finalResult);
        }
        catch (EmptyStackException noItems)
        {
            System.out.println("Invalid equation. Not enough Numbers");
        }
        catch(Exception logicalError) {
            System.out.println(logicalError.getMessage());
        }
*/
    }

}
