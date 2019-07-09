package calculator.computation;


/**
 * Interface which increases the modifiability of the system, new operators could be added easily or removed without changing other classes
 */
public interface MathOperation {

     /**
      * @param first_number - the first parameter of the mathematical operation
      * @param second_number - the second parameter of the mathematical operation
      * @return the result of the mathematical operation
      * @throws ArithmeticException - if some type of error occurs like division on zero
      */
     int compute(final int first_number, final int second_number) throws ArithmeticException;

     /**
      * @return the priority of the operator
      */
     int getPriority();


     /**
      * @return if the operator is left associative
      */
     boolean isLeftAssociative();
}
