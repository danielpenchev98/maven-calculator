package calculator.container;

import java.util.Stack;

/**
 *  Class which represents the containerTests, which helps for calculating the equation via Reversed Polish Notation
 */
public class NumberSupplier {

    /**
     * storage for the items of type String. The type String is suitable for the purpose of representing different types of numbers
     */
    private Stack<String> numbers;

    /**
     * Default constructor
     */
    public NumberSupplier()
    {
        numbers=new Stack<>();
    }

    /**
     * This function supplies the storage with more items
     * @param number - item to be added in the storage
     */
    public void addNumber(String number)
    {
        numbers.push(number);
    }

    /**
     * This function is used mainly as a help function of getting two elements from the storage and for getting the result of the equation - at the end there should be only 1 item in the storage
     * @return - the top item from the storage - Stack
     * @throws OutOfItemsException - if the stack is empty
     */
    public String getOneNumber() throws OutOfItemsException
    {
        if(numbers.size()==0)
        {
            throw new OutOfItemsException("The operation requires more items in the containerTests");
        }
        String result= numbers.peek();
        numbers.pop();
        return result;
    }

    /**
     * This function supplies the ComputationalMachine object with 2 arguments to compute the operation
     * @return two items wrapped in the class Pair
     * @throws OutOfItemsException if there arent enough items in the storage
     */
    public Pair getTwoNumbers() throws OutOfItemsException
    {
        String secondNum=getOneNumber();
        String firstNum=getOneNumber();
        return new Pair(firstNum,secondNum);
    }

    /**
     * Monitoring function
     * @return the current load of the storage
     */
    public int getNumberOfItems()
    {
        return numbers.size();
    }

}
