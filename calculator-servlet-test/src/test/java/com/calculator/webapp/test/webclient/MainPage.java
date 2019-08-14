package com.calculator.webapp.test.webclient;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;


public class MainPage {

    private final URL baseUrl;

    private static final String ENCODING = "UTF-8";
    private static final String CALCULATOR_SERVLET_URL = "/calculator";
    private static final String GET_REQUEST_URL = "/api/v2/calculation";
    private static final String REQUEST_PARAMETER="equation";

    public MainPage(final URL baseUrl) {
        this.baseUrl = baseUrl;
    }

    public Response getResponseFromTheGeneratedPage(final String input) throws IOException {
        URL encodedUrl=getEncodedUrl(input);
        return getResponse(encodedUrl);
    }

    private URL getEncodedUrl(String unformattedInput) throws IOException {
        String spec = URLEncoder.encode(unformattedInput, ENCODING);
        return new URL(baseUrl,CALCULATOR_SERVLET_URL + GET_REQUEST_URL +"?"+REQUEST_PARAMETER+"="+spec);
    }

    private Response getResponse(final URL url){
        Client client= ClientBuilder.newClient();
        WebTarget webTarget=client.target(URI.create(url.toExternalForm()));
        return webTarget.request(MediaType.APPLICATION_JSON).get(Response.class);
    }
}
