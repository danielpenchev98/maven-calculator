package calculator;

import java.util.Scanner;

public class CalculatorAppConsole {
    public static void main(String[] argc)
    {
       Scanner scan=new Scanner(System.in);
        String userInput=scan.nextLine();
        CalculatorApp application=new CalculatorApp();

        System.out.println("1".split(" ").length);
       // application.processEquationAndCalculateResult();
        //application.printResult(String)

        //application.processEquationAndCalculateResult(userInput);
       //TODO Double.MAX_VALUE wont pass the regex because of the exponent symbol, fix it maybe
    }
}
