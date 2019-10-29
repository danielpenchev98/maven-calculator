package com.calculator.webapp.db.dto.requeststatus;

public enum RequestStatus {
    PENDING(0),
    COMPLETED(1);

    private final int statusCode;

    RequestStatus(final int statusCode){
        this.statusCode=statusCode;
    }

    public int getStatusCode(){
        return this.statusCode;
    }
}
