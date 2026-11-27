package reportes;

import java.util.ArrayList;
import servicio.ServicioReportes;

/**
 * Reporte 1: actividad investigativa y de posgrado por departamento en un año.
 * Responsable: Armando Villavicencio Meneses.
 * Clases cuyos datos procesa: Departamento, Docente, ResultadoInvestigativo, MatriculaCurso y CursoPosgrado (lista de listas).
 * El cálculo se realiza en ServicioReportes; esta clase define el nombre, el título,
 * las columnas y delega la generación de las filas.
 */
public class ReporteActividadDepartamento implements Reporte {

    private final ServicioReportes servicio;

    public ReporteActividadDepartamento() {
        this.servicio = new ServicioReportes();
    }

    @Override
    public String getNombre() {
        return "Actividad por departamento en un año";
    }

    @Override
    public String getTitulo(int year) {
        return "Actividad investigativa y de posgrado por departamento en " + year;
    }

    @Override
    public boolean requiereYear() {
        return true;
    }

    @Override
    public String[] getColumnas() {
        return new String[] {"Departamento", "Puntaje investigativo", "Cursos impartidos", "Créditos obtenidos"};
    }

    @Override
    public ArrayList<String[]> generar(int year) {
        return this.servicio.reporteActividadPorDepartamento(year);
    }

    @Override
    public String toString() {
        return getNombre();
    }
}
