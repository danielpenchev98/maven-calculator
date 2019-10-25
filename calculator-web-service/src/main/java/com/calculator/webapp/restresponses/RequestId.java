package com.calculator.webapp.restresponses;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

public class RequestId {

    private long id;

    @JsonCreator
    public RequestId(@JsonProperty("id") long id){
        setId(id);
    }

    @JsonGetter("id")
    public long getId(){
        return id;
    }

    @JsonSetter("id")
    public void setId(final long id){
        this.id=id;
    }
}
