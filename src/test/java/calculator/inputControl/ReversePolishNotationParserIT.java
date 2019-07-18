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

        String realResult=parserRPN.formatFromInfixToReversedPolishNotation("15 + 10 * 2");
        assertEquals("15 10 2 * +",realResult);
    }

    @Test
    public void formatToReversedPolishNotation_EquationWithEqualPriorityLeftAssociativeOperators_RPNFormat()  throws EmptyStackException, InvalidOperatorException
    {
        String realResult=parserRPN.formatFromInfixToReversedPolishNotation("1 / 2 * 3");
        assertEquals("1 2 / 3 *",realResult);
    }

    @Test
    public void formatToReversedPolishNotation_EquationWithBrackets_RPNFormat() throws EmptyStackException, InvalidOperatorException
    {
        String realResult=parserRPN.formatFromInfixToReversedPolishNotation("( 15 + 10 ) * 2");
        assertEquals("15 10 + 2 *",realResult);
    }

    @Test
    public void formatToReversedPolishNotation_EquationWithMultipleNestedBrackets_RPNFormat() throws EmptyStackException, InvalidOperatorException
    {
        String realResult=parserRPN.formatFromInfixToReversedPolishNotation("( ( ( 15 + 10 * 2 ) ) )");
        assertEquals("15 10 2 * +",realResult);
    }

    @Test
    public void formatToReversedPolishNotation_EquationProductOfTwoExpressionsEachInBracket_RPNFormat()  throws EmptyStackException, InvalidOperatorException
    {
        String realResult=parserRPN.formatFromInfixToReversedPolishNotation("( 1 + 2 ) * ( 3 + 4 )");
        assertEquals("1 2 + 3 4 + *",realResult);
    }

    @Test
    public void formatToReversedPolishNotation_EquationWithLeftAndRightAssociativeOperators_RPNFormat() throws EmptyStackException, InvalidOperatorException
    {
        String result=parserRPN.formatFromInfixToReversedPolishNotation("3 + 4 * 2 / 2 ^ ( 1 - 5 ) ^ 3");
        assertEquals("3 4 2 * 2 1 5 - 3 ^ ^ / +",result);
    }



}