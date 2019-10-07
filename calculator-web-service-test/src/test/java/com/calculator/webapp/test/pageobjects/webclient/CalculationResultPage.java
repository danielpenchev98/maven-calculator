package com.calculator.webapp.test.pageobjects.webclient;

import com.calculator.webapp.restresponse.CalculationResult;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class CalculationResultPage extends CalculatorRestPage {

    static final String ENCODING = "UTF-8";
    static final String CALCULATE_EQUATION_URL = "/calculate";
    static final String REQUEST_PARAMETER="equation";

    public CalculationResultPage(final URL baseUrl) {
        super(baseUrl);
    }

    public CalculationResult calculate(final String equation) throws Exception {
        String encodedEquation = getUrlEncodedInput(equation);
        URL requestUrl = getResultRequestUrl(encodedEquation);
        return requestExecutor.executeGetRequest(requestUrl).readEntity(CalculationResult.class);
    }

    private String getUrlEncodedInput(final String unformattedInput) throws IOException {
        return URLEncoder.encode(unformattedInput, ENCODING);
    }

    private URL getResultRequestUrl(final String encodedEquation) throws MalformedURLException {
        return new URL(baseUrl,CALCULATOR_SERVICE_WAR + REST_URL+CALCULATE_EQUATION_URL + "?" + REQUEST_PARAMETER + "=" + encodedEquation);
    }

}
