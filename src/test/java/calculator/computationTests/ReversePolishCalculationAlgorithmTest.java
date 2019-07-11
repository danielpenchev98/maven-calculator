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

import static org.junit.Assert.*;

public class ReversePolishCalculationAlgorithmTest {

    private ReversePolishCalculationAlgorithm algorithm;

    private final double DELTA=0.0001;

    @Before
    public void setUp()
    {
        ComputationalMachine machine=ComputationalMachine.getInstance();
        EquationValidator validator=new EquationValidator();
        algorithm=new ReversePolishCalculationAlgorithm(machine,validator);
    }

    @Test(expected = OutOfItemsException.class)
    public void calculationEquation_EquationWithMissingNumber_OutOfItemsExceptionThrown() throws Exception
    {
        algorithm.calculateEquation("2.0 3 + +".split(" "));
    }

    @Test(expected = MissingOperatorException.class)
    public void calculationEquation_EquationWithMissingOperator_MissingOperatorExceptionThrown() throws Exception
    {
        algorithm.calculateEquation("2.0 1.0 + 3.0".split(" "));
    }

    @Test(expected = InvalidOperatorException.class)
    public void calculationEquation_EquationWithInvalidOperator_InvalidOperatorException() throws Exception
    {
        algorithm.calculateEquation("2.0 1.0 #".split(" "));
    }

    @Test
    public void calculationEquation_LegalEquation() throws Exception
    {
        assertEquals(3.0001,algorithm.calculateEquation("3 4 2 * 1 5 - 2 3 ^ ^ / +".split(" ")),DELTA);
    }

}