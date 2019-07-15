package calculator.computationTests;

import calculator.computation.ComputationalMachine;
import calculator.computation.ReversePolishCalculationAlgorithm;

import calculator.exceptions.InvalidOperatorException;
import calculator.exceptions.MissingOperatorException;
import calculator.exceptions.OutOfItemsException;
import calculator.inputControl.EquationValidator;
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


    @Test(expected = EmptyStackException.class)
    public void calculationEquation_EquationWithMissingNumber_OutOfItemsExceptionThrown() throws Exception
    {
        Mockito.when(machine.computeAction("+",2.0,3)).thenReturn(5.0);

        Mockito.when(validator.isValidNumber("2.0")).thenReturn(true);
        Mockito.when(validator.isValidNumber("3")).thenReturn(true);
        Mockito.when(validator.isValidNumber("+")).thenReturn(false);

        algorithm.calculateEquation("2.0 3 + +".split(" "));
    }


    @Test(expected = MissingOperatorException.class)
    public void calculationEquation_EquationWithMissingOperator_MissingOperatorExceptionThrown() throws Exception
    {
        Mockito.when(machine.computeAction("+",2.0,1)).thenReturn(3.0);

        Mockito.when(validator.isValidNumber("2.0")).thenReturn(true);
        Mockito.when(validator.isValidNumber("1.0")).thenReturn(true);
        Mockito.when(validator.isValidNumber("+")).thenReturn(false);
        Mockito.when(validator.isValidNumber("3.0")).thenReturn(true);

        algorithm.calculateEquation("2.0 1.0 + 3.0".split(" "));
    }

    @Test(expected = InvalidOperatorException.class)
    public void calculationEquation_EquationWithInvalidOperator_InvalidOperatorException() throws Exception
    {
        Mockito.when(validator.isValidNumber("2.0")).thenReturn(true);
        Mockito.when(validator.isValidNumber("1.0")).thenReturn(true);
        Mockito.when(validator.isValidNumber("#")).thenReturn(false);


        Mockito.when(machine.computeAction("#",2.0,1.0)).thenThrow(new InvalidOperatorException("Invalid operator"));

        algorithm.calculateEquation("2.0 1.0 #".split(" "));
    }

    @Test
    public void calculationEquation_LegalEquation() throws Exception
    {
        Mockito.when(machine.computeAction("^",3.0,3.0)).thenReturn(27.0);
        Mockito.when(machine.computeAction("+",3.0,27.0)).thenReturn(30.0);

        Mockito.when(validator.isValidNumber("^")).thenReturn(false);
        Mockito.when(validator.isValidNumber("3")).thenReturn(true);
        Mockito.when(validator.isValidNumber("+")).thenReturn(false);


        assertEquals(30,algorithm.calculateEquation("3 3 3 ^ +".split(" ")),DELTA);
    }

}