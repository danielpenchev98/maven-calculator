package calculator.computation;

import calculator.exceptions.InvalidComponentException;
import calculator.exceptions.InvalidEquationException;
import org.junit.Before;
import org.junit.Test;


import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.LinkedList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class ReversePolishCalculationAlgorithmIT {

    private ReversePolishCalculationAlgorithm algorithm;

    private NumberComponent firstNumber;
    private NumberComponent secondNumber;
    private Addition addition;


    @Before
    public void setUp()
    {
        algorithm=new ReversePolishCalculationAlgorithm();
        firstNumber=new NumberComponent("2.0");
        secondNumber=new NumberComponent("3.0");
        addition=new Addition();
    }


    @Test(expected = EmptyStackException.class)
    public void calculationEquation_EquationWithMissingNumber_OutOfItemsExceptionThrown() throws Exception
    {

        List<EquationComponent> input=new LinkedList<>(Arrays.asList(firstNumber,secondNumber,addition,addition));
        algorithm.calculateEquation(input);
    }

    @Test(expected = InvalidEquationException.class)
    public void calculationEquation_EquationWithMissingOperator_MissingOperatorExceptionThrown() throws Exception
    {

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

        List<EquationComponent> input=new LinkedList<>(Arrays.asList(secondNumber,secondNumber,secondNumber,new Power(),addition));
        assertEquals(30,algorithm.calculateEquation(input),0.001);
    }
}