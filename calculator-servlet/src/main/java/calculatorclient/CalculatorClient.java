package calculatorclient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class CalculatorClient {

    public double doCalculate(final String equation) throws Exception
    {
        return Double.valueOf(getResultFromCalculatorJar(equation));
    }

    private String getResultFromCalculatorJar(final String equation) throws Exception
    {
        List<String> command=Arrays.asList("java","-jar","../calculator-core/target/calculator-core.jar",equation);
        final ProcessBuilder pBuilder = new ProcessBuilder(command);
        final Process process = pBuilder.start();
        BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String result = in.readLine();
        process.waitFor();
        return result;
    }
}
