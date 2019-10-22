package com.calculator.webapp.restresources;

public class EquationRequestBody {
    private String equation;

    public EquationRequestBody(){this("");}

    public EquationRequestBody(final String equation){
        this.equation=equation;
    }

    public void setEquation(final String equation){
        this.equation=equation;
    }
    public String getEquation(){return this.equation;}
}
