package com.calculator.webapp.test.pageobjects.webclient;

import com.calculator.webapp.db.dto.CalculatorResponseDTO;
import com.fasterxml.jackson.core.type.TypeReference;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class CalculationHistoryPage extends CalculatorRestPage {

    static final String CALCULATION_HISTORY_URL = "/calculationHistory";

    public CalculationHistoryPage(final URL baseUrl) {
        super(baseUrl);
    }

    public List<CalculatorResponseDTO> getCalculationHistory() throws Exception {
        URL requestUrl = getHistoryRequestUrl();
        String responseBody = getRestResponse(requestUrl).readEntity(String.class);
        return mapper.readValue(responseBody,new TypeReference<List<CalculatorResponseDTO>>(){});
    }

    private URL getHistoryRequestUrl() throws MalformedURLException {
        return new URL(baseUrl,CALCULATOR_SERVICE_WAR+ REST_URL+CALCULATION_HISTORY_URL);
    }

}
