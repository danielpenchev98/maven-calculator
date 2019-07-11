package calculator.computationTests;

import calculator.computation.ComputationalMachine;
import calculator.computation.ReversePolishCalculationAlgorithm;
import calculator.container.ComponentSupplier;
import calculator.inputControl.EquationValidator;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ReversePolishCalculationAlgorithmTest {

    private ReversePolishCalculationAlgorithm algorithm;

    @Before
    public void setUp()
    {
        ComputationalMachine machine=ComputationalMachine.getInstance();
        EquationValidator validator=new EquationValidator();
        algorithm=new ReversePolishCalculationAlgorithm(machine,validator);
    }

    @Test
    public void calculateEquation() throws Exception {
        assertEquals(4.0,algorithm.calculateEquation("2.0 -1.5 * 7.0 +".split(" ")),0.0001);
    }

}