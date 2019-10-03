package com.calculator.webapp.test.pageobjects.webclient;

import com.calculator.webapp.restresponse.CalculationError;
import com.calculator.webapp.test.pageobjects.webclient.exception.CalculatorRestException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.URI;
import java.net.URL;

public abstract class CalculatorRestPage {
    static final String CALCULATOR_SERVICE_WAR = "/calculator";
    static final String REST_URL = "/api/v1/calculator";

    protected URL baseUrl;
    protected final ObjectMapper mapper = new ObjectMapper();

    public CalculatorRestPage(final URL baseUrl){
        this.baseUrl=baseUrl;
    }

    //TODO not sure if the checkResponseStatusCode should be called here????
    protected Response getRestResponse(final URL url) throws Exception {
        WebTarget webTarget = getWebTarget(url);
        Response restResponse = webTarget.request(MediaType.APPLICATION_JSON).get(Response.class);
        checkResponseStatusCode(restResponse);
        return restResponse;
    }

    private WebTarget getWebTarget(final URL resource){
        Client client = ClientBuilder.newClient();
        return client.target(URI.create(resource.toExternalForm()));
    }

    private void checkResponseStatusCode(final Response response) throws Exception {
        if(isUnsuccessfulRequest(response)) {
            String exceptionMessage = extractExceptionMessage(response);
            throw new CalculatorRestException(exceptionMessage);
        }
    }

    private boolean isUnsuccessfulRequest(final Response response){
        return response.getStatus()!=Response.Status.OK.getStatusCode();
    }

    private String extractExceptionMessage(final Response response) throws IOException {
        String responseBody = response.readEntity(String.class);
        CalculationError error = mapper.readValue(responseBody,CalculationError.class);
        return error.getMessage();
    }

}
