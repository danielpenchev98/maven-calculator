package com.calculator.webapp.injectionconfig;

import com.calculator.webapp.db.dao.CalculatorDaoImpl;
import org.glassfish.hk2.utilities.binding.AbstractBinder;


//specifies how dependency injection should create the classes
public class CalculatorWebBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bind(CalculatorDaoImpl.class).to(CalculatorDaoImpl.class);
    }
}
