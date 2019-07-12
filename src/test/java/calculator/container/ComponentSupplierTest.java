package calculator.container;

import calculator.exceptions.OutOfItemsException;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ComponentSupplierTest {

    private ComponentSupplier<String> supplier;

    @Before
    public void setUp()
    {
          supplier=new ComponentSupplier<>();
    }

    @Test
    public void addItem_AddStringToContainer_NextElementIsThatStringAndSizeIsIncreased() throws OutOfItemsException {
        int size=supplier.numberOfItemsAvailable();
        supplier.addItem("TestString");
        assertTrue(supplier.viewNextItem().equals("TestString")&&supplier.numberOfItemsAvailable()==size+1);
    }

    @Test
    public void receiveListOfNextItems_RequestMultipleStringsToContainer_GetTheStringsInReversedOrder() throws OutOfItemsException {
        supplier.addItem("First");
        supplier.addItem("Second");
        supplier.addItem("Third");
        List<String> realResult=supplier.receiveListOfNextItems(3);
        assertArrayEquals("First Second Third".split(" "),realResult.toArray());
    }

    @Test(expected = OutOfItemsException.class)
    public void receiveListOfNextItems_RequestMoreItemsThanContainerHas_OutOfItemsExceptionThrown() throws OutOfItemsException
    {
        supplier.addItem("First");
        supplier.addItem("Second");
        supplier.addItem("Third");
        supplier.receiveListOfNextItems(4);
    }

    @Test
    public void receiveNextItem_RequestNextItemFromContainer_DecreaseLoadOfContainer() throws OutOfItemsException {
        supplier.addItem("First");
        supplier.addItem("Second");
        int size=supplier.numberOfItemsAvailable();
        assertTrue(supplier.receiveNextItem().equals("Second")&&supplier.numberOfItemsAvailable()==size-1);
    }

    @Test(expected = OutOfItemsException.class)
    public void receiveNextItem_RequestNextItemFromEmptyContainer_OutOfItemsExceptionThrown() throws OutOfItemsException {
       supplier.receiveNextItem();
    }

    @Test
    public void viewNextItem_RequestToViewNextItemFromContainer_LoadOfContainerSame() throws OutOfItemsException {
        supplier.addItem("First");
        supplier.addItem("Second");
        int size=supplier.numberOfItemsAvailable();
        assertTrue(supplier.viewNextItem().equals("Second")&&supplier.numberOfItemsAvailable()==size);
    }

    @Test
    public void removeNextItem_RemoveNextItemFromContainer_DecreasedSize() throws OutOfItemsException {
        supplier.addItem("First");
        supplier.addItem("Second");
        int size=supplier.numberOfItemsAvailable();
        supplier.removeNextItem();
        assertTrue(supplier.numberOfItemsAvailable()==size-1);
    }

    @Test
    public void numberOfItemsAvailable_SeveralItemsInContainer_LoadOfContainer() {
        supplier.addItem("First");
        supplier.addItem("Second");
        assertEquals(2,supplier.numberOfItemsAvailable());
    }

    @Test
    public void isOutOfItems_EmptyContainer_ZeroLoad() {
        assertEquals(0,supplier.numberOfItemsAvailable());
    }
}