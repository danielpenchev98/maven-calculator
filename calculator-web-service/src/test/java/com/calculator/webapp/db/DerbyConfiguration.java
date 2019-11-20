package com.calculator.webapp.db;

class DerbyConfiguration {
    static final String CONNECTION_URL="jdbc:derby:memory:calculator;create=true";
    static final String RESTART_IDENTITY_COUNTER = "ALTER TABLE calculation_requests ALTER COLUMN id RESTART WITH 1";
    static final String DB_USERNAME="";
    static final String DB_PASSWORD="";
}
