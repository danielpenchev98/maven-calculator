package com.calculator.webapp.restresponses;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

public class CalculationResult {

    private double result;

    @JsonCreator
    public CalculationResult(@JsonProperty("result") final double result) {
        setResult(result);
    }

    @JsonSetter("result")
    public void setResult(final double result) {
        this.result=result;
    }

    @JsonGetter("result")
    public double getResult() {
        return this.result;
    }
}