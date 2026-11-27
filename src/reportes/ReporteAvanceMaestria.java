package reportes;

import java.util.ArrayList;
import servicio.ServicioReportes;

/**
 * Reporte 3: avance de los docentes matriculados hacia el visto bueno de la maestría.
 * Responsable: Ernesto Alejandro García Seuret.
 * Clases cuyos datos procesa: PlanEstudiosMaestria, MatriculaMaestria, CursoPosgrado y MatriculaCurso (lista de listas).
 * El cálculo se realiza en ServicioReportes; esta clase define el nombre, el título,
 * las columnas y delega la generación de las filas.
 */
public class ReporteAvanceMaestria implements Reporte {

    private final ServicioReportes servicio;

    public ReporteAvanceMaestria() {
        this.servicio = new ServicioReportes();
    }

    @Override
    public String getNombre() {
        return "Avance hacia el visto bueno de la maestría";
    }

    @Override
    public String getTitulo(int year) {
        return "Avance de los docentes matriculados hacia el visto bueno de la maestría";
    }

    @Override
    public boolean requiereYear() {
        return false;
    }

    @Override
    public String[] getColumnas() {
        return new String[] {"Docente", "Créditos acumulados", "Créditos del plan", "Cursos pendientes", "Visto bueno"};
    }

    @Override
    public ArrayList<String[]> generar(int year) {
        return this.servicio.reporteAvanceMaestria();
    }

    @Override
    public String toString() {
        return getNombre();
    }
}
