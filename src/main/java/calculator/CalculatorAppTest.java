package calculator;


import java.util.Scanner;

public class CalculatorAppTest {
    public static void main(String[] argc)
    {
       Scanner scan=new Scanner(System.in);
        String userInput=scan.nextLine();
        CalculatorApp application=new CalculatorApp();
        application.processEquationAndCalculateResult(userInput);
    }
}
