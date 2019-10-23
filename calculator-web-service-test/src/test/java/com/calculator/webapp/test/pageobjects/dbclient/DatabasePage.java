package com.calculator.webapp.test.pageobjects.dbclient;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.filter.DefaultColumnFilter;
import org.dbunit.operation.DatabaseOperation;
import org.dbunit.util.fileloader.FlatXmlDataFileLoader;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.swing.text.html.parser.Entity;
import javax.xml.crypto.Data;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.dbunit.Assertion.assertEquals;

public class DatabasePage {

    private static final String CONNECTION_URL="jdbc:derby:memory:calculator;create=true";
    private static final String DB_USERNAME="";
    private static final String DB_PASSWORD="";
    private static final String RESTART_IDENTITY_COUNTER = "ALTER TABLE calculator_responses ALTER COLUMN id RESTART WITH 1";

    private IDatabaseConnection databaseConnection;

    public DatabasePage() throws Exception {
        databaseConnection = new DatabaseConnection(DriverManager.getConnection(CONNECTION_URL,DB_USERNAME,DB_PASSWORD));
    }

    public void closeDbConnection() throws SQLException {
        databaseConnection.getConnection().close();
    }


    public void setInitialTableInDataBase(final String pathToDataSet) throws Exception {
        IDataSet initialDataSet = getDataSet(pathToDataSet);
        uploadDataSet(initialDataSet);
    }

    public ITable getTableFromDataset(final String datasetPath,final String nameOfTable) throws DataSetException {
        IDataSet dataSet = getDataSet(datasetPath);
        return dataSet.getTable(nameOfTable);
    }

    private IDataSet getDataSet(final String path) {
        return new FlatXmlDataFileLoader().load(path);
    }

    public void verifyTableEquality(final ITable expected, final ITable actual) throws DatabaseUnitException {
        assertEquals(expected,actual);
    }

    public void uploadDataSet(final IDataSet wantedDataSet) throws Exception {
        DatabaseOperation.CLEAN_INSERT.execute(databaseConnection, wantedDataSet);
    }

    public void resetStateOfDatabase() throws SQLException {
        PreparedStatement statement = databaseConnection.getConnection().prepareStatement(RESTART_IDENTITY_COUNTER);
        statement.executeUpdate();
    }

    public ITable getTableFromDatabase(final String tableName) throws Exception {
        return databaseConnection.createDataSet().getTable(tableName);
    }

    public ITable getFilteredTableFromDatabase(final String nameOfTable,final String[] columnsToFilter) throws Exception {
        ITable table = getTableFromDatabase(nameOfTable);
        return getFilteredTable(table,columnsToFilter);
    }

    public ITable getFilteredTableFromDataset(final String nameOfTable,final String datasetPath, final String[] columnsToFilter) throws Exception {
        ITable table = getTableFromDataset(datasetPath,nameOfTable);
        return getFilteredTable(table,columnsToFilter);
    }

    private ITable getFilteredTable(final ITable table,final String[]columnsToFilter) throws DataSetException {
        return DefaultColumnFilter.excludedColumnsTable(table, columnsToFilter);
    }


}
