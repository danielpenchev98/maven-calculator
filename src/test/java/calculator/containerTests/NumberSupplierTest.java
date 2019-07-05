package calculator.containerTests;

import calculator.container.NumberSupplier;
import calculator.container.OutOfItemsException;
import calculator.container.Pair;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class NumberSupplierTest {

    private NumberSupplier supplier;

    @Before
    public void setUp() {
        supplier=new NumberSupplier();
    }

    @After
    public void tearDown()
    {
        supplier=null;
    }

    //region getNumberOfItemsTests
    @Test
    public void getNumberOfItems_EmptyContainer_ZeroLoad()
    {
        assertEquals(0,supplier.getNumberOfItems());
    }

    @Test
    public void getNumberOfItems_NonEmptyContainer_CurrentLoad()
    {
        supplier.addNumber("1234");
        assertEquals(1,supplier.getNumberOfItems());
    }
    //endregion

    //region getOneNumberTest
    @Test
    public void getOneNumber_OneItemInTheContainer_RemoveAnItemFromTheContainer() throws OutOfItemsException {
        supplier.addNumber("1234");
        String result=supplier.getOneNumber();
        assertEquals("1234",result);
    }

    @Test(expected = OutOfItemsException.class)
    public void getOneNumber_EmptyContainer_OutOfItemsExceptionThrown() throws OutOfItemsException
    {
        supplier.getOneNumber();
    }
    //endregion

    //region addNumberTests
    @Test
    public void addNumber_NonEmptyContainer_AddItemInTheContainer() throws OutOfItemsException {
        supplier.addNumber("1234");
        supplier.addNumber("5678");
        assertEquals("5678",supplier.getOneNumber());
    }

    @Test
    public void addNumber_EmptyContainer_IncreaseTheLoadOfContainerWithOne()
    {
        supplier.addNumber("1234");
        assertEquals(1,supplier.getNumberOfItems());
    }
    //endregion

    //region getTwoNumbersTest
    @Test (expected = OutOfItemsException.class)
    public void getTwoNumbers_EmptyContainer_OutOfItemsException() throws OutOfItemsException {
       supplier.getTwoNumbers();
    }

    @Test (expected = OutOfItemsException.class)
    public void getTwoNumbers_ContainerWithOneItem_OutOfItemsExceptions() throws OutOfItemsException
    {
        supplier.addNumber("1234");
        supplier.getTwoNumbers();
    }

    //Why do i need this test? Of the implementation changes, for example it doesnt use getOneNumber() anymore, it should pass the test
    @Test
    public void getTwoNumbers_ContainerWithTwoOrMoreItems_DecreaseTheNumberOfItemsInContainer() throws OutOfItemsException
    {
          supplier.addNumber("1234");
          supplier.addNumber("5678");
          supplier.getTwoNumbers();
          assertEquals(0,supplier.getNumberOfItems());
    }

    @Test
    public void getTwoNumbers_ContainerWithEnoughItems_GetItemsInParticularOrder() throws OutOfItemsException
    {
        supplier.addNumber("1234");
        supplier.addNumber("5678");
        Pair result=supplier.getTwoNumbers();
        assertTrue(result.getFirstMember().equals("1234")&&result.getSecondMember().equals("5678"));
    }
    //endregion
}