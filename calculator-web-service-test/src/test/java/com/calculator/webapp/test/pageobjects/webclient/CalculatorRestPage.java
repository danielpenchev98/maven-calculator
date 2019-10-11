package com.calculator.webapp.test.pageobjects.webclient;

import com.calculator.webapp.test.pageobjects.webclient.requestexecutor.HttpRequestExecutor;

import java.net.URL;

public abstract class CalculatorRestPage {
    static final String CALCULATOR_SERVICE_WAR = "/calculator";
    static final String REST_URL = "/api/v1/calculator";

    protected URL baseUrl;
    protected HttpRequestExecutor requestExecutor;

    public CalculatorRestPage(final URL baseUrl,final HttpRequestExecutor executor){
        this.baseUrl=baseUrl;
        this.requestExecutor = executor;
    }

}
