package com.calculator.webapp.injectionconfig;

import org.glassfish.jersey.server.ResourceConfig;

public class CalculatorWeb extends ResourceConfig {
    public CalculatorWeb() {
        //specifying the custom binder
        register(new CalculatorWebBinder());
        //specifying the location of the Rest resource
        packages(true,"com.calculator.webapp.restresources");
    }
}
