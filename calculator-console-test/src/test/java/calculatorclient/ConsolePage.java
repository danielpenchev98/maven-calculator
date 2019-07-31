package calculatorclient;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ConsolePage {

    private final static String PATH = "." + File.separator + "target" + File.separator + "lib" + File.separator + "calculator-console-1.0-SNAPSHOT.jar";

    public String getResultFromCalculatorConsole(final List<String> equation) throws Exception
    {
        List<String> command=new LinkedList<>(Arrays.asList("java","-jar",PATH));
        command.addAll(equation);
        final ProcessBuilder pBuilder = new ProcessBuilder(command);
        final Process process = pBuilder.start();
        BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String result = in.readLine();
        process.waitFor();
        return result;
    }
}
