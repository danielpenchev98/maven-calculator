package com.calculator.webapp.test.pageobjects.webclient;


import java.util.Date;

public class CalculatorResponseDTO {

    private long id;
    private String equation;
    private String responseMsg;
    private Date timeOfCreation;


    public CalculatorResponseDTO(final String equation,final String responseMsg,final Date timeOfCreation)
    {
        setEquation(equation);
        setResponseMsg(responseMsg);
        setTimeOfCreation(timeOfCreation);
    }

    public CalculatorResponseDTO()
    {
        this(" ","Empty equation",new Date());
    }

    public long getId() {
        return this.id;
    }

    public void setId(final long id) {
        this.id=id;
    }

    public void setEquation(final String equation) {
        this.equation = equation;
    }

    public String getEquation() {
        return this.equation;
    }

    public void setResponseMsg(final String responseMsg) {
        this.responseMsg = responseMsg;
    }

    public String getResponseMsg() {
        return this.responseMsg;
    }

    public Date getTimeOfCreation() {
        return this.timeOfCreation;
    }

    public void setTimeOfCreation(final Date timeOfCreation){
        this.timeOfCreation=timeOfCreation;
    }

}
