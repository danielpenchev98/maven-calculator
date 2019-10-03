package com.calculator.webapp.test.pageobjects.webclient;

import com.calculator.webapp.db.dto.CalculatorResponseDTO;
import com.calculator.webapp.restresponse.CalculationError;
import com.calculator.webapp.restresponse.CalculationResult;
import com.calculator.webapp.test.pageobjects.webclient.exception.CalculatorRestException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.InternalServerErrorException;
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

    private final ObjectMapper mapper = new ObjectMapper();

    public CalculatorPage(final URL baseUrl) {
        this.baseUrl = baseUrl;
    }

    public CalculationResult calculate(final String input) throws Exception {
        String encodedInput = getUrlEncodedInput(input);
        URL encodedUrl = new URL(baseUrl,CALCULATOR_SERVICE_WAR+REST_URL+CALCULATE_EQUATION_URL+"?"+REQUEST_PARAMETER+"="+encodedInput);

        String responseBody = getRestResponse(encodedUrl).readEntity(String.class);

        return mapper.readValue(responseBody,CalculationResult.class);
    }

    public List<CalculatorResponseDTO> getCalculationHistory() throws Exception {
        URL url = new URL(baseUrl,CALCULATOR_SERVICE_WAR+ REST_URL+CALCULATION_HISTORY_URL);

        String responseBody = getRestResponse(url).readEntity(String.class);

        return mapper.readValue(responseBody,new TypeReference<List<CalculatorResponseDTO>>(){});
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

    private Response getRestResponse(final URL url) throws Exception {
        Client client= ClientBuilder.newClient();
        WebTarget webTarget=client.target(URI.create(url.toExternalForm()));
        Response restResponse = webTarget.request(MediaType.APPLICATION_JSON).get(Response.class);
        checkResponseStatusCode(restResponse);
        return restResponse;
    }

    private String extractExceptionMessage(final Response response) throws IOException {
        String responseBody = response.readEntity(String.class);
        CalculationError error = mapper.readValue(responseBody,CalculationError.class);
        return error.getMessage();
    }


    private String getUrlEncodedInput(final String unformattedInput) throws IOException {
        return URLEncoder.encode(unformattedInput, ENCODING);
    }

}
