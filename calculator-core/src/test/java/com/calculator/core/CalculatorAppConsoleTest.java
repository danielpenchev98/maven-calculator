package com.calculator.core;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class CalculatorAppConsoleTest {

    @Test
    public void main_CalculateLegalEquation_DisplayResult() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        String[] argc=new String[]{"1+1"};
        CalculatorAppConsole.main(argc);

        String expectedOutput  = "2.0";

        assertEquals(expectedOutput, outContent.toString());
    }

}