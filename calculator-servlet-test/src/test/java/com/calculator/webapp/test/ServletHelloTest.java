package com.calculator.webapp.test;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.jboss.arquillian.container.test.api.Deployment;;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;


import static org.junit.Assert.assertEquals;

@RunWith(Arquillian.class)
public class ServletHelloTest {

    //to run as client -> to test it from outside of the container
    @Deployment(testable = false)
    public static WebArchive createTestArchive() {

        WebArchive archive=  ShrinkWrap.create(WebArchive.class,"test.war")
                .addClass(ServletHello.class)
                .addAsResource("arquillian.xml")
                .setWebXML("WEB-INF/web.xml");
        System.out.println(archive.toString(true));
        return archive;
    }

    @Test
    public void testGetText(@ArquillianResource URL url) throws Exception {
        URL test=new URL(url,"hello");
        System.out.println(test.toString());
        test.openStream();

        HttpClient client= HttpClientBuilder.create().build();
        HttpResponse response=client.execute(new HttpGet(URI.create(test.toExternalForm())));
        BufferedReader rd=new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

        assertEquals("Hello, World!",rd.readLine());
    }

}
