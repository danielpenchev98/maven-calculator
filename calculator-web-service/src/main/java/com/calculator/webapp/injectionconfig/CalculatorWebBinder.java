package com.calculator.webapp.injectionconfig;

import com.calculator.core.CalculatorApp;
import com.calculator.webapp.db.dao.CalculatorDaoImpl;
import org.glassfish.hk2.utilities.binding.AbstractBinder;


//specifies how dependency injection should create the classes
public class CalculatorWebBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bind(CalculatorApp.class).to(CalculatorApp.class);
        bind(CalculatorDaoImpl.class).to(CalculatorDaoImpl.class);
    }
}
