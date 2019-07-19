package calculator.inputcontrol;

import calculator.exceptions.InvalidComponentException;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

//TODO when isValidNumber is changed to private then all these test will be testing validateComponent
public class ComponentValidatorTest {

    private ComponentValidator validator;

    @Before
    public void setUp(){
        validator=new ComponentValidator();
    }

    @Test(expected = InvalidComponentException.class)
    public void validateComponents_NonSupportedComponent_Illegal() throws Exception
    {
        List<String> input=new LinkedList<>(Arrays.asList("(",")","PI","*"));
        validator.validateComponents(input);
    }

    //region isValidNumber
    @Test
    public void isValidNumber_NumberAsParam_Legal()
    {
        assertTrue(validator.isValidNumber("-102.1"));
    }

    @Test
    public void isValidNumber_NumberWithoutDigitAfterFloatingPoint_Illegal()
    {
        assertFalse(validator.isValidNumber("-102."));
    }

    @Test
    public void isValidNumber_NumberWithTwoFloatingPointsSeparatedByDigits_Illegal()
    {
        assertFalse(validator.isValidNumber("12.24.4"));
    }

    @Test
    public void isValidNumber_NumberWithTwoSequentialFloatingPoints_Illegal()
    {
        assertFalse(validator.isValidNumber("12..2"));
    }

    @Test
    public void isValidNumber_NumberStartingWithFloatingPoint_Illegal()
    {
        assertFalse(validator.isValidNumber(".1234"));
    }

    @Test
    public void isValidNumber_NumberEndingWithFloatingPoint_Illegal()
    {
        assertFalse(validator.isValidNumber("1234."));
    }

    @Test
    public void isValidNumber_InvalidNumberWithTwoSigns_Illegal()
    {
        assertFalse(validator.isValidNumber("--102"));
    }

    @Test
    public void isValidNumber_InvalidNumberContainingLetters_Illegal()
    {
        assertFalse(validator.isValidNumber("132A231"));
    }

    @Test
    public void isValidNumber_NumberBeginningWithZero_Legal()
    {
        assertTrue(validator.isValidNumber("0123"));
    }
    //endregion

    //region isValidOperation

    @Test
    public void isValidOperator_ValidOperatorAsParam_Legal()
    {
        assertTrue(validator.isValidArithmeticOperator("+"));
    }

    @Test
    public void IsValidOperator_InvalidOperatorAsParam_Illegal()
    {
        assertFalse(validator.isValidArithmeticOperator("%"));
    }
    //endregion

}