package com.calculator.webapp.test.pageobjects.webclient;

import com.calculator.webapp.test.pageobjects.webclient.CalculatorResponseDTO;
import com.calculator.webapp.test.pageobjects.webclient.CalculatorResultPojo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

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

    public CalculatorResultPojo calculate(final String input) throws IOException {
        String encodedInput = getUrlEncodedInput(input);
        URL encodedUrl = new URL(baseUrl,CALCULATOR_SERVICE_WAR+REST_URL+CALCULATE_EQUATION_URL+"?"+REQUEST_PARAMETER+"="+encodedInput);
        return new ObjectMapper().readValue(getResponseViaURL(encodedUrl).readEntity(String.class),CalculatorResultPojo.class);
    }

    public List<CalculatorResponseDTO> getCalculationHistory() throws IOException {
        URL url = new URL(baseUrl,CALCULATOR_SERVICE_WAR+ REST_URL+CALCULATION_HISTORY_URL);
        return new ObjectMapper().readValue(getResponseViaURL(url).readEntity(String.class),new TypeReference<List<CalculatorResponseDTO>>(){});
    }

    private void checkResponseStatusCode(final Response response) {
        if(response.getStatus()!=Response.Status.OK.getStatusCode()) {
            String exceptionMessage = extractExceptionMessage(response);
            throw new BadRequestException(exceptionMessage);
        }
    }

    private Response getResponseViaURL(final URL url) {
        Client client= ClientBuilder.newClient();
        WebTarget webTarget=client.target(URI.create(url.toExternalForm()));
        Response restResponse = webTarget.request(MediaType.APPLICATION_JSON).get(Response.class);
        checkResponseStatusCode(restResponse);
        return restResponse;
    }

    private String extractExceptionMessage(final Response response) {
        JSONObject errorResponse = new JSONObject(response.readEntity(String.class));
        return errorResponse.getString("message");
    }


    private String getUrlEncodedInput(final String unformattedInput) throws IOException {
        return URLEncoder.encode(unformattedInput, ENCODING);
    }

}
