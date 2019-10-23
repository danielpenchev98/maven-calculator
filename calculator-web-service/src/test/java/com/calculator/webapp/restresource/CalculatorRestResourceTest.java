package com.calculator.webapp.restresource;

import com.calculator.core.CalculatorApp;
import com.calculator.webapp.db.dao.CalculatorDaoImpl;
import com.calculator.webapp.db.dao.exceptions.ItemDoesNotExistException;
import com.calculator.webapp.db.dto.CalculatorResponseDTO;
import com.calculator.webapp.restresources.CalculatorRestResource;
import com.calculator.webapp.restresources.EquationRequestBody;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import javax.ws.rs.core.Response;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class CalculatorRestResourceTest {


    private CalculatorRestResource resource;

    @Mock
    private CalculatorApp mockedApp;

    @Mock
    private CalculatorDaoImpl mockedDao;

    @Before
    public void setUp() {
        resource = new CalculatorRestResource(mockedApp,mockedDao);
    }

    @Test
    public void queueCurrentRequest_idOfJob(){
        Response response=resource.queueCurrentRequest(new EquationRequestBody("1+1"));

        assertThat(response.getEntity(),is(instanceOf(Long.class)));
        verifyResponseCode(response,Response.Status.ACCEPTED.getStatusCode());
    }

    @Test
    public void doGetCalculationResult_CalculationFinished() throws Exception {
        CalculatorResponseDTO expectedResult = new CalculatorResponseDTO("1+1","2.0",new Date());
        expectedResult.setId(1l);
        Mockito.when(mockedDao.getItem(1l)).thenReturn(expectedResult);

        Response response=resource.doGetCalculationResult(1l);
        CalculatorResponseDTO actualResult = (CalculatorResponseDTO) response.getEntity();

        verifyResponseCode(response,Response.Status.OK.getStatusCode());
        verifyCalculationResult(expectedResult,actualResult);
    }

    @Test
    public void doGetCalculationResult_CalculationPending() throws Exception {
        CalculatorResponseDTO expectedResult = new CalculatorResponseDTO("1+1","Not evaluated",new Date());
        expectedResult.setId(1l);
        Mockito.when(mockedDao.getItem(1l)).thenReturn(expectedResult);

        Response response=resource.doGetCalculationResult(1l);

        assertThat(response.hasEntity(),is(false));
        verifyResponseCode(response,Response.Status.ACCEPTED.getStatusCode());
    }

    @Test
    public void doGetCalculationResult_CalculationMissing() throws Exception {
        Mockito.when(mockedDao.getItem(1l)).thenThrow(new ItemDoesNotExistException("Item not found"));

        Response response=resource.doGetCalculationResult(1l);

        assertThat(response.hasEntity(),is(false));
        verifyResponseCode(response,Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    public void doGetCalculationHistory_ExpectedResponseObject(){
        List<CalculatorResponseDTO> expectedHistory = new ArrayList<>();

        Mockito.when(mockedDao.getAllItems()).thenReturn(new ArrayList<>());

        Response response=resource.doGetCalculationHistory();
        List<CalculatorResponseDTO> actualHistory = (List<CalculatorResponseDTO>) response.getEntity();

        verifyResponseCode(response,Response.Status.OK.getStatusCode());
        assertThat(actualHistory.size(),is(expectedHistory.size()));
    }

    private void verifyResponseCode(final Response actualResponse, final int expectedStatusCode){
        assertThat(actualResponse.getStatus(),is(expectedStatusCode));
    }

    private void verifyCalculationResult(final CalculatorResponseDTO expectedResponseBody, final CalculatorResponseDTO actualResponseBody) {
        assertThat(actualResponseBody.getId(), is(expectedResponseBody.getId()));
        assertThat(actualResponseBody.getEquation(), is(expectedResponseBody.getEquation()));
        assertThat(actualResponseBody.getResponseMsg(), is(expectedResponseBody.getResponseMsg()));
        assertThat(actualResponseBody.getTimeOfCreation(),is(expectedResponseBody.getTimeOfCreation()));
    }


}