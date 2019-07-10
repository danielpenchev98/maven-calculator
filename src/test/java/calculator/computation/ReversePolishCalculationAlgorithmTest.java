package calculator.computation;

import calculator.inputControl.EquationValidator;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ReversePolishCalculationAlgorithmTest {

    private ReversePolishCalculationAlgorithm algorithm;

    @Before
    public void setUp()
    {
        algorithm=new ReversePolishCalculationAlgorithm(ComputationalMachine.getInstance(),new EquationValidator());
    }

    @Test
    public void calculateEquation() {


     //   assertEquals(2,algorithm.calculateEquation("2 0 -"));
    }
}