package com.calculator.webapp.injectionconfig;

import com.calculator.core.CalculatorApp;
import com.calculator.webapp.db.CalculatorDaoImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

import javax.persistence.EntityManager;

//specifies how dependency injection should create the classes
public class CalculatorWebBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bind(CalculatorApp.class).to(CalculatorApp.class);
        bind(ObjectMapper.class).to(ObjectMapper.class);
        bind(CalculatorDaoImpl.class).to(CalculatorDaoImpl.class);
        bind(EntityManager.class).to(EntityManager.class);
    }
}
