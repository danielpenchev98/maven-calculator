package com.calculator.webapp.test.pageobjects.webclient.requestobjects;

import java.net.URL;
import java.net.URLEncoder;


public class CalculationResultRequestUrl implements CalculatorRestRequestUrl {

    private URL requestUrl;

    public CalculationResultRequestUrl(final URL baseUrl, String equation) throws Exception {
        String encodedEquation = URLEncoder.encode(equation, CalculatorRestUrlProvider.ENCODING);
        requestUrl = new URL(baseUrl,
                CalculatorRestUrlProvider.CALCULATOR_SERVICE_WAR +
                        CalculatorRestUrlProvider.REST_URL +
                        CalculatorRestUrlProvider.CALCULATE_EQUATION_URL +
                        "?" +
                        CalculatorRestUrlProvider.REQUEST_PARAMETER +
                        "=" +
                        encodedEquation);
    }

    @Override
    public URL getRequestUrl() {
        return requestUrl;
    }
}
