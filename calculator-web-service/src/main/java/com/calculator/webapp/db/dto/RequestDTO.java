package com.calculator.webapp.db.dto;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "calculation_requests")
@NamedQuery(name = "Requests.findAll", query = "SELECT c FROM RequestDTO c")
@NamedQuery(name="Requests.findAllPending", query = "SELECT c FROM RequestDTO c WHERE statusCode =: statusCode")
public class RequestDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    @Column
    @NotNull
    private String expression;

    @Column
    private int statusCode;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "time_of_creation")
    @NotNull
    private Date timeOfCreation;

    public RequestDTO(final String expression, final Date timeOfCreation) {
        setExpression(expression);
        setTimeOfCreation(timeOfCreation);
    }

    public RequestDTO()
    {
        this("",new Date());
    }

    public long getId() {
        return this.id;
    }

    public void setId(final long id) {
        this.id=id;
    }

    public void setExpression(final String expression) {
        this.expression = expression;
    }

    public String getExpression() {
        return this.expression;
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
