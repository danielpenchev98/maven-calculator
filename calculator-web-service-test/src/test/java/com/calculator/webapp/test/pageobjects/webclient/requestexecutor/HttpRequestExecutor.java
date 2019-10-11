package com.calculator.webapp.test.pageobjects.webclient.requestexecutor;

import com.calculator.webapp.restresponse.CalculationError;
import com.calculator.webapp.test.pageobjects.webclient.exception.CalculatorRestException;
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

    public Response executeGetRequest(final URL url) throws Exception {
        WebTarget webTarget = getWebTarget(url);
        Response restResponse = webTarget.request(MediaType.APPLICATION_JSON).get(Response.class);
        checkResponseStatusCode(restResponse);
        return restResponse;
    }

    private WebTarget getWebTarget(final URL resource){
        Client client = ClientBuilder.newClient()
                .register(HttpAuthenticationFeature.basic("admin","admin"))
                .register(JacksonFeature.class);
        return client.target(URI.create(resource.toExternalForm()));
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

    private String extractExceptionMessage(final Response response) {
        CalculationError error = response.readEntity(CalculationError.class);
        return error.getMessage();
    }
}
