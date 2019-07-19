package calculator.inputcontrol;

import calculator.exceptions.*;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class EquationValidatorTest {

    private EquationValidator validator;
    @Before
    public void setUp()  {
        validator=new EquationValidator();
    }

    //region validateEquation

    @Test(expected = NullPointerException.class)
    public void validateEquation_NullReference_Illegal() throws Exception
    {
        validator.validateEquation(null);
    }

    @Test(expected = InvalidEquationException.class)
    public void validateEquation_EquationWithMissingOperatorBetweenNumberAndBracket_Illegal() throws Exception {

        List<String> input=new LinkedList<>(Arrays.asList("2","(","-5",")"));
        input.add("2");
        input.add("(");
        input.add("-5");
        input.add(")");

        validator.validateEquation(input);
    }

    @Test(expected = InvalidEquationException.class)
    public void validateEquation_EquationWithClosingBracketNextToOpeningBracket_Legal() throws Exception
    {
        List<String> input=new LinkedList<>(Arrays.asList("(","(","10","+","20",")","(","-5",")",")"));
        validator.validateEquation(input);
    }

    @Test
    public void validateEquation_EquationWithOnlyANumber_Legal() throws Exception
    {
        List<String> input=new LinkedList<>();
        input.add("1");
        validator.validateEquation(input);
    }

    @Test
    public void validateEquation_SimpleEquation_Illegal() throws Exception {
        List<String> input=new LinkedList<>(Arrays.asList("(","(","-1","+","5","+","2",")",")"));
        validator.validateEquation(input);
    }

    @Test(expected = InvalidEquationException.class)
    public void validateEquation_EquationOnlyWithOperatorInTheBrackets_Illegal() throws Exception
    {
        List<String> input=new LinkedList<>(Arrays.asList("(","+",")","+","1"));
        validator.validateEquation(input);
    }

    @Test(expected = InvalidEquationException.class)
    public void validateEquation_EquationWithSequentialOperators_Illegal() throws Exception
    {
        List<String> input=new LinkedList<>(Arrays.asList("2","+","+","1"));
        validator.validateEquation(input);
    }

    @Test(expected = InvalidEquationException.class)
    public void validateEquation_EquationWithEmptyBrackets_Illegal() throws Exception
    {
        List<String> input=new LinkedList<>(Arrays.asList("(",")","+","2"));
        validator.validateEquation(input);
    }

    @Test(expected = InvalidComponentException.class)
    public void validateEquation_EquationWithIllegalComponent_Illegal() throws Exception
    {
        List<String> input=new LinkedList<>();
        input.add("1ha");
        validator.validateEquation(input);
    }

    @Test(expected = InvalidEquationException.class)
    public void validateEquation_EmptyEquation_Illegal() throws Exception
    {
        List<String> input=new LinkedList<>();
        input.add("");
        validator.validateEquation(input);
    }

    @Test(expected = InvalidEquationException.class)
    public void validateEquation_EquationWithMissingBracket_Illegal() throws Exception
    {
        List<String> input=new LinkedList<>(Arrays.asList("(","(","2","+","1",")"));
        validator.validateEquation(input);
    }

    @Test(expected = InvalidEquationException.class)
    public void validateEquation_OperatorAfterOpeningBracket_Illegal() throws Exception
    {
        List<String> input=new LinkedList<>(Arrays.asList("(","+","1",")"));
        validator.validateEquation(input);
    }

    @Test(expected = InvalidEquationException.class)
    public void validateEquation_OperatorBeforeClosingBracket_Illegal() throws Exception
    {
        List<String> input=new LinkedList<>(Arrays.asList("(","1","+",")"));
        validator.validateEquation(input);
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