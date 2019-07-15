package calculator.inputControlTests;


import calculator.computation.MathOperatorFactory;
import calculator.exceptions.InvalidOperatorException;
import calculator.exceptions.OutOfItemsException;
import calculator.inputControl.EquationValidator;
import calculator.inputControl.ReversePolishNotationParser;
import org.junit.Before;
import org.junit.Test;
import java.util.EmptyStackException;

import static org.junit.Assert.assertEquals;

public class ReversePolishNotationParserTest {

   //@InjectMocks
    private ReversePolishNotationParser parserRPN;

    //@Mock
    private EquationValidator validator;

    private MathOperatorFactory operatorFactory;

    @Before
    public void setUp()
    {
        validator=new EquationValidator();
        operatorFactory=new MathOperatorFactory();
        parserRPN=new ReversePolishNotationParser(validator,operatorFactory);
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