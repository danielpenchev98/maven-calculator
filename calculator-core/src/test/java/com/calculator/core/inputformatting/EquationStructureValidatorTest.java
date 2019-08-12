package com.calculator.core.inputformatting;

import com.calculator.core.exceptions.InvalidEquationException;
import com.calculator.core.exceptions.InvalidParameterException;
import org.junit.BeforeClass;
import org.junit.Test;

public class EquationStructureValidatorTest {

    private static EquationStructureValidator validator;

    @BeforeClass
    public static void setUp()
    {
        validator=new EquationStructureValidator();
    }

    @Test(expected = InvalidParameterException.class)
    public void validateEquationStructure_NullReference_Illegal() throws Exception
    {
        validator.validateEquationStructure(null);
    }

    @Test(expected = InvalidEquationException.class)
    public void validateEquationStructure_EmptyEquation_Illegal() throws Exception
    {
        validator.validateEquationStructure("");
    }

    @Test(expected = InvalidEquationException.class)
    public void validateEquationStructure_BlankEquation_Illegal() throws Exception
    {
        validator.validateEquationStructure("       ");
    }

    @Test(expected = InvalidEquationException.class  )
    public void validateEquation_EquationWithAnOperatorOnly_Illegal() throws Exception
    {
        validator.validateEquationStructure("+");
    }

    @Test
    public void validateEquationStructure_EquationWithANumberOnly_Legal() throws Exception
    {
        validator.validateEquationStructure("10.02");
    }

    @Test(expected = InvalidEquationException.class)
    public void validateEquationStructure_SequentialNumbers_Illegal() throws Exception
    {
        validator.validateEquationStructure("1 2");
    }

    @Test(expected = InvalidEquationException.class)
    public void validateEquationStructure_SequentialOperators_Illegal() throws Exception
    {
        validator.validateEquationStructure("+*");
    }

    @Test(expected = InvalidEquationException.class)
    public void validateEquationStructure_EquationEndingWithOperator_Illegal() throws Exception
    {
        validator.validateEquationStructure("10 +");
    }

    @Test(expected = InvalidEquationException.class)
    public void validateEquationStructure_EquationBeginningWIthOperator_Illegal() throws Exception
    {
        validator.validateEquationStructure("+   10");
    }

    @Test(expected = InvalidEquationException.class)
    public void validateEquation_EquationWithEmptyBracket_Illegal() throws Exception
    {
        validator.validateEquationStructure("1 +()");
    }

    @Test(expected = InvalidEquationException.class)
    public void validateEquation_EquationWithMissingBracket_Illegal() throws Exception
    {
        validator.validateEquationStructure("(1 + 2");
    }

    @Test(expected = InvalidEquationException.class)
    public void validateEquation_EquationWithOperatorBeforeClosingBracket_Illegal() throws Exception
    {
        validator.validateEquationStructure("( 1 + )");
    }

    @Test(expected = InvalidEquationException.class)
    public void validateEquation_EquationWithMissingOperatorBetweenNumberAndBracket_Illegal() throws Exception
    {
        validator.validateEquationStructure("-1 ( 2 )");
    }

    @Test(expected = InvalidEquationException.class)
    public void validateEquation_EquationWithMissingOperatorBetweenClosingAndOpeningBracket_Illegal() throws Exception
    {
        validator.validateEquationStructure("( 1 ) ( 1 )");
    }

    @Test
    public void validateEquation_EquationWithMultipleLayersOfBrackets_Legal() throws Exception
    {
        validator.validateEquationStructure("( ( 1 + 2 ) )");
    }

    @Test(expected = InvalidEquationException.class)
    public void validateEquation_EquationWithMisplacedBrackets_Illegal() throws Exception
    {
        validator.validateEquationStructure("1 ) * 2 ( ");
    }


}