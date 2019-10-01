package com.calculator.webapp.test.pageobjects.webclient;


import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;

public class CalculatorPage {

    static final String ENCODING = "UTF-8";
    static final String CALCULATOR_SERVICE_WAR = "/calculator";
    static final String REST_URL = "/api/v1/calculator";
    static final String CALCULATE_EQUATION_URL = "/calculate";
    static final String CALCULATION_HISTORY_URL = "/calculationHistory";
    static final String REQUEST_PARAMETER="equation";

    private URL baseUrl;

    public CalculatorPage(final URL baseUrl) {
        this.baseUrl = baseUrl;
    }

    public Response calculate(final String input) throws IOException {
        String encodedInput = getUrlEncodedInput(input);
        URL encodedUrl = new URL(baseUrl,CALCULATOR_SERVICE_WAR+REST_URL+CALCULATE_EQUATION_URL+"?"+REQUEST_PARAMETER+"="+encodedInput);
        return getResponseViaURL(encodedUrl);
    }

    public Response getCalculationHistory() throws MalformedURLException {
        URL url = new URL(baseUrl,CALCULATOR_SERVICE_WAR+ REST_URL+CALCULATION_HISTORY_URL);
        return getResponseViaURL(url);
    }


    private Response getResponseViaURL(final URL url){
        Client client= ClientBuilder.newClient();
        WebTarget webTarget=client.target(URI.create(url.toExternalForm()));
        return webTarget.request(MediaType.APPLICATION_JSON).get(Response.class);
    }

    private String getUrlEncodedInput(final String unformattedInput) throws IOException {
        return URLEncoder.encode(unformattedInput, ENCODING);
    }

}
