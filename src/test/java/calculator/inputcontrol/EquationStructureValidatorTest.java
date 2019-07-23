package calculator.inputcontrol;

import calculator.exceptions.InvalidEquationException;
import calculator.exceptions.InvalidParameterException;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class EquationStructureValidatorTest {

    private EquationStructureValidator validator;

    @Before
    public void setUp()
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
        List<String> input = new LinkedList<>();
        validator.validateEquationStructure(input);
    }

    @Test
    public void validateEquationStructure_EquationWithANumberOnly_Legal() throws Exception
    {
        List<String> input = new LinkedList<>();
        input.add("10.02");
        validator.validateEquationStructure(input);
    }

    @Test(expected = InvalidEquationException.class)
    public void validateEquationStructure_SequentialNumbers_Illegal() throws Exception
    {
        List<String> input = Arrays.asList("1","2");
        validator.validateEquationStructure(input);
    }

    @Test(expected = InvalidEquationException.class)
    public void validateEquationStructure_SequentialOperators_Illegal() throws Exception
    {
        List<String> input=Arrays.asList("+","*");
        validator.validateEquationStructure(input);
    }

    @Test(expected = InvalidEquationException.class)
    public void validateEquationStructure_EquationEndingWithOperator_Illegal() throws Exception
    {
        List<String> input=Arrays.asList("10","+");
        validator.validateEquationStructure(input);
    }

    @Test(expected = InvalidEquationException.class)
    public void validateEquationStructure_EquationBeginningWIthOperator_Illegal() throws Exception
    {
        List<String> input=Arrays.asList("+","10");
        validator.validateEquationStructure(input);
    }

    @Test(expected = InvalidEquationException.class)
    public void validateEquation_EquationWithEmptyBracket_Illegal() throws Exception
    {
        List<String> input=Arrays.asList("1","+","(",")");
        validator.validateEquationStructure(input);
    }

    @Test(expected = InvalidEquationException.class)
    public void validateEquation_EquationWithMisplacedBracket_Illegal() throws Exception
    {
        List<String> input=Arrays.asList("1","+",")","(");
        validator.validateEquationStructure(input);
    }

    @Test(expected = InvalidEquationException.class)
    public void validateEquation_EquationWithMissingBracket() throws Exception
    {
        List<String> input=Arrays.asList("(","1","+","2");
        validator.validateEquationStructure(input);
    }

   /* @Test(expected = InvalidEquationException.class)
    public void validateEquationStructure_EquationWithAnOperatorOnly_Illegal() throws Exception
    {
        List<String> input=new LinkedList<>();
        input.add("+");
        validator.validateEquationStructure(input);
    }

    @Test(expected = InvalidEquationException.class)
    public void validateEquationStructure_NumberAndOperator_Illegal() throws Exception
    {
        List<String> input = Arrays.asList("2","+");
        validator.validateEquationStructure(input);
    }*/
    /*
 //   private EquationStructureValidator validator;

    @Before
    public void setUp(){
      validator=new EquationStructureValidator();
    }

    @Test(expected = NullPointerException.class)
    public void validateEquation_NullReference_Illegal() throws Exception
    {
        validator.validateEquationStructure(null);
    }

    @Test(expected = InvalidEquationException.class)
    public void validateEquationStructure_EquationWithMissingOperatorBetweenNumberAndBracket_Illegal() throws Exception {

        List<String> input=new LinkedList<>(Arrays.asList("2","(","-5",")"));
        input.add("2");
        input.add("(");
        input.add("-5");
        input.add(")");

        validator.validateEquationStructure(input);
    }

    @Test(expected = InvalidEquationException.class)
    public void validateEquationStructure_EquationWithClosingBracketNextToOpeningBracket_Legal() throws Exception
    {
        List<String> input=Arrays.asList("(","(","10","+","20",")","(","-5",")",")");
        validator.validateEquationStructure(input);
    }

    @Test
    public void validateEquationStructure_EquationWithOnlyANumber_Legal() throws Exception
    {
        List<String> input=new LinkedList<>();
        input.add("1");
        validator.validateEquationStructure(input);
    }

    @Test
    public void validateEquationStructure_SimpleEquation_Illegal() throws Exception {
        List<String> input=Arrays.asList("(","(","-1","+","5","+","2",")",")");
        validator.validateEquationStructure(input);
    }

    @Test(expected = InvalidEquationException.class)
    public void validateEquationStructure_EquationOnlyWithOperatorInTheBrackets_Illegal() throws Exception
    {
        List<String> input=Arrays.asList("(","+",")","+","1");
        validator.validateEquationStructure(input);
    }

    @Test(expected = InvalidEquationException.class)
    public void validateEquationStructure_EquationWithSequentialOperators_Illegal() throws Exception
    {
        List<String> input=Arrays.asList("2","+","+","1");
        validator.validateEquationStructure(input);
    }

    @Test(expected = InvalidEquationException.class)
    public void validateEquationStructure_EquationWithEmptyBrackets_Illegal() throws Exception
    {
        List<String> input=Arrays.asList("(",")","+","2");
        validator.validateEquationStructure(input);
    }

    @Test(expected = InvalidEquationException.class)
    public void validateEquationStructure_EmptyEquation_Illegal() throws Exception
    {
        List<String> input=new LinkedList<>();
        input.add("");
        validator.validateEquationStructure(input);
    }

    @Test(expected = InvalidEquationException.class)
    public void validateEquationStructure_EquationWithMissingBracket_Illegal() throws Exception
    {
        List<String> input=Arrays.asList("(","(","2.0","+","1",")");
        validator.validateEquationStructure(input);
    }

    @Test(expected = InvalidEquationException.class)
    public void validateEquationStructure_OperatorAfterOpeningBracket_Illegal() throws Exception
    {
        List<String> input=Arrays.asList("(","+","1",")");
        validator.validateEquationStructure(input);
    }

    @Test(expected = InvalidEquationException.class)
    public void validateEquationStructure_OperatorBeforeClosingBracket_Illegal() throws Exception
    {
        List<String> input=Arrays.asList("(","1","+",")");
        validator.validateEquationStructure(input);
    }

    @Test(expected = InvalidEquationException.class)
    public void validateEquationStrucure_EquationEndingWithOperator_Illegal() throws Exception
    {
        List<String> input=Arrays.asList("1","+");
        validator.validateEquationStructure(input);
    }*/

}