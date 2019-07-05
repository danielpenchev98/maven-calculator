package calculator.containerTests;

import calculator.container.Pair;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PairTest {

    private Pair names;

    @Before
    public void setUp(){
        names=new Pair("Harry","Potter");
    }

    @After
    public void tearDown() {
        names=null;
    }

    @Test
    public void getFirstMember_True() {
        assertEquals("Harry",names.getFirstMember());
    }

    @Test
    public void getFirstMember_False()
    {
        assertNotEquals("Potter",names.getFirstMember());
    }

    @Test
    public void getSecondMember_True()
    {
        assertEquals("Potter",names.getSecondMember());
    }

    @Test
    public void getSecondMember_False()
    {
        assertNotEquals("Harry",names.getSecondMember());
    }
}