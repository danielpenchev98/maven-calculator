package calculator;


import java.util.Scanner;

public class CalculatorAppConsole {
    public static void main(String[] argc)
    {
       Scanner scan=new Scanner(System.in);
        String userInput=scan.nextLine();
        CalculatorApp application=new CalculatorApp();
        application.processEquationAndCalculateResult(userInput);
    }
}
