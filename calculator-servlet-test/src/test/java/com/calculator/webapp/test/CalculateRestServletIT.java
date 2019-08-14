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

import javax.ws.rs.core.Response;
import java.io.File;
import java.net.URL;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(Arquillian.class)
public class CalculateRestServletIT {

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
        Response response=page.getResponseFromTheGeneratedPage("((100/0^0/100)*10)-(-90)");
        assertThat(response.getStatus(),equalTo(Response.Status.OK.getStatusCode()));


        String calculationResult=response.readEntity(String.class);
        assertThat(calculationResult,containsString("\"result\":\"100.0\""));
    }

    @Test
    public void doGet_IllegalExpression_MissingBracket() throws Exception
    {
        Response response=page.getResponseFromTheGeneratedPage("(-1.0/0.001");
        assertThat(response.getStatus(),equalTo(Response.Status.BAD_REQUEST.getStatusCode()));

        String calculationResult=response.readEntity(String.class);
        assertThat(calculationResult,containsString("\"errorCode\":400"));
        assertThat(calculationResult,containsString("\"message\":\"Missing or misplaced brackets\""));
    }

    @Test
    public void doGet_IllegalExpression_SequentialComponents() throws Exception
    {
        Response response=page.getResponseFromTheGeneratedPage("-1.0 2 + 3");
        assertThat(response.getStatus(),equalTo(Response.Status.BAD_REQUEST.getStatusCode()));

        String calculationResult=response.readEntity(String.class);
        assertThat(calculationResult,containsString("\"errorCode\":400"));
        assertThat(calculationResult,containsString("\"message\":\"Sequential components of the same type\""));
    }

    @Test
    public void doGet_IllegalExpression_MissingOperator() throws Exception
    {
        Response response=page.getResponseFromTheGeneratedPage("1(-1.0)/2");
        assertThat(response.getStatus(),equalTo(Response.Status.BAD_REQUEST.getStatusCode()));
        String calculationResult=response.readEntity(String.class);
        assertThat(calculationResult,containsString("\"errorCode\":400"));
        assertThat(calculationResult,containsString("\"message\":\"Missing operator between a number and an opening bracket or a closing bracket and a number\""));
    }

    @Test
    public void doGet_IllegalExpression_EmptyEquation() throws Exception
    {
        Response response=page.getResponseFromTheGeneratedPage("     ");
        assertThat(response.getStatus(),equalTo(Response.Status.BAD_REQUEST.getStatusCode()));
        String calculationResult=response.readEntity(String.class);
        assertThat(calculationResult,containsString("\"errorCode\":400"));
        assertThat(calculationResult,containsString("\"message\":\"Empty equation\""));
    }

    @Test
    public void doGet_IllegalExpression_EmptyBrackets() throws Exception
    {
        Response response=page.getResponseFromTheGeneratedPage("()");
        assertThat(response.getStatus(),equalTo(Response.Status.BAD_REQUEST.getStatusCode()));
        String calculationResult=response.readEntity(String.class);
        assertThat(calculationResult,containsString("\"errorCode\":400"));
        assertThat(calculationResult,containsString("\"message\":\"Empty brackets\""));
    }

    @Test
    public void doGet_IllegalExpression_EquationBeginningWithOperation() throws Exception
    {
        Response response=page.getResponseFromTheGeneratedPage("^1/2+3");
        assertThat(response.getStatus(),equalTo(Response.Status.BAD_REQUEST.getStatusCode()));
        String calculationResult=response.readEntity(String.class);
        assertThat(calculationResult,containsString("\"errorCode\":400"));
        assertThat(calculationResult,containsString("\"message\":\"Scope of equation ending or beginning with an operator\""));
    }

    @Test
    public void doGet_IllegalExpression_DivisionByZero() throws Exception
    {
        Response response=page.getResponseFromTheGeneratedPage("1/0");
        assertThat(response.getStatus(),equalTo(Response.Status.BAD_REQUEST.getStatusCode()));
        String calculationResult=response.readEntity(String.class);
        assertThat(calculationResult,containsString("\"errorCode\":400"));
        assertThat(calculationResult,containsString("\"message\":\"Division by zero\""));
    }

    @Test
    public void doGet_IllegalExpression_UnsupportedComponent() throws Exception
    {
        Response response=page.getResponseFromTheGeneratedPage("1#3");
        assertThat(response.getStatus(),equalTo(Response.Status.BAD_REQUEST.getStatusCode()));
        String calculationResult=response.readEntity(String.class);
        assertThat(calculationResult,containsString("\"errorCode\":400"));
        assertThat(calculationResult,containsString("\"message\":\"Unsupported component :#\""));
    }

}

