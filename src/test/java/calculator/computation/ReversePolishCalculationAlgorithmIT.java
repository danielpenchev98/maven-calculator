package calculator.computation;

import calculator.exceptions.InvalidEquationException;
import calculator.exceptions.InvalidParameterException;
import calculator.inputcontrol.ComponentValidator;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class ReversePolishCalculationAlgorithmIT {

    private ReversePolishCalculationAlgorithm algorithm;


    @Before
    public void setUp()
    {
        ComputationalMachine machine=new ComputationalMachine(new EquationComponentFactory());
        ComponentValidator validator=new ComponentValidator();
        algorithm=new ReversePolishCalculationAlgorithm(machine,validator);
    }


    @Test(expected = EmptyStackException.class)
    public void calculationEquation_EquationWithMissingNumber_OutOfItemsExceptionThrown() throws Exception
    {
        List<String> input=new LinkedList<>(Arrays.asList("2.0","3.0","+","+"));
        algorithm.calculateEquation(input);
    }


    @Test(expected = InvalidEquationException.class)
    public void calculationEquation_EquationWithMissingOperator_MissingOperatorExceptionThrown() throws Exception
    {
        List<String> input=new LinkedList<>(Arrays.asList("2.0","2.0","+","3.0"));
        algorithm.calculateEquation(input);
    }

    @Test(expected = InvalidParameterException.class)
    public void calculationEquation_EquationWithInvalidOperator_InvalidOperatorException() throws Exception
    {
        List<String> input=new LinkedList<>(Arrays.asList("2.0","3.0","#"));
        algorithm.calculateEquation(input);
    }

    @Test
    public void calculationEquation_LegalEquation() throws Exception
    {

        List<String> input=new LinkedList<>(Arrays.asList("3.0","3.0","3.0","^","+"));
        assertEquals(30,algorithm.calculateEquation(input),0.001);
    }

}