import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.importer.ZipImporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import static org.junit.Assert.assertEquals;


//TODO make it run from IDE
@RunWith(Arquillian.class)
public class CalculateServletIT {

    @Deployment(testable = false)
    public static WebArchive createTestArchive()
    {
       WebArchive archive = ShrinkWrap.create(ZipImporter.class,"calculator.war")
               .importFrom(new File("target"+File.separator+"lib"+File.separator+"calculator-servlet-1.0-SNAPSHOT.war"))
               .as(WebArchive.class)
               .addAsResource("arquillian.xml");
       System.out.println(archive.toString(true))
    ;
       return archive;
    }

    @Test
    public void testGetText(@ArquillianResource URL url) throws Exception
    {
        URL test=new URL(url,"calculation?equation=10%2A10");
        System.out.println(test.toString());
        test.openStream();

        HttpClient client= HttpClientBuilder.create().build();
        HttpResponse response=client.execute(new HttpGet(URI.create(test.toExternalForm())));
        BufferedReader rd=new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

        assertEquals("100.0",rd.readLine());
    }

}

