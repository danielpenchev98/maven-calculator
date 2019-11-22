package com.calculator.core;

import com.calculator.core.calculation.CalculationAlgorithm;
import com.calculator.core.inputformatting.InputFormatter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.inOrder;

@RunWith(MockitoJUnitRunner.class)
public class CalculateAppTest
{
    private CalculatorApp app;

    @Mock
    private InputFormatter formatter;

    @Mock
    private CalculationAlgorithm algorithm;

    @Before
    public void setUp()
    {
        app=new CalculatorApp(formatter,algorithm);
    }

    @Test
    public void calculateResult_OrderOfCalledMethods() throws Exception
    {
        app.calculateResult("1+1");

        InOrder inorder=inOrder(formatter,algorithm);
        inorder.verify(formatter).doFormat("1+1");
        inorder.verify(algorithm).calculateExpression(ArgumentMatchers.anyList());
    }

}
