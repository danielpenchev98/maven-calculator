package com.calculator.webapp.test.pageobjects.webclient.requestexecutor;

import com.calculator.webapp.restresources.EquationRequestBody;
import com.calculator.webapp.restresponses.CalculationError;
import com.calculator.webapp.test.pageobjects.webclient.exception.CalculatorRestException;
import com.calculator.webapp.test.pageobjects.webclient.exception.UnauthenticatedUserException;
import com.calculator.webapp.test.pageobjects.webclient.exception.UnauthorizedUserException;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.jackson.JacksonFeature;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URL;

public class HttpRequestExecutor {

    private static final int UNAUTHORIZED = Response.Status.UNAUTHORIZED.getStatusCode();
    private static final int FORBIDDEN = Response.Status.FORBIDDEN.getStatusCode();
    private static final int OK = Response.Status.OK.getStatusCode();
    private static final int ACCEPTED = Response.Status.ACCEPTED.getStatusCode();


    private String username;
    private String password;

    public HttpRequestExecutor(final String username,final String password){
        this.username=username;
        this.password=password;
    }


    public Response executeGetRequest(final URL url) throws Exception {
        WebTarget webTarget = getWebTarget(url);
        Response restResponse = webTarget.request(MediaType.APPLICATION_JSON).get();
        checkResponseStatusCode(restResponse);
        return restResponse;
    }

    public Response executePostRequest(final URL url,final EquationRequestBody payload) throws Exception {
        WebTarget webTarget = getWebTarget(url);
        Response restResponse = webTarget.request(MediaType.APPLICATION_JSON).post(Entity.entity(payload, MediaType.APPLICATION_JSON));
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
            checkProblemWithAuthentication(response);
            checkProblemWithAuthorization(response);

            String exceptionMessage = extractExceptionMessage(response);
            throw new CalculatorRestException(exceptionMessage);
        }
    }

    private boolean isUnsuccessfulRequest(final Response response){
        return isNotOKResponseCode(response) && isNotAcceptedResponseCode(response);
    }

    private boolean isNotOKResponseCode(final Response response){
        return response.getStatus()!=OK;
    }

    private boolean isNotAcceptedResponseCode(final Response response){
        return response.getStatus()!=ACCEPTED;
    }

    private String extractExceptionMessage(final Response response) {
        CalculationError error = response.readEntity(CalculationError.class);
        return error.getMessage();
    }

    private void checkProblemWithAuthorization(final Response response) throws UnauthorizedUserException {
        if(response.getStatus()==FORBIDDEN){
            throw new UnauthorizedUserException();
        }
    }

    private void checkProblemWithAuthentication(final Response response) throws UnauthenticatedUserException {
        if(response.getStatus()==UNAUTHORIZED){
            throw new UnauthenticatedUserException();
        }
    }
}
