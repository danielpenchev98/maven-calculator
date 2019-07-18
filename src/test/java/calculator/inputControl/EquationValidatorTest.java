package calculator.inputControl;

import calculator.exceptions.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class EquationValidatorTest {

    private EquationValidator validator;
    @Before
    public void setUp()  {
        validator=new EquationValidator();
    }

    //region validateEquation
    @Test(expected = InvalidEquationException.class)
    public void validateEquation_EquationWithMissingOperatorBetweenNumberAndBracket_Illegal() throws Exception {
        String[] formattedEquation={"2","("};
        validator.validateEquation(formattedEquation);
    }

    @Test(expected = InvalidEquationException.class)
    public void validateEquation_EquationWithClosingBracketNextToOpeningBracket_Legal() throws Exception
    {
        String[] formattedEquation={"(","10","+","20",")","(","-5",")"};
        validator.validateEquation(formattedEquation);
    }

    @Test
    public void validateEquation_EquationWithOnlyANumber_Legal() throws Exception
    {
        String[] formattedEquation={"1"};
        validator.validateEquation(formattedEquation);
    }

    @Test
    public void validateEquation_SimpleEquation_Illegal() throws Exception {
        String[] formattedEquation={"(","(","-1","+","2",")",")"};
        validator.validateEquation(formattedEquation);
    }

    @Test(expected = InvalidEquationException.class)
    public void validateEquation_EquationOnlyWithOperatorInTheBrackets_Illegal() throws Exception
    {
        String[] formattedEquation={"(","+",")","+","1"};
        validator.validateEquation(formattedEquation);
    }

    @Test(expected = InvalidEquationException.class)
    public void validateEquation_EquationWithSequentialOperators_Illegal() throws Exception
    {
        String[] formattedEquation={"2","+","+","1"};
        validator.validateEquation(formattedEquation);
    }

    @Test(expected = InvalidEquationException.class)
    public void validateEquation_EquationWithEmptyBrackets_Illegal() throws Exception
    {
        String[] formattedEquation={"(",")","+","2"};
        validator.validateEquation(formattedEquation);
    }

    @Test(expected = InvalidComponentException.class)
    public void validateEquation_EquationWithIllegalComponent_Illegal() throws Exception
    {
        String[] formattedEquation={"1ha"};
        validator.validateEquation(formattedEquation);
    }

    @Test(expected = InvalidEquationException.class)
    public void validateEquation_EmptyEquation_Illegal() throws Exception
    {
        String[] formattedEquation={""};
        validator.validateEquation(formattedEquation);
    }

    @Test(expected = InvalidEquationException.class)
    public void validateEquation_EquationWithMissingBracket_Illegal() throws Exception
    {
        String[] formattedEquation={"(","(","2","*","(","-3",")","-5",")"};
        validator.validateEquation(formattedEquation);
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