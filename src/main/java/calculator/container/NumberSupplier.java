package calculator.container;

import java.util.Stack;

//dont know yet if more of these would be needed in the future, so for now it isnt Singleton
public class NumberSupplier {
    private Stack<String> numbers;

    public NumberSupplier()
    {
        numbers=new Stack<>();
    }
    public void addNumber(String number)
    {
        numbers.push(number);
    }

    public String getOneNumber() throws OutOfItemsException
    {
        if(numbers.size()==0)
        {
            throw new OutOfItemsException("The operation requires more items in the container");
        }
        String result= numbers.peek();
        numbers.pop();
        return result;
    }

    public Pair getTwoNumbers() throws OutOfItemsException
    {
        String secondNum=getOneNumber();
        String firstNum=getOneNumber();
        return new Pair(firstNum,secondNum);
    }

    public int getNumberOfItems()
    {
        return numbers.size();
    }

}
