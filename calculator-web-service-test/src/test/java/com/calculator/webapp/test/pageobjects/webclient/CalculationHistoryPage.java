package com.calculator.webapp.test.pageobjects.webclient;

import com.calculator.webapp.db.dto.RequestDTO;
import com.calculator.webapp.test.pageobjects.webclient.requestexecutor.HttpRequestExecutor;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class CalculationHistoryPage extends CalculatorRestPage {

    static final String CALCULATION_HISTORY_URL = "/calculationHistory";

    public CalculationHistoryPage(final URL baseUrl,final String username,final String password) {
        super(baseUrl,new HttpRequestExecutor(username,password));
    }

    public List<RequestDTO> getCalculationHistory() throws Exception {
        URL requestUrl = getHistoryRequestUrl();
        return requestExecutor.executeGetRequest(requestUrl).readEntity(List.class);
    }

    private URL getHistoryRequestUrl() throws MalformedURLException {
        return new URL(baseUrl,CALCULATOR_SERVICE_WAR + REST_URL + CALCULATION_HISTORY_URL);
    }

}
