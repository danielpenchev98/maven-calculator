package calculator.inputControlTests;

import calculator.inputControl.InputValidator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class InputValidatorTest {

    private InputValidator validator;
    @Before
    public void setUp()  {
        validator=new InputValidator();
    }

    @After
    public void tearDown() {
        validator=null;
    }

    //region validateComponentTests
    @Test
    public void validateComponent_StringRepresentingNumberAsParam_Legal() {
        assertTrue(validator.validateComponent("101"));
    }

    @Test
    public void validateComponent_StringRepresentingOperatorAsParam_Legal() {
        assertTrue(validator.validateComponent("+"));
    }

    @Test
    public void validateComponent_StringRepresentingIllegalComponentAsParam_Illegal() {
        assertFalse(validator.validateComponent("**"));
    }
    //endregion

    //region isValidNumberTests
    @Test
    public void isValidNumber_StringRepresentingNumberAsParam_Legal() {
        assertTrue(validator.isValidNumber("777"));
    }


    @Test
    public void isValidOperator_StringRepresentingNonNumberAsParam_Illegal() {
        assertFalse(validator.isValidNumber("123ab"));
    }
    //endregion

    //region isValidNumber
    @Test
    public void isValidNumber_StringRepresentingOperatorAsParam_Legal()
    {
        assertTrue(validator.isValidOperator("/"));
    }

    @Test
    public void isValidOperator_StringRepresentingNonOperatorAsParam_Illegal() {
        assertFalse(validator.isValidOperator("+/-"));
    }
    //endregion
}