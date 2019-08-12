package com.calculator.webapp.test;

import com.calculator.webapp.test.webclient.MainPage;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.importer.ZipImporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.net.URL;
import static org.junit.Assert.assertEquals;

@RunWith(Arquillian.class)
public class CalculateServletIT {

    @ArquillianResource
    private URL url;

    private MainPage page;

    //tests are clients outside of the container
    @Deployment(testable = false)
    public static WebArchive createTestArchive()
    {
       WebArchive archive = ShrinkWrap.create(ZipImporter.class,"calculator.war")
               .importFrom(new File("target"+File.separator+"lib"+File.separator+"calculator-servlet-1.0-SNAPSHOT.war"))
               .as(WebArchive.class)
               .addAsResource("arquillian.xml");
       return archive;
    }

    @Before
    public void setUp()
    {
        page=new MainPage(url);
    }

    @Test
    public void doGet_LegalExpression() throws Exception
    {
        String response=page.getResultFromTheGeneratedPage("10*10");
        assertEquals("{\"result\":\"100.0\"}",response);
    }

    @Test
    public void doGet_IllegalExpression_MissingBracket() throws Exception
    {
        String response=page.getResultFromTheGeneratedPage("(-1.0/0.001");
        assertEquals("{\"error\":\"Missing or misplaced brackets\"}",response);
    }

    @Test
    public void doGet_IllegalExpression_SequentialComponents() throws Exception
    {
        String response=page.getResultFromTheGeneratedPage("-1.0 2 + 3");
        assertEquals("{\"error\":\"Sequential components of the same type\"}",response);
    }

    @Test
    public void doGet_IllegalExpression_MissingOperator() throws Exception
    {
        String response=page.getResultFromTheGeneratedPage("1(-1.0)/2");
        assertEquals("{\"error\":\"Missing operator between a number and an opening bracket or a closing bracket and a number\"}",response);
    }

    @Test
    public void doGet_IllegalExpression_EmptyEquation() throws Exception
    {
        String response=page.getResultFromTheGeneratedPage("     ");
        assertEquals("{\"error\":\"Empty equation\"}",response);
    }

    @Test
    public void doGet_IllegalExpression_EmptyBrackets() throws Exception
    {
        String response=page.getResultFromTheGeneratedPage("()");
        assertEquals("{\"error\":\"Empty brackets\"}",response);
    }

    @Test
    public void doGet_IllegalExpression_EquationBeginningWithOperation() throws Exception
    {
        String response=page.getResultFromTheGeneratedPage("^1/2+3");
        assertEquals("{\"error\":\"Scope of equation ending or beginning with an operator\"}",response);
    }

    @Test
    public void doGet_IllegalExpression_DivisionByZero() throws Exception
    {
        String response=page.getResultFromTheGeneratedPage("1/0");
        assertEquals("{\"error\":\"Division by zero\"}",response);
    }

    @Test
    public void doGet_IllegalExpression_UnsupportedComponent() throws Exception
    {
        String response=page.getResultFromTheGeneratedPage("1#3");
        assertEquals("{\"error\":\"Unsupported component :#\"}",response);
    }
}

