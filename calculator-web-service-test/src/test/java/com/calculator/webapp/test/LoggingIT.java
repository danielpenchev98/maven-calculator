package com.calculator.webapp.test;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

@RunWith(Arquillian.class)
public class LoggingIT extends RestResourceIT {



    @Test
    public void verifyLoggingDuringCalculationOfExpression() throws Exception{
        calculationResultPage.calculate("1+1");
    }
}
