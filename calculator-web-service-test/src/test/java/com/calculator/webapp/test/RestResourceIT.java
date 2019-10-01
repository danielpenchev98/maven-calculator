package com.calculator.webapp.test;

import com.calculator.webapp.test.pageobjects.dbclient.DatabasePage;
import com.calculator.webapp.test.pageobjects.webclient.ResourcePage;
import org.eu.ingwar.tools.arquillian.extension.suite.annotations.ArquillianSuiteDeployment;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.importer.ZipImporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import java.io.File;
import java.net.URL;

@ArquillianSuiteDeployment
public class RestResourceIT {


    protected static DatabasePage dbPage;

    @ArquillianResource
    protected URL baseUrl;

    protected ResourcePage resourcePage;

    @Deployment(testable = false)
    public static WebArchive createTestArchive() throws InterruptedException {
        WebArchive archive = ShrinkWrap.create(ZipImporter.class, "calculator.war")
                .importFrom(new File("target" + File.separator + "lib" + File.separator + "calculator-web-service-1.0-SNAPSHOT.war"))
                .as(WebArchive.class)
                .addAsResource("arquillian.xml")
                .addAsManifestResource("context.xml", "context.xml");
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
    public void setUp() throws Exception{
        resourcePage = new ResourcePage();
        dbPage.resetStateOfDatabase();
        dbPage.setInitialTableInDataBase(DatasetPaths.EMPTY_DATASET_PATH);
    }


}


