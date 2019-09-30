package com.calculator.webapp.test;

import com.calculator.webapp.test.pageobjects.dbclient.DatabasePage;
import com.calculator.webapp.test.pageobjects.webclient.ResourcePage;
import com.calculator.webapp.test.pageobjects.webclient.requestobjects.CalculationHistoryRequestUrl;
import com.calculator.webapp.test.pageobjects.webclient.requestobjects.CalculationResultRequestUrl;
import org.dbunit.dataset.ITable;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.importer.ZipImporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.json.JSONArray;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;


import java.io.File;
import java.net.URL;

public class CalculateRestServiceIT {

    protected static DatabasePage dbPage;
    protected ResourcePage resourcePage;
    @ArquillianResource
    protected URL baseUrl;


    @Deployment(testable = false)
    public static WebArchive createTestArchive(){
        WebArchive archive = ShrinkWrap.create(ZipImporter.class, "calculator.war")
                .importFrom(new File("target" + File.separator + "lib" + File.separator + "calculator-web-service-1.0-SNAPSHOT.war"))
                .as(WebArchive.class)
                .addAsResource("arquillian.xml")
                .addAsManifestResource("context.xml","context.xml");
        return archive;
    }

    @BeforeClass
    public static void setUpDB() throws Exception {
        dbPage = new DatabasePage();
    }

    @AfterClass
    public static void tearDownDB() throws Exception {
      dbPage.closeDbConnection();
    }

    @Before
    public void setUp() throws Exception {
        dbPage.resetStateOfDatabase();
        dbPage.setInitialTableInDataBase(DatasetPaths.EMPTY_DATASET_PATH);
    }

}

