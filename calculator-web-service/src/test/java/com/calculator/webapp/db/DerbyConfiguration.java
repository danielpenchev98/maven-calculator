package com.calculator.webapp.db;

class DerbyConfiguration {
    static final String connectionUrl="jdbc:derby:memory:calculator;create=true";
    static final String RESTART_IDENTITY_COUNTER = "ALTER TABLE calculator_responses ALTER COLUMN id RESTART WITH 1";
    static final String DBUsername="";
    static final String DBPassword="";
}
