package calculator;

import java.util.Scanner;

public class CalculatorAppConsole {
    public static void main(String[] argc)
    {
       Scanner scan=new Scanner(System.in);
        String userInput=scan.nextLine();
        CalculatorApp application=new CalculatorApp();
        try {
            System.out.println(application.calculateResult(userInput));
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }
            // application.processEquationAndCalculateResult();
        //application.printResult(String)

        //application.processEquationAndCalculateResult(userInput);
       //TODO Double.MAX_VALUE wont pass the regex because of the exponent symbol, fix it maybe
    }
}
