package banxico;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import banxico.Response;
import banxico.Serie;
import io.github.cdimascio.dotenv.Dotenv;


public class Series {

    static private Dotenv dotenv = Dotenv.load();
    static String token = dotenv.get("token");

	public static Response readSeries() throws Exception {
        System.out.printl(Series.token); // TODO

        //La URL a consultar con los parametros de idSerie y fechas
        String url="https://www.banxico.org.mx/SieAPIRest/service/v1/series/SF43718/datos/2023-01-01/2023-01-31";
        //Se especifica la versión del protocolo TLS 1.3
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory (
                        SSLContexts.createDefault(),
                        new String [] {"TLSv1.3"},null,
                        SSLConnectionSocketFactory.getDefaultHostnameVerifier());
        Response response;
        try (CloseableHttpClient httpclient = 
            HttpClients.custom().setSSLSocketFactory(sslsf).build()) {
            //Se realiza una petición GET
            HttpGet getMethod = new HttpGet(url);
            //Se solicita que la respuesta esté en formato JSON
            getMethod.setHeader("Content-Type", "application/json");
            //Se envía el header Bmx-Token con el token de consulta
            //Modificar por el token de consulta propio
            getMethod.setHeader("Bmx-Token", "6e3bd0938be9fff6e4f4811b7b413d111f96a8c9a0ae60abef4029b681e9b8a9");
            HttpResponse httResponse = httpclient.execute(getMethod);
            StatusLine statusLine = httResponse.getStatusLine();
            //En caso de ser exitosa la petición se devuelve un estatus HTTP 200
            if  (statusLine.getStatusCode() != HttpStatus.SC_OK) {
                System.out.println("Mensaje de error "+statusLine.getReasonPhrase() );
                throw new RuntimeException ("Http codigo de error: "+statusLine);
            }   
            //Se utiliza Jackson para mapear el JSON a objetos Java
            ObjectMapper mapper = new ObjectMapper();
            response = mapper.readValue(httResponse.getEntity().getContent(),Response.class);
            httpclient.close();
        }
        
        return response;

	}
}