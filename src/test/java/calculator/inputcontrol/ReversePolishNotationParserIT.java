package calculator.inputcontrol;

import calculator.computation.EquationComponentFactory;
import calculator.exceptions.InvalidComponentException;
import org.junit.Before;
import org.junit.Test;


import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class ReversePolishNotationParserIT {

    private ReversePolishNotationParser parserRPN;


    @Before
    public void setUp()
    {
        ComponentValidator validator = new ComponentValidator();
        EquationComponentFactory operatorFactory = new EquationComponentFactory();
        parserRPN=new ReversePolishNotationParser(validator, operatorFactory);
    }



    @Test
    public void formatToReversedPolishNotation_EquationWithoutBrackets_RPNFormat() throws InvalidComponentException {
        List<String> input=new LinkedList<>(Arrays.asList("15","+","10","*","2"));
        List<String> expected=new LinkedList<>(Arrays.asList("15","10","2","*","+"));
        List<String> realResult=parserRPN.formatFromInfixToReversedPolishNotation(input);
        assertEquals(expected,realResult);
    }

    @Test
    public void formatToReversedPolishNotation_EquationWithEqualPriorityLeftAssociativeOperators_RPNFormat() throws InvalidComponentException
    {
        List<String> input=new LinkedList<>(Arrays.asList("1","/","2","*","3"));
        List<String> expected=new LinkedList<>(Arrays.asList("1","2","/","3","*"));
        List<String> realResult = parserRPN.formatFromInfixToReversedPolishNotation(input);
        assertEquals(expected,realResult);
    }

    @Test
    public void formatToReversedPolishNotation_EquationWithBrackets_RPNFormat() throws InvalidComponentException
    {
        List<String> input=new LinkedList<>(Arrays.asList("(","15","+","10",")","*","2"));
        List<String> expected=new LinkedList<>(Arrays.asList("15","10","+","2","*"));
        List<String> realResult=parserRPN.formatFromInfixToReversedPolishNotation(input);
        assertEquals(expected,realResult);
    }

    @Test
    public void formatToReversedPolishNotation_EquationWithMultipleNestedBrackets_RPNFormat() throws InvalidComponentException
    {
        List<String> input=new LinkedList<>(Arrays.asList("(","(","(","15","+","10","*","2",")",")",")"));
        List<String> expected=new LinkedList<>(Arrays.asList("15","10","2","*","+"));
        List<String> realResult=parserRPN.formatFromInfixToReversedPolishNotation(input);
        assertEquals(expected,realResult);
    }

    @Test
    public void formatToReversedPolishNotation_EquationProductOfTwoExpressionsEachInBracket_RPNFormat() throws InvalidComponentException
    {
        List<String> input=new LinkedList<>(Arrays.asList("(","1","+","2",")","*","(","3","+","4",")"));
        List<String> expected=new LinkedList<>(Arrays.asList("1","2","+","3","4","+","*"));
        List<String> realResult=parserRPN.formatFromInfixToReversedPolishNotation(input);
        assertEquals(expected,realResult);
    }

    @Test
    public void formatToReversedPolishNotation_EquationWithLeftAndRightAssociativeOperators_RPNFormat() throws InvalidComponentException
    {
        List<String> input=new LinkedList<>(Arrays.asList("2","/","2","^","2"));
        List<String> expected=new LinkedList<>(Arrays.asList("2","2","2","^","/"));
        List<String> result=parserRPN.formatFromInfixToReversedPolishNotation(input);
        assertEquals(expected,result);
    }



}