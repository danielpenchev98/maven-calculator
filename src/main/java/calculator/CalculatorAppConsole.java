package calculator;

import java.util.Arrays;
import java.util.Scanner;

public class CalculatorAppConsole {
    public static void main(String[] argc)
    {
       Scanner scan=new Scanner(System.in);
        String userInput=scan.nextLine();
        CalculatorApp application=new CalculatorApp();
        try {
            application.calculateResult("1");
        }
        catch (Exception exp)
        {
            System.out.println("noooo");
        }
            // application.processEquationAndCalculateResult();
        //application.printResult(String)

        //application.processEquationAndCalculateResult(userInput);
       //TODO Double.MAX_VALUE wont pass the regex because of the exponent symbol, fix it maybe
    }
}
