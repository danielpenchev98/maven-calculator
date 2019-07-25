package com.calculator.core;


public class CalculatorAppConsole {
    public static void main(String[] argc) throws Exception
    {
        CalculatorApp application=new CalculatorApp();
        try {
            System.out.println(application.calculateResult(argc[0]));
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
            throw ex;
        }
    }
}
