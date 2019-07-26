package com.calculator.core;


public class CalculatorAppConsole {
    public static void main(String[] argc)
    {
        CalculatorApp application=new CalculatorApp();
        try {
            System.out.print(application.calculateResult(argc[0]));
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }
    }
}
