package com.calculator.webapp.db;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "calculator_responses")
@NamedQuery(name = "CalculatorResponses.findAll", query = "SELECT c FROM CalculatorResponseDTO c")
public class CalculatorResponseDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private long id;

    @Column
    private boolean legitimacy;

    @Column
    private String equation;

    @Column
    private String responseMsg;

    @Temporal(TemporalType.TIMESTAMP)
    //automatically generated from Hibernate
    @org.hibernate.annotations.CreationTimestamp
    @Column(name = "date_of_creation")

    private Date dateOfCreation;

    public long getId() {
        return this.id;
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

}
