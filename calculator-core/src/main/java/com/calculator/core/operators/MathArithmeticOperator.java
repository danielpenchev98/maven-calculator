package com.calculator.core.operators;


import com.calculator.core.exceptions.BadInputException;

/**
 * Interface which increases the modifiability of the system, new operators could be added easily or removed without changing other classes
 */
public interface MathArithmeticOperator extends EquationComponent {

     /**
      * @param firstNumber - the first parameter of the mathematical operation
      * @param secondNumber - the second parameter of the mathematical operation
      * @return the result of the mathematical operation
      * @throws ArithmeticException - if some type of error occurs like division on zero
      */
     double compute(final double firstNumber, final double secondNumber) throws BadInputException;

     /**
      * @return the priority of the operator
      */
     int getPriority();

     /**
      * @return if the operator is left associative
      */
     boolean isLeftAssociative();

}
