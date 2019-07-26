import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

import static org.junit.Assert.assertEquals;

@RunWith(DataProviderRunner.class)
public class CalculatorAppIT {

    final static Double DELTA=0.0001;

    @DataProvider
    public static Object[][] correctExpressionSupplier(){
        return new Object[][]{{"((8 ^2/18)-( 100*100)/1500)^( (10))",84948.4030},
                              {"22 /7",3.14285},
                              {"100 /0^0",100.0}};
    }

    @DataProvider
    public static Object[][] illegalExpressionSupplier(){
        return new Object[][]{{"1+(( 23*4) - (-11)","Equation with missing or misplaced brackets"},
                              {"1 / ( 5-(-2 3 + 5)/10)","Sequential components of the same type"},
                              {"( 1 + 2 *( 10/5/2/1 ) ^^3)","Sequential components of the same type"},
                              {"8/ (((10 - 11/2 )))( -1 / 3 )","Missing operator between a number and an opening bracket or a closing bracket and a number"},
                              {"\"\"","Empty equation"},
                              {"(()()(()))","Empty brackets"},
                              {"[ (10 +7) ] (20^100)","Scope of equation ending or beginning with an operator"},
                              {"(10-10)*(10+10)/(10-10)","Division on zero"},
                              {"(( 100 - 99) & (199))","Unsupported component :&"},
                              {"PI/2 * 6","Unsupported component :PI"}};
    }


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
        String result = in.readLine();

        //TODO what to do with that status
        int status = process.waitFor();
        assertEquals(expectedResult,Double.valueOf(result),DELTA);
    }

    @Test
    @UseDataProvider("illegalExpressionSupplier")
    public void calculate_WrongStructuredExpression(final String equation,final String exceptionMessage) throws Exception
    {
        final Process process = executeJarInNewProcess(equation);
        BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String result = in.readLine();
        int status = process.waitFor();
        assertEquals(exceptionMessage, result);
    }


}
