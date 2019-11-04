package com.calculator.webapp.injectionconfig;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

public class CalculatorWeb extends ResourceConfig {
    public CalculatorWeb() {
        register(new CalculatorWebBinder());
        register(JacksonFeature.class);
        packages(true,"com.calculator.webapp.restresources");
    }
}
