package calculator.computation;

import calculator.computation.ComputationalMachine;
import calculator.computation.MathArithmeticOperatorFactory;
import calculator.computation.ReversePolishCalculationAlgorithm;
import calculator.exceptions.InvalidEquationException;
import calculator.exceptions.InvalidOperatorException;
import calculator.exceptions.MissingOperatorException;
import calculator.inputControl.EquationValidator;
import org.junit.Before;
import org.junit.Test;

import java.util.EmptyStackException;

import static org.junit.Assert.*;

public class ReversePolishCalculationAlgorithmIT {

    private ReversePolishCalculationAlgorithm algorithm;

    private final double DELTA=0.0001;

    @Before
    public void setUp()
    {
        ComputationalMachine machine=new ComputationalMachine(new MathArithmeticOperatorFactory());
        EquationValidator validator=new EquationValidator();
        algorithm=new ReversePolishCalculationAlgorithm(machine,validator);
    }


    @Test(expected = EmptyStackException.class)
    public void calculationEquation_EquationWithMissingNumber_OutOfItemsExceptionThrown() throws Exception
    {
        algorithm.calculateEquation("2.0 3.0 + +");
    }


    @Test(expected = InvalidEquationException.class)
    public void calculationEquation_EquationWithMissingOperator_MissingOperatorExceptionThrown() throws Exception
    {
        algorithm.calculateEquation("2.0 2.0 + 3.0");
    }

    @Test(expected = InvalidOperatorException.class)
    public void calculationEquation_EquationWithInvalidOperator_InvalidOperatorException() throws Exception
    {
        algorithm.calculateEquation("2.0 3.0 #");
    }

    @Test
    public void calculationEquation_LegalEquation() throws Exception
    {

        assertEquals(30,algorithm.calculateEquation("3.0 3.0 3.0 ^ +"),DELTA);
    }

}