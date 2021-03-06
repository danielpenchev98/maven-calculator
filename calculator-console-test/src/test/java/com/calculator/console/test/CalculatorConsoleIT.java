package com.calculator.console.test;

import com.calculator.console.test.calculatorclient.ConsolePage;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

@RunWith(DataProviderRunner.class)
public class CalculatorConsoleIT {

    private final static Double DELTA=0.0001;

    private static ConsolePage calculator;

    @BeforeClass
    public static void setUp()
    {
        calculator=new ConsolePage();
    }

    @DataProvider
    public static Object[][] correctExpressionSupplier(){
        return new Object[][]{{"(  (3/18) + 11 -( 100*100)/1500)-( (10))",-5.5},
                              {"22 / 7",3.14285},
                              {"1 + 0/100",1.0}};
    }

    @DataProvider
    public static Object[][] illegalExpressionSupplier(){
        return new Object[][]{{"1+(( 23*4) - (-11)","Problem with the structure of expression :Missing or misplaced brackets"},
                              {"1 / ( 5-(-2 3 + 5)/10)","Problem with the structure of expression :Sequential components of the same type"},
                              {"( 1 + 2 *( 10/5/2/1 ) ++3)","Problem with the structure of expression :Sequential components of the same type"},
                              {"8/ (((10 - 11/2 )))( -1 / 3 )","Problem with the structure of expression :Missing operator between a number and an opening bracket or a closing bracket and a number"},
                              {"    ","Problem with the structure of expression :Empty expression"},
                              {"(()()(()))","Problem with the structure of expression :Empty brackets"},
                              {"[ (10 +7) ] (20*100)","Problem with the structure of expression :Scope of expression ending or beginning with an operator"},
                              {"(10-10)*(10+10)/(10-10)","Arithmetic error :Division by zero"},
                              {"(( 100 - 99) & (199))","Problem with a component of expression :Unsupported component :&"},
                              {"PI/2 * 6","Problem with a component of expression :Unsupported component :PI"}};
    }



    @Test
    @UseDataProvider("correctExpressionSupplier")
    public void calculate_ReturnCorrectResultOfEquation(final String expression, final Double expectedResult) throws Exception {
        String actualResult = calculator.calculateExpression(Arrays.asList(expression));
        assertEquals(expectedResult,Double.valueOf(actualResult),DELTA);
    }

    @Test
    @UseDataProvider("illegalExpressionSupplier")
    public void calculate_WrongStructuredExpression(final String expression, final String exceptionMessage) throws Exception {
        String actualResult = calculator.calculateExpression(Arrays.asList(expression));
        assertThat(actualResult,is(exceptionMessage));
    }

    @Test
    public void calculate_InvalidNumberOfArguments() throws Exception {
        String actualResult = calculator.calculateExpression(Arrays.asList("1", "+", "1"));
        assertThat(actualResult,is("Invalid number of arguments"));
    }

}
