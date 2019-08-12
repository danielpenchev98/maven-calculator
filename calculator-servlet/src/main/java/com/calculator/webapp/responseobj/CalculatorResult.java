package com.calculator.webapp.responseobj;

public class CalculatorResult {

    final String equation;
   final String result;

   public CalculatorResult(final String equation, final String result)
   {
       this.equation=equation;
       this.result=result;
   }
}
