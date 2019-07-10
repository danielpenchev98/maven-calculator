package calculator.inputControlTests;

import calculator.exceptions.*;
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

    @Test(expected = MissingBracketException.class)
    public void validateEquation_EquationWithMissingBracket_Illegal() throws Exception
    {
        validator.validateEquation("1 + ( ( 2 * ( -3 ) -5 ) ");
    }

    //endregion

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
    public void isValidNumber_InvalidNumberWithLettersInIt_Illegal()
    {
        assertFalse(validator.isValidNumber("132A231"));
    }

    @Test
    public void isValidNumber_NumberBeginningWithZero_Illegal()
    {
        assertFalse(validator.isValidNumber("0123"));
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