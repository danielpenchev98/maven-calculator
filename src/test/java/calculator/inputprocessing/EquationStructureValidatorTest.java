package calculator.inputprocessing;

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

    @Test(expected = InvalidEquationException.class  )
    public void validateEquation_EquationWithAnOperatorOnly_Illegal() throws Exception
    {
        List<String> input =new LinkedList<>();
        input.add("+");
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
        List<String> input=Arrays.asList("(","1",")","(");
        validator.validateEquationStructure(input);
    }

    @Test(expected = InvalidEquationException.class)
    public void validateEquation_EquationWithMissingBracket_Illegal() throws Exception
    {
        List<String> input=Arrays.asList("(","1","+","2");
        validator.validateEquationStructure(input);
    }

    @Test(expected = InvalidEquationException.class)
    public void validateEquation_EquationWithOperatorBeforeClosingBracket_Illegal() throws Exception
    {
        List<String> input=Arrays.asList("(","1","+",")");
        validator.validateEquationStructure(input);
    }

    @Test(expected = InvalidEquationException.class)
    public void validateEquation_EquationWithMissingOperatorBetweenNumberAndBracket_Illegal() throws Exception
    {
        List<String> input=Arrays.asList("1","(","2",")");
        validator.validateEquationStructure(input);
    }

    @Test(expected = InvalidEquationException.class)
    public void validateEquation_EquationWithMissingOperatorBetweenClosingAndOpeningBracket_Illegal() throws Exception
    {
        List<String> input=Arrays.asList("(","1",")","(","1",")");
        validator.validateEquationStructure(input);

    }

    @Test
    public void validateEquation_EquationWithMultipleLayersOfBrackets_Legal() throws Exception
    {
        List<String> input=Arrays.asList("(","(","1","+","2",")",")");
        validator.validateEquationStructure(input);
    }

    @Test(expected = InvalidEquationException.class)
    public void validateEquation_EquationWithMisplacedBrackets_Illegal() throws Exception
    {
        List<String> input=Arrays.asList("1",")","*","2","(");
        validator.validateEquationStructure(input);
    }



}