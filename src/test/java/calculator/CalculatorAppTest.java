package calculator;

import calculator.exceptions.EmptyEquationException;
import calculator.exceptions.InvalidTypeOfEquationComponent;
import calculator.inputControl.EquationValidator;
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

    @Before
    public void setUp()
    {
        app=new CalculatorApp(validator);
    }

    @Test(expected = EmptyEquationException.class)
    public void testProcessEquationAndCalculateResult() throws Exception{
        app.calculateResult("");
    }

    @Test
    public void calculateResult_NonEmptyEquation_ReturnZero() throws  Exception
    {
        double result=app.calculateResult("1");
        assertEquals(0.0,result);
        Mockito.verify(validator).validateEquation("1");
    }

    @Test(expected = InvalidTypeOfEquationComponent.class)
    public void calculateResult_InvalidEquation_InvalidTypeOfEquationComponentThrown() throws Exception
    {
        Mockito.doThrow(new InvalidTypeOfEquationComponent("Invalid component")).when(validator).validateEquation("#");
       app.calculateResult("#");

    }

}
