package com.calculator.webapp.test;

import com.calculator.webapp.test.pageobjects.dbclient.DatabasePage;
import com.calculator.webapp.test.pageobjects.webclient.CalculationHistoryPage;
import com.calculator.webapp.test.pageobjects.webclient.CalculationResultPage;
import org.eu.ingwar.tools.arquillian.extension.suite.annotations.ArquillianSuiteDeployment;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.importer.ZipImporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.File;
import java.net.URL;

@ArquillianSuiteDeployment
public class RestResourceIT {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    protected static DatabasePage dbPage;
    protected CalculationResultPage calculationResultPage;
 //   protected CalculationHistoryPage calculationHistoryPage;

    private static String VALID_USERNAME="admin";
    private static String VALID_PASSWORD="admin";

    @ArquillianResource
    protected URL baseUrl;

    @Deployment(testable = false)
    public static WebArchive createTestArchive() {
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
        calculationResultPage = new CalculationResultPage(baseUrl,VALID_USERNAME,VALID_PASSWORD);
        dbPage.resetStateOfDatabase();
        dbPage.setInitialTableInDataBase(DatasetPaths.EMPTY_DATASET_PATH);
      //  calculationHistoryPage = new CalculationHistoryPage(baseUrl,VALID_USERNAME,VALID_PASSWORD);
    }

}


