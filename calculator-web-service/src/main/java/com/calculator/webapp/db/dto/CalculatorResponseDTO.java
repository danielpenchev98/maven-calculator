package com.calculator.webapp.db.dto;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
    private boolean legitimacy;

    @Column
    @NotNull
    private String equation;

    @Column
    @NotNull
    private String responseMsg;

    @Temporal(TemporalType.TIMESTAMP)
    //automatically generated from Hibernate
    @Column(name = "date_of_creation")
    @NotNull
    private Date dateOfCreation;

    public CalculatorResponseDTO(final long id,final boolean legitimacy,final String equation,final String responseMsg,final Date dateOfCreation)
    {
        setId(id);
        setLegitimacy(legitimacy);
        setEquation(equation);
        setResponseMsg(responseMsg);
        setDateOfCreation(dateOfCreation);
    }

    public CalculatorResponseDTO()
    {
        this(1,false," ","Empty equation",new Date());
    }

    public long getId() {
        return this.id;
    }

    public void setId(final long id) {
        this.id=id;
    }

    public void setLegitimacy(final boolean legitimacy) {
        this.legitimacy = legitimacy;
    }

    public boolean getLegitimacy() {
        return this.legitimacy;
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

    public Date getDateOfCreation() {
        return this.dateOfCreation;
    }

    public void setDateOfCreation(final Date dateOfCreation){
        this.dateOfCreation=dateOfCreation;
    }

}
