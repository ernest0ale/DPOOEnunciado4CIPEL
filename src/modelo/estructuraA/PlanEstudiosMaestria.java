package modelo.estructuraA;

import excepciones.ValidacionException;
import java.util.ArrayList;
import modelo.cursoA.CursoPosgrado;
import modelo.investigadorA.Docente;

public class PlanEstudiosMaestria {

    private String nombre;
    private ArrayList<CursoPosgrado> cursos;
    private ArrayList<MatriculaMaestria> matriculas;

    public PlanEstudiosMaestria(String nombre) {
        this.cursos = new ArrayList<CursoPosgrado>();
        this.matriculas = new ArrayList<MatriculaMaestria>();
        setNombre(nombre);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new ValidacionException("El nombre del plan no puede estar vacío.");
        }
        this.nombre = nombre.trim();
    }

    public ArrayList<CursoPosgrado> getCursos() {
        return cursos;
    }

    public void agregarCurso(CursoPosgrado curso) {
        if (curso == null) {
            throw new ValidacionException("El curso no puede ser nulo.");
        }
        if (this.cursos.contains(curso)) {
            throw new ValidacionException("El curso ya pertenece al plan.");
        }
        this.cursos.add(curso);
    }

    public void eliminarCurso(CursoPosgrado curso) {
        if (curso == null || !this.cursos.contains(curso)) {
            throw new excepciones.EntidadNoEncontradaException("El curso no pertenece al plan.");
        }
        this.cursos.remove(curso);
    }

    public boolean contieneCurso(CursoPosgrado curso) {
        return this.cursos.contains(curso);
    }

    public int calcularTotalCreditos() {
        int total = 0;
        for (int i = 0; i < cursos.size(); i++) {
            total = total + cursos.get(i).getCreditos();
        }
        return total;
    }

    public ArrayList<MatriculaMaestria> getMatriculas() {
        return matriculas;
    }

    public MatriculaMaestria buscarMatricula(Docente docente) {
        MatriculaMaestria encontrada = null;
        for (int i = 0; i < matriculas.size() && encontrada == null; i++) {
            if (matriculas.get(i).getDocente().equals(docente)) {
                encontrada = matriculas.get(i);
            }
        }
        return encontrada;
    }

    public void matricular(Docente docente) {
        if (docente == null) {
            throw new ValidacionException("El docente no puede ser nulo.");
        }
        if (buscarMatricula(docente) != null) {
            throw new ValidacionException("El docente ya está matriculado en el plan.");
        }
        MatriculaMaestria matricula = new MatriculaMaestria(docente, this);
        this.matriculas.add(matricula);
    }

    public void eliminarMatricula(MatriculaMaestria matricula) {
        if (matricula == null || !this.matriculas.contains(matricula)) {
            throw new excepciones.EntidadNoEncontradaException("La matrícula no pertenece al plan.");
        }
        this.matriculas.remove(matricula);
    }

    @Override
    public String toString() {
        return nombre;
    }
}