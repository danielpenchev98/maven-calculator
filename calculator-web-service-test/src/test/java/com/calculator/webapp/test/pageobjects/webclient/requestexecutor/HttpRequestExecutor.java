package com.calculator.webapp.test.pageobjects.webclient.requestexecutor;

import com.calculator.webapp.restresponse.CalculationError;
import com.calculator.webapp.test.pageobjects.webclient.exception.CalculatorRestException;
import com.calculator.webapp.test.pageobjects.webclient.exception.UnauthorizedUserException;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.client.filter.CsrfProtectionFilter;
import org.imixs.melman.FormAuthenticator;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URL;

public class HttpRequestExecutor {

    private String username;
    private String password;

    public HttpRequestExecutor(final String username,final String password){
        this.username=username;
        this.password=password;
    }

    public Response executeGetRequest(final URL url) throws Exception {
        WebTarget webTarget = getWebTarget(url);
        Response restResponse = webTarget.request(MediaType.APPLICATION_JSON).get(Response.class);
        checkResponseStatusCode(restResponse);
        return restResponse;
    }

    private WebTarget getWebTarget(final URL resource){
        Client client = ClientBuilder.newClient()
                .register(HttpAuthenticationFeature.basic(username,password))
                .register(JacksonFeature.class);
        return client.target(URI.create(resource.toExternalForm()));
    }

    private void checkResponseStatusCode(final Response response) throws Exception {
        if(isUnsuccessfulRequest(response)) {
            verifyProblemWithAuthorization(response);
            String exceptionMessage = extractExceptionMessage(response);
            throw new CalculatorRestException(exceptionMessage);
        }
    }

    private void verifyProblemWithAuthorization(final Response response) throws UnauthorizedUserException {
        if(response.getStatus()==Response.Status.UNAUTHORIZED.getStatusCode()){
            throw new UnauthorizedUserException();
        }
    }

    private boolean isUnsuccessfulRequest(final Response response){
        return response.getStatus()!=Response.Status.OK.getStatusCode();
    }

    private String extractExceptionMessage(final Response response) {
        CalculationError error = response.readEntity(CalculationError.class);
        return error.getMessage();
    }
}
