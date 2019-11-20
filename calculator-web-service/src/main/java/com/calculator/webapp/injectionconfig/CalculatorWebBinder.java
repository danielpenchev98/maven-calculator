package com.calculator.webapp.injectionconfig;

import com.calculator.webapp.db.dao.EquationDaoImpl;
import com.calculator.webapp.db.dao.RequestDaoImpl;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class CalculatorWebBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bind(RequestDaoImpl.class).to(RequestDaoImpl.class);
        bind(EquationDaoImpl.class).to(EquationDaoImpl.class);
    }
}
