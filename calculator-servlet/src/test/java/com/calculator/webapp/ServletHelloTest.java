package com.calculator.webapp;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import static org.junit.Assert.assertEquals;

@RunWith(Arquillian.class)
public class ServletHelloTest {
    @Deployment
    public static WebArchive createTestArchive() {

        WebArchive archive=  ShrinkWrap.create(WebArchive.class, "testss.war")
                .addClass(ServletHello.class);
        return archive;
    }

    @Test
    public void testGetText() throws Exception {
        URL url = new URL("http://localhost:8080/testss/hello");
        InputStream is = url.openStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String result = br.readLine();
        String expected = "Hello, World!";
        assertEquals(expected, result);

    }

}
