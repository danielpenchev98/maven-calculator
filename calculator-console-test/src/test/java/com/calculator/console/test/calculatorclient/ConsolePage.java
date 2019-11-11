package com.calculator.console.test.calculatorclient;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ConsolePage {

    private final static String PATH = MessageFormat.format(".{0}target{0}lib{0}calculator-console-1.0-SNAPSHOT.jar",File.separator);

    public String calculateEquation(final List<String> equation) throws Exception
    {
        List<String> command=new LinkedList<>(Arrays.asList("java","-jar",PATH));
        command.addAll(equation);
        final Process process = new ProcessBuilder(command).start();
        return getCalculationResult(process);
    }

    private String getCalculationResult(final Process process) throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String result = in.readLine();
        process.waitFor();
        return result;
    }
}
