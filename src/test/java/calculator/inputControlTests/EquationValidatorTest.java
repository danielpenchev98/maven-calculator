package calculator.inputControlTests;

import calculator.inputControl.EquationValidator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class EquationValidatorTest {

    private EquationValidator validator;
    @Before
    public void setUp()  {
        validator=new EquationValidator();
    }

    @After
    public void tearDown() {
        validator=null;
    }

    //region validateComponentTests
    @Test
    public void isValidEquation_EquationWithMissingOperatorBetweenNumberAndBracket_Legal() {
        assertTrue(validator.hasNonOperatorBeforeBracket("1 + 2 ( ) * 5"));
    }

    @Test
    public void isValidEquation_EquationWithBracketsWithintBrackets_Legal() {
        assertTrue(validator.hasBracketBalance("1 + ( ( 10 + 20 ) * ( -5 ) )"));
    }

    @Test
    public void isValidEquation_StringRepresentingIllegalComponentAsParam_Illegal() {
        assertTrue(validator.isValidEquation("1 + 5 + ( -1 ) + 2"));
    }
    //endregion
    @Test
    public void checkBracketBalance()
    {
        assertTrue(validator.hasBracketBalance("( ())")); }




}