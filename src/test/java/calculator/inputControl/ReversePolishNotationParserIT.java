package calculator.inputControl;

import calculator.computation.MathArithmeticOperatorFactory;
import calculator.exceptions.InvalidOperatorException;
import calculator.inputControl.parserTriggers.ReversePolishComponentTriggerFactory;
import org.junit.Before;
import org.junit.Test;

import java.util.EmptyStackException;

import static org.junit.Assert.assertEquals;


public class ReversePolishNotationParserIT {

    private ReversePolishNotationParser parserRPN;

    @Before
    public void setUp()
    {
        EquationValidator validator = new EquationValidator();
        MathArithmeticOperatorFactory arithmeticOperatorFactory = new MathArithmeticOperatorFactory();
        ReversePolishComponentTriggerFactory operatorFactory = new ReversePolishComponentTriggerFactory(validator, arithmeticOperatorFactory);
        parserRPN=new ReversePolishNotationParser(operatorFactory);
    }

    @Test
    public void formatToReversedPolishNotation_EquationWithoutBrackets_RPNFormat() throws EmptyStackException, InvalidOperatorException {
        String[] formattedInput={"15","+","10","*","2"};
        String realResult=parserRPN.formatFromInfixToReversedPolishNotation(formattedInput);
        assertEquals("15 10 2 * +",realResult);
    }

    @Test
    public void formatToReversedPolishNotation_EquationWithEqualPriorityLeftAssociativeOperators_RPNFormat()  throws EmptyStackException, InvalidOperatorException
    {
        String[] formattedInput={"1","/","2","*","3"};
        String realResult=parserRPN.formatFromInfixToReversedPolishNotation(formattedInput);
        assertEquals("1 2 / 3 *",realResult);
    }

    @Test
    public void formatToReversedPolishNotation_EquationWithBrackets_RPNFormat() throws EmptyStackException, InvalidOperatorException
    {
        String[] formattedInput={"(","15","+","10",")","*","2"};
        String realResult=parserRPN.formatFromInfixToReversedPolishNotation(formattedInput);
        assertEquals("15 10 + 2 *",realResult);
    }

    @Test
    public void formatToReversedPolishNotation_EquationWithMultipleNestedBrackets_RPNFormat() throws EmptyStackException, InvalidOperatorException
    {
        String[] formattedInput={"(","(","(","15","+","10","*","2",")",")",")"};
        String realResult=parserRPN.formatFromInfixToReversedPolishNotation(formattedInput);
        assertEquals("15 10 2 * +",realResult);
    }

    @Test
    public void formatToReversedPolishNotation_EquationProductOfTwoExpressionsEachInBracket_RPNFormat()  throws EmptyStackException, InvalidOperatorException
    {
        String[] formattedInput={"(","1","+","2",")","*","(","3","+","4",")"};
        String realResult=parserRPN.formatFromInfixToReversedPolishNotation(formattedInput);
        assertEquals("1 2 + 3 4 + *",realResult);
    }

    @Test
    public void formatToReversedPolishNotation_EquationWithLeftAndRightAssociativeOperators_RPNFormat() throws EmptyStackException, InvalidOperatorException
    {
        String[] formattedInput={"2","/","2","^","2"};
        String result=parserRPN.formatFromInfixToReversedPolishNotation(formattedInput);
        assertEquals("2 2 2 ^ /",result);
    }



}