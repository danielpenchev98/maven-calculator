package calculator.computation;

import calculator.exceptions.InvalidComponentException;
import calculator.exceptions.InvalidEquationException;
import org.junit.Before;
import org.junit.Test;


import java.util.EmptyStackException;
import java.util.LinkedList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class ReversePolishCalculationAlgorithmIT {

    private ReversePolishCalculationAlgorithm algorithm;


    @Before
    public void setUp()
    {
        algorithm=new ReversePolishCalculationAlgorithm();
    }


    @Test(expected = EmptyStackException.class)
    public void calculationEquation_EquationWithMissingNumber_OutOfItemsExceptionThrown() throws Exception
    {

        List<EquationComponent> input=new LinkedList<>();
        input.add(new NumberComponent("2.0"));
        input.add(new NumberComponent("3.0"));
        input.add(new Addition());
        input.add(new Addition());

        algorithm.calculateEquation(input);
    }

    @Test(expected = InvalidEquationException.class)
    public void calculationEquation_EquationWithMissingOperator_MissingOperatorExceptionThrown() throws Exception
    {

        List<EquationComponent> input=new LinkedList<>();
        input.add(new NumberComponent("2.0"));
        input.add(new NumberComponent("2.0"));
        input.add(new Addition());
        input.add(new NumberComponent("3.0"));

        algorithm.calculateEquation(input);
    }

    @Test(expected = InvalidComponentException.class)
    public void calculationEquation_EquationWithInvalidOperator_InvalidOperatorException() throws Exception
    {

        List<EquationComponent> input=new LinkedList<>();
        input.add(new NumberComponent("2.0"));
        input.add(new NumberComponent("3.0"));
        input.add(new ClosingBracket());

        algorithm.calculateEquation(input);
    }

    @Test
    public void calculationEquation_LegalEquation() throws Exception
    {

        List<EquationComponent> input=new LinkedList<>();
        NumberComponent number=new NumberComponent("3.0");
        input.add(number);
        input.add(number);
        input.add(number);
        input.add(new Power());
        input.add(new Addition());

        assertEquals(30,algorithm.calculateEquation(input),0.001);
    }
}