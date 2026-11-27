package reportes;

import java.util.ArrayList;
import servicio.ServicioReportes;

/**
 * Reporte 2: ranking de investigadores por puntaje, con desglose por tipo de resultado.
 * Responsable: Armando Villavicencio Meneses.
 * Clases cuyos datos procesa: Investigador (Docente y Estudiante) y ResultadoInvestigativo (Articulo, CapituloLibro y Ponencia).
 * El cálculo se realiza en ServicioReportes; esta clase define el nombre, el título,
 * las columnas y delega la generación de las filas.
 */
public class ReporteRankingInvestigadores implements Reporte {

    private final ServicioReportes servicio;

    public ReporteRankingInvestigadores() {
        this.servicio = new ServicioReportes();
    }

    @Override
    public String getNombre() {
        return "Ranking de investigadores por puntaje";
    }

    @Override
    public String getTitulo(int year) {
        return "Ranking de investigadores por puntaje en " + year;
    }

    @Override
    public boolean requiereYear() {
        return true;
    }

    @Override
    public String[] getColumnas() {
        return new String[] {"Posición", "Investigador", "Tipo", "Total", "Artículos", "Capítulos", "Ponencias"};
    }

    @Override
    public ArrayList<String[]> generar(int year) {
        return this.servicio.reporteRankingInvestigadores(year);
    }

    @Override
    public String toString() {
        return getNombre();
    }
}
