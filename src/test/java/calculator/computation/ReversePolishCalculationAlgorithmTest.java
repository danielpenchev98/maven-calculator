package calculator.computation;

import calculator.exceptions.InvalidComponentException;
import calculator.exceptions.InvalidEquationException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;


import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.LinkedList;
import java.util.List;
import static junit.framework.TestCase.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class ReversePolishCalculationAlgorithmTest {

    private ReversePolishCalculationAlgorithm algorithm;

    @Mock
    NumberComponent firstNumber;

    @Mock
    NumberComponent secondNumber;

    @Mock
    Addition addition;

    @Mock
    Power power;



    @Before
    public void setUp() {
        algorithm=new ReversePolishCalculationAlgorithm();
    }

    @Test(expected = EmptyStackException.class)
    public void calculationEquation_EquationWithMissingNumber_OutOfItemsExceptionThrown() throws Exception
    {
        Mockito.when(firstNumber.getValue()).thenReturn("2.0");
        Mockito.when(secondNumber.getValue()).thenReturn("3.0");

        Mockito.when(addition.compute(2.0,3.0)).thenReturn(5.0);

        List<EquationComponent> input=new LinkedList<>(Arrays.asList(firstNumber,secondNumber,addition,addition));
        algorithm.calculateEquation(input);
    }

    @Test(expected = InvalidEquationException.class)
    public void calculationEquation_EquationWithMissingOperator_MissingOperatorExceptionThrown() throws Exception
    {
        Mockito.when(firstNumber.getValue()).thenReturn("2.0");
        Mockito.when(addition.compute(2.0,2.0)).thenReturn(4.0);

        List<EquationComponent> input=new LinkedList<>(Arrays.asList(firstNumber,firstNumber,addition,secondNumber));

        algorithm.calculateEquation(input);
    }

    @Test(expected = InvalidComponentException.class)
    public void calculationEquation_EquationWithInvalidOperator_InvalidOperatorException() throws Exception
    {

        List<EquationComponent> input=new LinkedList<>(Arrays.asList(firstNumber,secondNumber,new ClosingBracket()));

        algorithm.calculateEquation(input);
    }

    @Test
    public void calculationEquation_LegalEquation() throws Exception
    {
        Mockito.when(secondNumber.getValue()).thenReturn("3.0");
        Mockito.when(power.compute(3.0,3.0)).thenReturn(27.0);
        Mockito.when(addition.compute(3.0,27.0)).thenReturn(30.0);

        List<EquationComponent> input=new LinkedList<>(Arrays.asList(secondNumber,secondNumber,secondNumber,power,addition));

        assertEquals(30,algorithm.calculateEquation(input),0.001);
    }
}