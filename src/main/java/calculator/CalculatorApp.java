package calculator;


import calculator.computation.ComputationalMachine;
import calculator.computation.InvalidMathematicalOperationException;
import calculator.computation.MathComponentType;
import calculator.container.NumberSupplier;
import calculator.container.OutOfItemsException;
import calculator.container.Pair;
import calculator.inputControl.InputParser;
import calculator.inputControl.InvalidTypeOfEquationComponent;

class CalculatorApp {

    private String[] parseInput(String input, InputParser parser) throws InvalidTypeOfEquationComponent, NullPointerException
    {
        String[] splitInput= parser.processInput(input);
        if(splitInput==null)
        {
            throw new NullPointerException("The input consists of 0 or 1 components");
        }
        return splitInput;
    }

    private int getResultFromEquation(final String[] splitInput, final InputParser parser, final NumberSupplier supplier, final ComputationalMachine calculator) throws Exception
    {
        for (String component : splitInput) {
            MathComponentType type = parser.getTypeOfComponent(component);
            if (type == MathComponentType.NUMBER)
            {
                supplier.addNumber(component);
            }
            else
            {
                Pair numbers = supplier.getTwoNumbers();
                int result = calculator.computeAction(component, Integer.valueOf(numbers.getFirstMember()), Integer.valueOf(numbers.getSecondMember()));
                supplier.addNumber(String.valueOf(result));
            }
            //there shouldnt be a case where the tyoe is INVALID - maybe should check this case
        }
        if(supplier.getNumberOfItems()!=1)
        {
           throw new Exception("Invalid equation. Logical error. There arent enough operators");
        }

        return Integer.valueOf(supplier.getOneNumber());
    }


    public void calculate(final String equation)
    {
        InputParser parser= InputParser.getInstance();
        String[] splitInput;
        try {
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
        }


        NumberSupplier supplier=new NumberSupplier();
        ComputationalMachine calculator= ComputationalMachine.getInstance();
        try {
            int finalResult=getResultFromEquation(splitInput,parser,supplier,calculator);
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
