package connection;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.zip.GZIPInputStream;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TrainDataConnection {

    public void create() throws Exception {

        URL url = new URL("https://rata.digitraffic.fi/api/v1/train-locations/latest/");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setConnectTimeout(5000);
        con.setReadTimeout(5000);
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept-Encoding", "gzip");

        String body = null;
        String charset = "UTF-8"; 

        try (
                InputStream gzippedResponse = con.getInputStream();  
                InputStream ungzippedResponse = new GZIPInputStream(gzippedResponse);  
                Reader reader = new InputStreamReader(ungzippedResponse, charset);  
                Writer writer = new StringWriter();) {
            char[] buffer = new char[1024];
            for (int length = 0; (length = reader.read(buffer)) > 0;) {
                writer.write(buffer, 0, length);
            }
            body = writer.toString();
            reader.close();
        }

        File file = new File("src//data//test");
        FileWriter myWriter = new FileWriter(file);

        myWriter.write(body);
        myWriter.close();



    }
}
