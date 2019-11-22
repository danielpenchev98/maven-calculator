package com.calculator.core.inputformatting;

import com.calculator.core.exceptions.InvalidExpressionException;
import com.calculator.core.exceptions.InvalidParameterException;
import org.junit.BeforeClass;
import org.junit.Test;

public class ExpressionStructureValidatorTest {

    private static ExpressionStructureValidator validator;

    @BeforeClass
    public static void setUp() {
        validator = new ExpressionStructureValidator();
    }

    @Test(expected = InvalidParameterException.class)
    public void validateExpressionStructure_NullReference_Illegal() throws Exception {
        validator.validateExpressionStructure(null);
    }

    @Test(expected = InvalidExpressionException.class)
    public void validateExpressionStructure_EmptyExpression_Illegal() throws Exception {
        validator.validateExpressionStructure("");
    }

    @Test(expected = InvalidExpressionException.class)
    public void validateExpressionStructure_BlankExpression_Illegal() throws Exception {
        validator.validateExpressionStructure("       ");
    }

    @Test(expected = InvalidExpressionException.class)
    public void validateExpression_ExpressionWithAnOperatorOnly_Illegal() throws Exception {
        validator.validateExpressionStructure("+");
    }

    @Test
    public void validateExpressionStructure_ExpressionWithANumberOnly_Legal() throws Exception {
        validator.validateExpressionStructure("10.02");
    }

    @Test(expected = InvalidExpressionException.class)
    public void validateExpressionStructure_SequentialNumbers_Illegal() throws Exception {
        validator.validateExpressionStructure("1 2");
    }

    @Test(expected = InvalidExpressionException.class)
    public void validateExpressionStructure_SequentialOperators_Illegal() throws Exception {
        validator.validateExpressionStructure("+*");
    }

    @Test(expected = InvalidExpressionException.class)
    public void validateExpressionStructure_ExpressionEndingWithOperator_Illegal() throws Exception {
        validator.validateExpressionStructure("10 +");
    }

    @Test(expected = InvalidExpressionException.class)
    public void validateExpressionStructure_ExpressionBeginningWIthOperator_Illegal() throws Exception {
        validator.validateExpressionStructure("+   10");
    }

    @Test(expected = InvalidExpressionException.class)
    public void validateExpression_ExpressionWithEmptyBracket_Illegal() throws Exception {
        validator.validateExpressionStructure("1 +()");
    }

    @Test(expected = InvalidExpressionException.class)
    public void validateExpression_ExpressionWithMissingBracket_Illegal() throws Exception {
        validator.validateExpressionStructure("(1 + 2");
    }

    @Test(expected = InvalidExpressionException.class)
    public void validateExpression_ExpressionWithOperatorBeforeClosingBracket_Illegal() throws Exception {
        validator.validateExpressionStructure("( 1 + )");
    }

    @Test(expected = InvalidExpressionException.class)
    public void validateExpression_ExpressionWithMissingOperatorBetweenNumberAndBracket_Illegal() throws Exception {
        validator.validateExpressionStructure("-1 ( 2 )");
    }

    @Test(expected = InvalidExpressionException.class)
    public void validateExpression_ExpressionWithMissingOperatorBetweenClosingAndOpeningBracket_Illegal() throws Exception {
        validator.validateExpressionStructure("( 1 ) ( 1 )");
    }

    @Test
    public void validateExpression_ExpressionWithMultipleLayersOfBrackets_Legal() throws Exception {
        validator.validateExpressionStructure("( ( 1 + 2 ) )");
    }

    @Test(expected = InvalidExpressionException.class)
    public void validateExpression_ExpressionWithMisplacedBrackets_Illegal() throws Exception {
        validator.validateExpressionStructure("1 ) * 2 ( ");
    }

}