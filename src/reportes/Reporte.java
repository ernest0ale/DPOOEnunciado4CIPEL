package reportes;

import java.util.ArrayList;

public interface Reporte {

    String getNombre();

    String getTitulo(int year);

    boolean requiereYear();

    String[] getColumnas();

    ArrayList<String[]> generar(int year);
}
