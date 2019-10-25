package com.calculator.webapp.test.pageobjects.webclient;

import com.calculator.webapp.restresources.EquationRequestBody;
import com.calculator.webapp.restresponses.CalculationResult;
import com.calculator.webapp.restresponses.RequestId;
import com.calculator.webapp.test.pageobjects.webclient.requestexecutor.HttpRequestExecutor;

import javax.ws.rs.core.Response;
import java.net.MalformedURLException;
import java.net.URL;

public class CalculationResultPage extends CalculatorRestPage {

    static final String CALCULATE_EQUATION_URL = "/calculate";
    static final String GET_CALCULATION_URL = "/calculations";

    private static final int TIME_TO_WAIT_IN_MILLISECONDS = 7000;

    public CalculationResultPage(final URL baseUrl,final String username,final String password) {
        super(baseUrl,new HttpRequestExecutor(username,password));
    }

    public CalculationResult calculate(final String equation) throws Exception {
        long requestId = sendCalculationRequest(equation);
        return getCalculationResult(requestId);
    }

    private long sendCalculationRequest(final String equation) throws Exception {
        URL requestUrl = postCalculationRequestUrl();
        Response response = requestExecutor.executePostRequest(requestUrl,new EquationRequestBody(equation));
        return response.readEntity(RequestId.class).getId();
    }

    private CalculationResult getCalculationResult(final long requestId) throws Exception{
        waitForCalculationToBeCompleted();
        URL url = getCalculationResultUrl(requestId);
        Response response = requestExecutor.executeGetRequest(url);
        return response.readEntity(CalculationResult.class);
    }

    private void waitForCalculationToBeCompleted() throws InterruptedException {
        Thread.sleep(TIME_TO_WAIT_IN_MILLISECONDS);
    }

    private URL postCalculationRequestUrl() throws MalformedURLException {
        return new URL(baseUrl,CALCULATOR_SERVICE_WAR+REST_URL+CALCULATE_EQUATION_URL);
    }

    private URL getCalculationResultUrl(final Long id) throws MalformedURLException {
        return new URL(baseUrl,CALCULATOR_SERVICE_WAR + REST_URL+GET_CALCULATION_URL+"/"+id);
    }

}
