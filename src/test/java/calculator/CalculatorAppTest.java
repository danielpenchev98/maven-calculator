package calculator;

import calculator.exceptions.InvalidEquationException;
import calculator.exceptions.InvalidOperatorException;
import calculator.exceptions.InvalidComponentException;
import calculator.inputControl.EquationValidator;
import calculator.inputControl.InputFormatter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static junit.framework.TestCase.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class CalculatorAppTest {

    private CalculatorApp app;

    @Mock
    private EquationValidator validator;

    @Mock
    private InputFormatter formatter;

    @Before
    public void setUp()
    {
        app=new CalculatorApp(formatter,validator);
    }

    @Test(expected = InvalidEquationException.class)
    public void testProcessEquationAndCalculateResult() throws Exception{
        app.calculateResult("");
    }

    @Test
    public void calculateResult_NonEmptyEquation_ReturnZero() throws  Exception
    {
        String[] formattedInput={"1"};
        Mockito.when(formatter.doFormat("1")).thenReturn(formattedInput);

        double result=app.calculateResult("1");
        assertEquals(0.0,result);
        Mockito.verify(validator).validateEquation(formattedInput);
    }

    @Test(expected = InvalidComponentException.class)
    public void calculateResult_EquationUsingNonSupportedOperator_Illegal() throws Exception {
        String[] formattedInput={"#"};
        Mockito.when(formatter.doFormat("#")).thenReturn(formattedInput);
        Mockito.doThrow(new InvalidComponentException("Invalid component")).when(validator).validateEquation(formattedInput);
        app.calculateResult("#");
    }

    @Test(expected = InvalidEquationException.class)
    public void calculateResult_EquationWithMissingBracket_Illegal() throws Exception
    {
        String[] formattedInput={"(","1","+","2",};
        Mockito.when(formatter.doFormat("( 1 + 2")).thenReturn(formattedInput);
        Mockito.doThrow(new InvalidEquationException("Missing Bracket")).when(validator).validateEquation(formattedInput);
        app.calculateResult("( 1 + 2");
    }

    @Test(expected = InvalidOperatorException.class)
    public void calculateResult_InfixNotationEquation_RPNEquation() throws Exception
    {
        String[] formattedInput={"1","+","2"};
        Mockito.when(formatter.doFormat("1+2")).thenReturn(formattedInput);


        Mockito.verify(validator).validateEquation(formattedInput);
    }

}
