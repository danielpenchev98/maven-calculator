package com.calculator.webapp.test.pageobjects.webclient.requestobjects;

import java.net.URL;

public class CalculationHistoryRequestUrl implements CalculatorRestRequestUrl {

    private URL requestUrl;

    public CalculationHistoryRequestUrl(final URL baseUrl) throws Exception {
        requestUrl = new URL(baseUrl,
                CalculatorRestUrlProvider.CALCULATOR_SERVICE_WAR +
                        CalculatorRestUrlProvider.REST_URL +
                        CalculatorRestUrlProvider.CALCULATION_HISTORY_URL);
    }

    @Override
    public URL getRequestUrl() {
        return requestUrl;
    }
}
