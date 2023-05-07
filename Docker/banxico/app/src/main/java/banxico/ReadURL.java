package banxico;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ReadURL {
    // Clase que le un archivo de raiz llamado urls.txt y le un arreglo de url y devuelve un arreglo o lista
    public static String[] getUrls() {
        String[] urls = null;

        try {
            // extrae la información del arreglo de json, sin las comillas dobles
            urls = Files.readAllLines(Paths.get("urls.txt")).toArray(String[]::new);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return urls;
    }
}
