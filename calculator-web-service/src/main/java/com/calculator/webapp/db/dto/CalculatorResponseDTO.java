package com.calculator.webapp.db.dto;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "calculator_responses")
@NamedQuery(name = "CalculatorResponses.findAll", query = "SELECT c FROM CalculatorResponseDTO c")
public class CalculatorResponseDTO {

    public CalculatorResponseDTO(final String equation){
        setEquation(equation);
    }

    public CalculatorResponseDTO(){ this(""); };

    @Id
    @Column @NotNull
    private String equation;

    @Column
    private double calculationResult;

    @Column
    private String errorMsg;

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
}
