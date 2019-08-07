import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.importer.ZipImporter;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.ScopeType;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(Arquillian.class)
public class CalculateServletIT {
    @Deployment
    public static WebArchive createTestArchive()
    {
        File[] files=Maven.resolver().loadPomFromFile("./pom.xml")
                .importDependencies().resolve().withoutTransitivity().asFile();
        //.addAsLibraries(...)
       return ShrinkWrap.create(ZipImporter.class,"calculator-servlet.war")
               .importFrom(new File("target/calculator-servlet.war"))
               .as(WebArchive.class);
    }

    @Test
    public void testGetText() throws Exception
    {
        URL url=new URL("http://localhost:8080/calculate?equation=1%2b2");
        InputStream is=url.openStream();
        BufferedReader br=new BufferedReader(new InputStreamReader(is));
        String result=br.readLine();
        String expected="3";
        assertThat(result,is(expected));
    }

}
