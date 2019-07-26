package com.calculator.core;

import com.calculator.core.computation.ReversePolishCalculationAlgorithm;
import com.calculator.core.inputprocessing.InputFormatter;
import com.calculator.core.inputprocessing.ReversePolishNotationParser;
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
    private InputFormatter formatter;

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
        inorder.verify(parser).formatFromInfixToReversedPolishNotation(ArgumentMatchers.anyList());
        inorder.verify(algorithm).calculateEquation(ArgumentMatchers.anyList());
    }

}
