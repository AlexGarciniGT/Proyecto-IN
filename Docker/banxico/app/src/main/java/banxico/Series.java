package banxico;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.FileWriter;
import java.util.List;

import io.github.cdimascio.dotenv.Dotenv;


public class Series implements Runnable {

    private Dotenv dotenv = Dotenv.load();
    private String token = dotenv.get("token");
    // variable con la fecha actual en este formato año-mes-dia
	private String fechaActual = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
    //La URL a consultar con los parametros de idSerie y fechas
    private String url;
    private String headerToken = dotenv.get("header_token");

    // Constructor Series
    public Series(String url) {
        this.url = String.format("%s/%s", url, this.fechaActual);
    }

	public Response readSeries() throws Exception {

        //Se especifica la versión del protocolo TLS 1.3
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory (
                        SSLContexts.createDefault(),
                        new String [] {"TLSv1.3"},null,
                        SSLConnectionSocketFactory.getDefaultHostnameVerifier());
        Response response;

        try (CloseableHttpClient httpclient = 
            HttpClients.custom().setSSLSocketFactory(sslsf).build()) {
            //Se realiza una petición GET
            HttpGet getMethod = new HttpGet(this.url);
            //Se solicita que la respuesta esté en formato JSON
            getMethod.setHeader("Content-Type", "application/json");
            //Se envía el header Bmx-Token con el token de consulta
            getMethod.setHeader(this.headerToken, this.token);

            HttpResponse httResponse = httpclient.execute(getMethod);
            StatusLine statusLine = httResponse.getStatusLine();
            //En caso de ser exitosa la petición se devuelve un estatus HTTP 200
            if  (statusLine.getStatusCode() != HttpStatus.SC_OK) {
                System.out.println("Mensaje de error " + statusLine.getReasonPhrase() );
                throw new RuntimeException ("Http codigo de error: "+statusLine);

            }

            //Se utiliza Jackson para mapear el JSON a objetos Java
            ObjectMapper mapper = new ObjectMapper();
            response = mapper.readValue(httResponse.getEntity().getContent(),Response.class);
            httpclient.close();
        }
        return response;
	}


    private  void doConsulta() {
		try {
			Response response = this.readSeries();
			Serie serie=response.getBmx().getSeries().get(0);
			this.getDatosToCSV(serie.getDatos(), serie.getTitulo());
		} catch(Exception e) {
            
            System.out.println(this.url);
            System.out.println("ERROR: "+ e.getCause());
			System.out.println("ERROR: "+e.getMessage());
		}
	}

	private void getDatosToCSV(List<DataSerie> datos, String nombreArchivo) {
		try {
            // Crea el archivo donde se va a escribir los datos
            nombreArchivo = nombreArchivo.replaceAll("\\s+", " ").replaceAll("\"", "");
            FileWriter fw = new FileWriter(String.format("src/main/resources/%s.txt", nombreArchivo));
			fw.write("Fecha"+","+"Dato"+"\n");
            for(DataSerie data:datos){
                fw.write(data.getFecha()+","+data.getDato()+"\n");
            }
            fw.close();
        } catch (Exception e) {
            
            System.out.println(this.url);
            System.out.println("ERROR: "+e.getMessage());
        }
	}

    @Override
    public void run() {
        this.doConsulta();
    }
}