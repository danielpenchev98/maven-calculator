package com.calculator.core;

import com.calculator.core.calculation.ReversePolishCalculationAlgorithm;
import com.calculator.core.inputformatting.EquationFormatter;
import com.calculator.core.calculation.ReversePolishNotationParser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.inOrder;

@RunWith(MockitoJUnitRunner.class)
public class CalculateAppTest
{

    @InjectMocks
    private CalculatorApp app;

    @Mock
    private EquationFormatter formatter;

    @Mock
    private ReversePolishNotationParser parser;

    @Mock
    private ReversePolishCalculationAlgorithm algorithm;

    @Test
    public void calculateResult_OrderOfCalledMethods() throws Exception
    {
        app.calculateResult("1+1");

        InOrder inorder=inOrder(formatter,parser,algorithm);
        inorder.verify(formatter).doFormat("1+1");
        inorder.verify(algorithm).calculateEquation(ArgumentMatchers.anyList());
    }

}
