package calculator.computationTests;

import calculator.computation.ComputationalMachine;
import calculator.computation.ReversePolishCalculationAlgorithm;

import calculator.exceptions.InvalidOperatorException;
import calculator.exceptions.MissingOperatorException;
import calculator.inputControl.EquationValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;


import java.util.EmptyStackException;

import static org.junit.Assert.*;


@RunWith(MockitoJUnitRunner.class)
public class ReversePolishCalculationAlgorithmTest {

    @InjectMocks
    private ReversePolishCalculationAlgorithm algorithm;

    @Mock
    public ComputationalMachine machine;
    @Mock
    public EquationValidator validator;


    private final double DELTA=0.0001;

    @Before
    public void setUp()
    {
        Mockito.when(validator.isValidNumber("2.0")).thenReturn(true);
        Mockito.when(validator.isValidNumber("3.0")).thenReturn(true);
        Mockito.when(validator.isValidNumber("+")).thenReturn(false);
    }


    @Test(expected = EmptyStackException.class)
    public void calculationEquation_EquationWithMissingNumber_OutOfItemsExceptionThrown() throws Exception
    {
        Mockito.when(machine.computeAction("+",2.0,3)).thenReturn(5.0);
        algorithm.calculateEquation("2.0 3.0 + +");
    }


    @Test(expected = MissingOperatorException.class)
    public void calculationEquation_EquationWithMissingOperator_MissingOperatorExceptionThrown() throws Exception
    {
        Mockito.when(machine.computeAction("+",2.0,2.0)).thenReturn(4.0);
        algorithm.calculateEquation("2.0 2.0 + 3.0");
    }

    @Test(expected = InvalidOperatorException.class)
    public void calculationEquation_EquationWithInvalidOperator_InvalidOperatorException() throws Exception
    {
        Mockito.when(validator.isValidNumber("#")).thenReturn(false);
        Mockito.when(machine.computeAction("#",2.0,3.0)).thenThrow(new InvalidOperatorException("Invalid operator"));

        algorithm.calculateEquation("2.0 3.0 #");
    }

    @Test
    public void calculationEquation_LegalEquation() throws Exception
    {
        Mockito.when(machine.computeAction("^",3.0,3.0)).thenReturn(27.0);
        Mockito.when(machine.computeAction("+",3.0,27.0)).thenReturn(30.0);

        Mockito.when(validator.isValidNumber("^")).thenReturn(false);

        assertEquals(30,algorithm.calculateEquation("3.0 3.0 3.0 ^ +"),DELTA);
    }

}