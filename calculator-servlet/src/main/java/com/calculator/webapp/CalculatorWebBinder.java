package com.calculator.webapp;

import com.calculator.core.CalculatorApp;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class CalculatorWebBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bind(CalculatorApp.class).to(CalculatorApp.class);
        bind(ObjectMapper.class).to(ObjectMapper.class);
    }
}
