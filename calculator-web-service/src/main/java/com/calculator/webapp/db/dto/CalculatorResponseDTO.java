package com.calculator.webapp.db.dto;
/*
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "calculator_responses")
@NamedQuery(name = "CalculatorResponses.findAll", query = "SELECT c FROM CalculatorResponseDTO c")
public class CalculatorResponseDTO {

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

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "time_of_creation")
    @NotNull
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

}*/
