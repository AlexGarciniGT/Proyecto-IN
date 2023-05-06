package banxico;

import banxico.Serie;
import banxico.Response;
import banxico.Series;
import banxico.DataSerie;

public class App {
    public String getGreeting() {
        return "Hello World!";
    }

    public static void main(String[] args) {
        try {
            
            Series classSeries = new Series();
			Response response = classSeries.readSeries();

			Serie serie=response.getBmx().getSeries().get(0);
			System.out.println("Serie: "+serie.getTitulo());

			for(DataSerie data:serie.getDatos()){
				//Se omiten las observaciones sin dato (N/E)
				if(data.getDato().equals("N/E")) continue;
				System.out.println("Fecha: "+data.getFecha());
				System.out.println("Dato: "+data.getDato());
			}
			
		} catch(Exception e) {
			System.out.println("ERROR: "+e.getMessage());
		}
    }
}
