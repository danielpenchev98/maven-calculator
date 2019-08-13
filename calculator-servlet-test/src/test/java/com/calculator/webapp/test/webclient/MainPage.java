package com.calculator.webapp.test.webclient;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;

public class MainPage {

    private final URL baseUrl;

    private static final String ENCODING = "UTF-8";
    private static final String CALCULATOR_SERVLET_URL = "/calculator";
    private static final String GET_REQUEST_URL = "/api/v1/calculation";
    private static final String REQUEST_PARAMETER="equation";

    public MainPage(final URL baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getResultFromTheGeneratedPage(String input) throws IOException {
        URL fullUrl = getEncodedUrl(input);
        openConnection(fullUrl);

        HttpClient client = createHttpClient();
        HttpResponse response = client.execute(createGetRequest(fullUrl));

        return getResponse(response);
    }

    private URL getEncodedUrl(String unformattedInput) throws IOException {
        String spec = URLEncoder.encode(unformattedInput, ENCODING);
        return new URL(baseUrl,CALCULATOR_SERVLET_URL + GET_REQUEST_URL +"?"+REQUEST_PARAMETER+"="+spec);
    }

    private void openConnection(final URL url) throws IOException {
        url.openStream();
    }

    private HttpClient createHttpClient() {
        return HttpClientBuilder.create().build();
    }

    private HttpGet createGetRequest(final URL url) {
        return new HttpGet(URI.create(url.toExternalForm()));
    }

    private String getResponse(final HttpResponse response) throws IOException {
        InputStream content = response.getEntity().getContent();
        BufferedReader rd = new BufferedReader(new InputStreamReader(content));
        return rd.readLine();
    }
}
