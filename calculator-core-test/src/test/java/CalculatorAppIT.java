import com.calculator.core.exceptions.InvalidEquationException;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

import static org.junit.Assert.assertEquals;

//TODO Problem, the process, which runs the jar, keeps the exception for himself -> need to fix it

@RunWith(DataProviderRunner.class)
public class CalculatorAppIT {

    final static Double DELTA=0.0001;

    @DataProvider
    public static Object[][] correctExpressionSupplier(){
        return new Object[][]{{"((8^2/18)-(100*100)/1500)^((10))",84948.4030},
                              {"22/7",3.14285},
                              {"100/0^0",100.0}};
    }

    @DataProvider
    public static Object[] illegalStructuredExpressionSupplier(){
        return new Object[]{"1+(( 23*4) - (-11)",
                            "1 / ( 5-(-2 3 + 5)/10",
                            "( 1 + 2 *( 10/5/2/1 ) ^^3)",
                            "8/ (((10 - 11/2 )))( -1 / 3 )",
                            "",
                            "(()()(()))",
                            "[ (10 +7) ] / {20^100}"};
    }

    //TODO might fail - if the process creating fails

    private Process executeJarInNewProcess(final String equation) throws Exception
    {
        final ProcessBuilder pBuilder = new ProcessBuilder("java","-jar","calculator-core.jar",equation);
        pBuilder.directory(new File("../calculator-core/classes/artifacts/calculator_core_jar"));
        return pBuilder.start();
    }

    @Test
    @UseDataProvider("correctExpressionSupplier")
    public void calculate_ReturnCorrectResultOfEquation(final String equation,final Double expectedResult) throws Exception {
        final Process process = executeJarInNewProcess(equation);
        BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String result="";
        result=in.readLine();
        int status = process.waitFor();
        assertEquals(expectedResult,Double.valueOf(result),DELTA);
    }

    @Test(expected = InvalidEquationException.class)
    @UseDataProvider("illegalStructuredExpressionSupplier")
    public void calculate_WrongStructuredExpression(final String equation) throws Exception
    {
        final Process process = executeJarInNewProcess(equation);
        BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String result="";
        result=in.readLine();
        int status = process.waitFor();
    }

    @Test
    public void calculate_DivisionOnZero() throws Exception
    {
        final Process process=executeJarInNewProcess("100/0");
        BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String result="";
        result=in.readLine();
        int status = process.waitFor();
        assertEquals(0,status);
    }
}
