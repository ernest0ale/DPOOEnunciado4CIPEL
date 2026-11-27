package persistencia;

import excepciones.ValidacionException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class PersistenciaTxt {

    private static PersistenciaTxt instancia;
    private static final String CARPETA = "datos";
    private static final String SEPARADOR = "|";

    private PersistenciaTxt() {
        crearCarpeta();
    }

    public static PersistenciaTxt getInstancia() {
        if (instancia == null) {
            instancia = new PersistenciaTxt();
        }
        return instancia;
    }

    private void crearCarpeta() {
        File carpeta = new File(CARPETA);
        if (!carpeta.exists()) {
            carpeta.mkdirs();
        }
    }

    public String getSeparador() {
        return SEPARADOR;
    }

    public ArrayList<String> leerLineas(String nombreArchivo) {
        ArrayList<String> lineas = new ArrayList<String>();
        File archivo = new File(CARPETA + File.separator + nombreArchivo);
        if (archivo.exists()) {
            BufferedReader lector = null;
            try {
                lector = new BufferedReader(new FileReader(archivo));
                String linea = lector.readLine();
                while (linea != null) {
                    if (!linea.trim().isEmpty()) {
                        lineas.add(linea);
                    }
                    linea = lector.readLine();
                }
            } catch (IOException e) {
                throw new ValidacionException("Error al leer el archivo " + nombreArchivo + ": " + e.getMessage(), e);
            } finally {
                cerrarLector(lector);
            }
        }
        return lineas;
    }

    public void escribirLineas(String nombreArchivo, ArrayList<String> lineas) {
        File archivo = new File(CARPETA + File.separator + nombreArchivo);
        BufferedWriter escritor = null;
        try {
            escritor = new BufferedWriter(new FileWriter(archivo));
            for (int i = 0; i < lineas.size(); i++) {
                escritor.write(lineas.get(i));
                escritor.newLine();
            }
        } catch (IOException e) {
            throw new ValidacionException("Error al escribir el archivo " + nombreArchivo + ": " + e.getMessage(), e);
        } finally {
            cerrarEscritor(escritor);
        }
    }

    public void agregarLinea(String nombreArchivo, String linea) {
        ArrayList<String> lineas = leerLineas(nombreArchivo);
        lineas.add(linea);
        escribirLineas(nombreArchivo, lineas);
    }

    private void cerrarLector(BufferedReader lector) {
        if (lector != null) {
            try {
                lector.close();
            } catch (IOException e) {
                throw new ValidacionException("Error al cerrar el lector: " + e.getMessage(), e);
            }
        }
    }

    private void cerrarEscritor(BufferedWriter escritor) {
        if (escritor != null) {
            try {
                escritor.close();
            } catch (IOException e) {
                throw new ValidacionException("Error al cerrar el escritor: " + e.getMessage(), e);
            }
        }
    }
}