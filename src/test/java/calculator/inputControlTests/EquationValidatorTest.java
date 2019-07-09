package calculator.inputControlTests;

import calculator.inputControl.*;
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

    //region validateEquation
    @Test(expected = OperatorMisplacementException.class)
    public void validateEquation_EquationWithMissingOperatorBetweenNumberAndBracket_Illegal() throws Exception {
        validator.validateEquation("1 + 2 ( ) * 5");
    }

    @Test(expected = OperatorMisplacementException.class)
    public void validateEquation_EquationWithClosingBracketNextToOpeningBracket_Legal() throws Exception
    {
        validator.validateEquation("1 + ( ( 10 + 20 ) ( -5 ) )");
    }

    @Test
    public void validateEquation_SimpleEquation_Illegal() throws Exception {
        validator.validateEquation("( ( -1 + 5 + 2 ) )");
    }

    @Test(expected = MissingNumberException.class)
    public void validateEquation_EquationOnlyWithOperatorInTheBrackets_Illegal() throws Exception
    {
        validator.validateEquation("( + ) + 1 * 2");
    }

    @Test(expected = OperatorMisplacementException.class)
    public void validateEquation_EquationWithSequentialOperators_Illegal() throws Exception
    {
        validator.validateEquation("1 + 2 + + ");
    }

    @Test(expected = MissingNumberException.class)
    public void validateEquation_EquationWithEmptyBrackets_Illegal() throws Exception
    {
        validator.validateEquation("( ) + 2");
    }

    @Test(expected = InvalidTypeOfEquationComponent.class)
    public void validateEquation_EquationWithIllegalComponent_Illegal() throws Exception
    {
        validator.validateEquation(" 1ha + 2 + 3");
    }

    @Test(expected = EmptyEquationException.class)
    public void validateEquation_EmptyEquation_Illegal() throws Exception
    {
        validator.validateEquation("");
    }
    //endregion

    //region isValidNumber
    @Test
    public void isValidNumber_ValidNumberAsParam_Legal()
    {
        assertTrue(validator.isValidNumber("-102"));
    }

    @Test
    public void isValidNumber_InvalidNumberWithTwoSigns_Illegal()
    {
        assertFalse(validator.isValidNumber("--102"));
    }

    @Test
    public void isValidNumber_InvalidNumberWithLettersInIt_Illegal()
    {
        assertFalse(validator.isValidNumber("132A231"));
    }
    //endregion

    //region isValidOperation

    @Test
    public void isValidOperator_ValidOperatorAsParam_Legal()
    {
        assertTrue(validator.isValidOperator("+"));
    }

    @Test
    public void IsValidOperator_InvalidOperatorAsParam_Illegal()
    {
        assertFalse(validator.isValidOperator("%"));
    }
    //endregion

}