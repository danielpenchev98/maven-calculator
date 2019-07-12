package calculator.computationTests;

import calculator.computation.ComputationalMachine;
import calculator.computation.ReversePolishCalculationAlgorithm;
import calculator.container.ComponentSupplier;
import calculator.exceptions.InvalidOperatorException;
import calculator.exceptions.MissingOperatorException;
import calculator.exceptions.OutOfItemsException;
import calculator.inputControl.EquationValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;


import java.util.Arrays;

import static org.junit.Assert.*;


@RunWith(MockitoJUnitRunner.class)
public class ReversePolishCalculationAlgorithmTest {

    private ReversePolishCalculationAlgorithm algorithm;

    @Mock
    public ComputationalMachine machine;
    @Mock
    public EquationValidator validator;
  //  @Mock
   // public ComponentSupplier<String> supplier;


    private final double DELTA=0.0001;

    @Before
    public void setUp()
    {
        algorithm=new ReversePolishCalculationAlgorithm(machine,validator);
    }

    @Test(expected = OutOfItemsException.class)
    public void calculationEquation_EquationWithMissingNumber_OutOfItemsExceptionThrown() throws Exception
    {
        Mockito.when(machine.computeAction("+",2.0,3)).thenReturn(5.0);

        Mockito.when(validator.isValidNumber("2.0")).thenReturn(true);
        Mockito.when(validator.isValidNumber("3")).thenReturn(true);
        Mockito.when(validator.isValidNumber("+")).thenReturn(false);

    //    Mockito.when(supplier.receiveListOfNextItems(2)).thenReturn(Arrays.asList("2.0","3"));

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

//        Mockito.doNothing().when(supplier).addItem(Mockito.anyString());
     //   Mockito.when(supplier.receiveListOfNextItems(2)).thenReturn(Arrays.asList("2.0","1.0"));

        algorithm.calculateEquation("2.0 1.0 + 3.0".split(" "));
    }

    @Test(expected = InvalidOperatorException.class)
    public void calculationEquation_EquationWithInvalidOperator_InvalidOperatorException() throws Exception
    {
        Mockito.when(machine.computeAction("#",2.0,1.0)).thenThrow(new InvalidOperatorException("Invalid operator"));

        Mockito.when(validator.isValidNumber("2.0")).thenReturn(true);
        Mockito.when(validator.isValidNumber("1.0")).thenReturn(true);
        Mockito.when(validator.isValidNumber("#")).thenReturn(false);

//        Mockito.doNothing().when(supplier).addItem("2.0");
//        Mockito.doNothing().when(supplier).addItem("1.0");

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

//        Mockito.doNothing().when(supplier).addItem("3");
//        Mockito.when(supplier.receiveListOfNextItems(2)).thenReturn(Arrays.asList("3","3")).thenReturn(Arrays.asList("3","27.0"));

        assertEquals(30,algorithm.calculateEquation("3 3 3 ^ +".split(" ")),DELTA);
    }

}