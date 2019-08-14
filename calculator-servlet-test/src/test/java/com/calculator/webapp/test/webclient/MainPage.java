package com.calculator.webapp.test.webclient;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import java.io.IOException;
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

    public String getResultFromTheGeneratedPage(String input) throws IOException {
        URL encodedUrl = getEncodedUrl(input);

        ClientResponse response=getResponse(encodedUrl);
        String result=response.getEntity(String.class);

        return result;
    }

    private URL getEncodedUrl(String unformattedInput) throws IOException {
        String spec = URLEncoder.encode(unformattedInput, ENCODING);
        return new URL(baseUrl,CALCULATOR_SERVLET_URL + GET_REQUEST_URL +"?"+REQUEST_PARAMETER+"="+spec);
    }

    private ClientResponse getResponse(final URL url){
        Client client=Client.create();
        WebResource webResource=client.resource(url.toExternalForm());
        return webResource.accept("application/json").get(ClientResponse.class);
    }
}
