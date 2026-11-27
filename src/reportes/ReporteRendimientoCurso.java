package reportes;

import java.util.ArrayList;
import servicio.ServicioReportes;

/**
 * Reporte 4: rendimiento por curso de posgrado y año.
 * Responsable: Ernesto Alejandro García Seuret.
 * Clases cuyos datos procesa: CursoPosgrado, MatriculaCurso y Docente.
 * El cálculo se realiza en ServicioReportes; esta clase define el nombre, el título,
 * las columnas y delega la generación de las filas.
 */
public class ReporteRendimientoCurso implements Reporte {

    private final ServicioReportes servicio;

    public ReporteRendimientoCurso() {
        this.servicio = new ServicioReportes();
    }

    @Override
    public String getNombre() {
        return "Rendimiento por curso de posgrado y año";
    }

    @Override
    public String getTitulo(int year) {
        return "Rendimiento de los cursos de posgrado impartidos en " + year;
    }

    @Override
    public boolean requiereYear() {
        return true;
    }

    @Override
    public String[] getColumnas() {
        return new String[] {"Curso", "Profesor", "Matriculados", "Aprobados", "Suspensos", "Sin nota", "Nota promedio"};
    }

    @Override
    public ArrayList<String[]> generar(int year) {
        return this.servicio.reporteRendimientoPorCurso(year);
    }

    @Override
    public String toString() {
        return getNombre();
    }
}
