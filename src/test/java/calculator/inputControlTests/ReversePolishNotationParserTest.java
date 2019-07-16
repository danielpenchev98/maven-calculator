package calculator.inputControlTests;

import calculator.computation.MathOperator;
import calculator.computation.MathOperatorFactory;
import calculator.exceptions.InvalidOperatorException;
import calculator.inputControl.EquationValidator;
import calculator.inputControl.ReversePolishNotationParser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.EmptyStackException;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;

@RunWith(MockitoJUnitRunner.class)
public class ReversePolishNotationParserTest {

   @InjectMocks
    private ReversePolishNotationParser parserRPN;

    @Mock
    private EquationValidator validator;

    @Mock
    private MathOperatorFactory operatorFactory;

    @Mock
    private MathOperator operator;

    @Before
    public void setUp() throws InvalidOperatorException
    {
        Mockito.when(operatorFactory.createOperation(anyString())).thenReturn(operator);
        Mockito.when(validator.isValidNumber("15")).thenReturn(true);
        Mockito.when(validator.isValidNumber("10")).thenReturn(true);
        Mockito.when(validator.isValidNumber("2")).thenReturn(true);

    }

    @Test
    public void formatToReversedPolishNotation_EquationWithoutBrackets_RPNFormat() throws EmptyStackException, InvalidOperatorException {

        String realResult=parserRPN.formatFromInfixToReversedPolishNotation("15 + 10 * 2");
        assertEquals("15 10 2 * +",realResult);
    }

    @Test
    public void formatToReversedPolishNotation_EquationWithEqualPriorityLeftAssociativeOperators_RPNFormat()  throws EmptyStackException, InvalidOperatorException
    {
        String realResult=parserRPN.formatFromInfixToReversedPolishNotation("10 / 2 * 15");
        assertEquals("10 2 / 15 *",realResult);
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
        String realResult=parserRPN.formatFromInfixToReversedPolishNotation("( 10 + 2 ) * ( 15 )");
        assertEquals("10 2 + 15 *",realResult);
    }


    @Test
    public void formatToReversedPolishNotation_EquationWithLeftAndRightAssociativeOperators_RPNFormat() throws EmptyStackException, InvalidOperatorException
    {
        String result=parserRPN.formatFromInfixToReversedPolishNotation("2 / 2 ^ 10");
                assertEquals("2 2 10 ^ /",result);
    }

}