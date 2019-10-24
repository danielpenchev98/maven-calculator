package com.calculator.webapp.db.dto;

import com.calculator.webapp.quartz.PendingCalculationJob;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

import static com.calculator.webapp.db.dto.requeststatus.RequestStatus.PENDING;

@Entity
@Table(name = "calculator_responses")
@NamedQuery(name = "CalculatorResponses.findAll", query = "SELECT c FROM CalculationRequestDTO c")
@NamedQuery(name="CalculatorResponses.findAllNotCalculated", query = "SELECT c FROM CalculationRequestDTO c WHERE responseMsg='PENDING'")
public class CalculationRequestDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    @Column
    @NotNull
    private String equation;

    @Column
    @NotNull
    private String responseMsg;

    //@Column
    //private int statusCode;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "time_of_creation")
    @NotNull
    private Date timeOfCreation;

    public CalculationRequestDTO(final String equation,final String responseMsg,final Date timeOfCreation) {
        setEquation(equation);
        setResponseMsg(responseMsg);
        setTimeOfCreation(timeOfCreation);
    }

    public CalculationRequestDTO()
    {
        this("","PENDING",new Date());
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

    /*public int getStatusCode(){
        return statusCode;
    }

    public void setStatusCode(final int statusCode) {
        this.statusCode=statusCode;
    }*/

    public Date getTimeOfCreation() {
        return this.timeOfCreation;
    }

    public void setTimeOfCreation(final Date timeOfCreation){
        this.timeOfCreation=timeOfCreation;
    }

}
