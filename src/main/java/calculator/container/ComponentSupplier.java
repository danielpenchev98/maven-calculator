package calculator.container;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 *  Class which represents the containerTests, which helps for calculating the equation via Reversed Polish Notation
 */
public class ComponentSupplier {

    /**
     * storage for the items of type String. The type String is suitable for the purpose of representing different types of numbers
     */
    private Stack<String> itemStorage;


    public ComponentSupplier()
    {
        itemStorage=new Stack<>();
    }

    /**
     * @param number - item to be added in the storage
     */
    public void addItem(String number) {
        itemStorage.push(number);
    }

    /**
     * @param numberOfItems - number of items to get
     * @return wanted items
     * @throws OutOfItemsException - not enough items in the container
     */
    public List<String> receiveListOfItems(final int numberOfItems) throws OutOfItemsException {

        if(numberOfItems>numberOfItemsAvailable())
        {
            throw new OutOfItemsException("The operation requires more items in the container");
        }

        List<String> items=new LinkedList<>();
        for(int i=0;i<numberOfItems;i++)
        {
            items.add(0,itemStorage.peek());
            itemStorage.pop();
        }
        return items;
    }


    /**
     * @return the current load of the storage
     */
    public int numberOfItemsAvailable()
    {
        return itemStorage.size();
    }

}
