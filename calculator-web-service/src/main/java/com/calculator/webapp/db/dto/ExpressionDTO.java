package com.calculator.webapp.db.dto;

import javassist.expr.Expr;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "calculator_expressions")
@NamedQuery(name = "Expressions.findAll", query = "SELECT c FROM ExpressionDTO c")
public class ExpressionDTO {

    public ExpressionDTO(){ this(""); };

    public ExpressionDTO(final String expression){
        this(expression,0.0,null);
    }

    public ExpressionDTO(final String expression,final double calculationResult,final String errorMsg){
        setExpression(expression);
        setCalculationResult(calculationResult);
        setErrorMsg(errorMsg);
    }

    @Id
    @Column @NotNull
    private String expression;

    @Column
    private double calculationResult;

    @Column
    private String errorMsg;

    public void setExpression(final String expression) {
        this.expression = expression;
    }

    public String getExpression() {
        return this.expression;
    }

    public void setCalculationResult(final double calculationResult) { this.calculationResult=calculationResult; }

    public double getCalculationResult() { return this.calculationResult; }

    public void setErrorMsg(final String responseMsg) {
        this.errorMsg = responseMsg;
    }

    public String getErrorMsg() {
        return this.errorMsg;
    }

    @Override
    public String toString(){
        return "ExpressionDTO{expression='"+this.expression+"',calculationResult="+this.calculationResult+",errorMsg='"+this.errorMsg+"'}";
    }
}
