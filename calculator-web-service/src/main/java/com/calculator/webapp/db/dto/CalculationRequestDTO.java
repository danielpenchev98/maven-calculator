package com.calculator.webapp.db.dto;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "calculator_responses")
@NamedQuery(name = "CalculatorResponses.findAll", query = "SELECT c FROM CalculationRequestDTO c")
@NamedQuery(name="CalculatorResponses.findAllNotCalculated", query = "SELECT c FROM CalculationRequestDTO c WHERE statusCode =: statusCode")
public class CalculationRequestDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    @Column
    @NotNull
    private String equation;

    @Column
    private double calculationResult;

    @Column
    private String errorMsg;

    @Column
    private int statusCode;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "time_of_creation")
    @NotNull
    private Date timeOfCreation;

    public CalculationRequestDTO(final String equation,final Date timeOfCreation) {
        setEquation(equation);
        setTimeOfCreation(timeOfCreation);
    }

    public CalculationRequestDTO()
    {
        this("",new Date());
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

    public void setCalculationResult(final double calculationResult) { this.calculationResult=calculationResult; }

    public double getCalculationResult() { return this.calculationResult; }

    public void setErrorMsg(final String responseMsg) {
        this.errorMsg = responseMsg;
    }

    public String getErrorMsg() {
        return this.errorMsg;
    }

    public int getStatusCode(){
        return statusCode;
    }

    public void setStatusCode(final int statusCode) {
        this.statusCode=statusCode;
    }

    public Date getTimeOfCreation() {
        return this.timeOfCreation;
    }

    public void setTimeOfCreation(final Date timeOfCreation){
        this.timeOfCreation=timeOfCreation;
    }

}
