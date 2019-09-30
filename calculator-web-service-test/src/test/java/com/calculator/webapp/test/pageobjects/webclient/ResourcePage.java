package com.calculator.webapp.test.pageobjects.webclient;

import com.calculator.webapp.test.pageobjects.webclient.requestobjects.CalculatorRestRequestUrl;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URL;


public class ResourcePage {

    public Response getResponseFromTheGeneratedPage(final CalculatorRestRequestUrl requestUrl) {
        URL encodedUrl = requestUrl.getRequestUrl();
        Client client= ClientBuilder.newClient();
        WebTarget webTarget=client.target(URI.create(encodedUrl.toExternalForm()));
        return webTarget.request(MediaType.APPLICATION_JSON).get(Response.class);
    }
}
