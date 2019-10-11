package com.calculator.webapp.test;

import com.calculator.webapp.test.pageobjects.webclient.CalculationHistoryPage;
import com.calculator.webapp.test.pageobjects.webclient.CalculationResultPage;
import com.calculator.webapp.test.pageobjects.webclient.exception.UnauthorizedUserException;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class AuthenticationIT extends RestResourceIT {

    private static String INVALID_USERNAME = "invalid";
    private static String INVALID_PASSWORD = "invalid";

    @Test
    public void givenInvalidCredentialsWhenCalculatingExpressionThenError401() throws Exception {
        calculationResultPage = new CalculationResultPage(baseUrl,INVALID_USERNAME,INVALID_PASSWORD);
        expectedException.expect(UnauthorizedUserException.class);

        calculationResultPage.calculate("1+1");
    }

    @Test
    public void givenInvalidCredentialsWhenGettingHistoryThenError401() throws Exception {
        calculationHistoryPage = new CalculationHistoryPage(baseUrl,INVALID_USERNAME,INVALID_PASSWORD);
        expectedException.expect(UnauthorizedUserException.class);

        calculationHistoryPage.getCalculationHistory();
    }
}
