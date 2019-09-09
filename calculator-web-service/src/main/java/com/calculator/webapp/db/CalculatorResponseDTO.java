package com.calculator.webapp.db;

import sun.util.calendar.LocalGregorianCalendar;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "CalculationResult")
public class CalculatorResponseDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private boolean legitimacy;

    private String equation;

    private String responseMsg;

    //accuracy
    @Temporal(TemporalType.TIMESTAMP)
    //automatically generated from Hibernate
    @org.hibernate.annotations.CreationTimestamp
    private Date creationDate;

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

    public Date getCreationDate() {
        return this.creationDate;
    }
}
