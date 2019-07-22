package calculator.inputcontrol;

import calculator.computation.*;
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
        parserRPN=new ReversePolishNotationParser();
    }



    @Test
    public void formatToReversedPolishNotation_EquationWithoutBrackets_RPNFormat() throws InvalidComponentException {
        List<EquationComponent> input=new LinkedList<>();
        input.add(new NumberComponent("15"));
        input.add(new Addition());
        input.add(new NumberComponent("10"));
        input.add(new Multiplication());
        input.add(new NumberComponent("2"));

        List<EquationComponent> expected=new LinkedList<>();
        expected.add(new NumberComponent("15"));
        expected.add(new NumberComponent("10"));
        expected.add(new NumberComponent("2"));
        expected.add(new Multiplication());
        expected.add(new Addition());

        List<EquationComponent> realResult=parserRPN.formatFromInfixToReversedPolishNotation(input);
        assertEquals(expected,realResult);
    }

    @Test
    public void formatToReversedPolishNotation_EquationWithEqualPriorityLeftAssociativeOperators_RPNFormat() throws InvalidComponentException
    {
        List<EquationComponent> input=new LinkedList<>();
        input.add(new NumberComponent("15"));
        input.add(new Division());
        input.add(new NumberComponent("2"));
        input.add(new Multiplication());
        input.add(new NumberComponent("10"));

        List<EquationComponent> expected=new LinkedList<>();
        expected.add(new NumberComponent("15"));
        expected.add(new NumberComponent("2"));
        expected.add(new Division());
        expected.add(new NumberComponent("10"));
        expected.add(new Multiplication());

        List<EquationComponent> realResult = parserRPN.formatFromInfixToReversedPolishNotation(input);
        assertEquals(expected,realResult);
    }

    @Test
    public void formatToReversedPolishNotation_EquationWithBrackets_RPNFormat() throws InvalidComponentException
    {
        List<EquationComponent> input=new LinkedList<>();
        input.add(new OpeningBracket());
        input.add(new NumberComponent("15"));
        input.add(new Addition());
        input.add(new NumberComponent("10"));
        input.add(new ClosingBracket());
        input.add(new Multiplication());
        input.add(new NumberComponent("2"));

        List<EquationComponent> expected=new LinkedList<>();
        expected.add(new NumberComponent("15"));
        expected.add(new NumberComponent("10"));
        expected.add(new Addition());
        expected.add(new NumberComponent("2"));
        expected.add(new Multiplication());

        List<EquationComponent> realResult=parserRPN.formatFromInfixToReversedPolishNotation(input);
        assertEquals(expected,realResult);
    }

    @Test
    public void formatToReversedPolishNotation_EquationWithMultipleNestedBrackets_RPNFormat() throws InvalidComponentException
    {
        List<EquationComponent> input=new LinkedList<>();
        input.add(new OpeningBracket());
        input.add(new OpeningBracket());
        input.add(new OpeningBracket());
        input.add(new NumberComponent("15"));
        input.add(new Addition());
        input.add(new NumberComponent("10"));
        input.add(new Multiplication());
        input.add(new NumberComponent("2"));
        input.add(new ClosingBracket());
        input.add(new ClosingBracket());
        input.add(new ClosingBracket());

        List<EquationComponent> expected=new LinkedList<>();
        expected.add(new NumberComponent("15"));
        expected.add(new NumberComponent("10"));
        expected.add(new NumberComponent("2"));
        expected.add(new Multiplication());
        expected.add(new Addition());

        List<EquationComponent> realResult=parserRPN.formatFromInfixToReversedPolishNotation(input);
        assertEquals(expected,realResult);
    }

    @Test
    public void formatToReversedPolishNotation_EquationProductOfTwoExpressionsEachInBracket_RPNFormat() throws InvalidComponentException
    {
        List<EquationComponent> input=new LinkedList<>();
        input.add(new OpeningBracket());
        input.add(new NumberComponent("10"));
        input.add(new Addition());
        input.add(new NumberComponent("15"));
        input.add(new ClosingBracket());
        input.add(new Multiplication());
        input.add(new OpeningBracket());
        input.add(new NumberComponent("2"));
        input.add(new Addition());
        input.add(new NumberComponent("10"));
        input.add(new ClosingBracket());

        List<EquationComponent> expected=new LinkedList<>();
        expected.add(new NumberComponent("10"));
        expected.add(new NumberComponent("15"));
        expected.add(new Addition());
        expected.add(new NumberComponent("2"));
        expected.add(new NumberComponent("10"));
        expected.add(new Addition());
        expected.add(new Multiplication());

        List<EquationComponent> realResult=parserRPN.formatFromInfixToReversedPolishNotation(input);
        assertEquals(expected,realResult);
    }

    @Test
    public void formatToReversedPolishNotation_EquationWithLeftAndRightAssociativeOperators_RPNFormat() throws InvalidComponentException
    {
        List<EquationComponent> input=new LinkedList<>();
        NumberComponent number = new NumberComponent("2");
        input.add(number);
        input.add(new Division());
        input.add(number);
        input.add(new Power());
        input.add(number);

        List<EquationComponent> expected=new LinkedList<>();
        expected.add(number);
        expected.add(number);
        expected.add(new Power());
        expected.add(new Division());

        List<EquationComponent> result=parserRPN.formatFromInfixToReversedPolishNotation(input);
        assertEquals(expected,result);
    }



}