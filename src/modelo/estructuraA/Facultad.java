package modelo.estructuraA;

import excepciones.ValidacionException;
import java.util.ArrayList;
import modelo.cursoA.CursoPosgrado;
import modelo.investigadorA.Docente;
import modelo.investigadorA.Estudiante;
import modelo.investigadorA.Investigador;

public class Facultad {

    private String nombre;
    private ArrayList<Departamento> departamentos;
    private ArrayList<Estudiante> estudiantes;
    private ArrayList<LineaInvestigacion> lineas;
    private PlanEstudiosMaestria plan;

    public Facultad(String nombre) {
        this.departamentos = new ArrayList<Departamento>();
        this.estudiantes = new ArrayList<Estudiante>();
        this.lineas = new ArrayList<LineaInvestigacion>();
        setNombre(nombre);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new ValidacionException("El nombre de la facultad no puede estar vacío.");
        }
        this.nombre = nombre.trim();
    }

    public ArrayList<Departamento> getDepartamentos() {
        return departamentos;
    }

    public void agregarDepartamento(Departamento departamento) {
        if (departamento == null) {
            throw new ValidacionException("El departamento no puede ser nulo.");
        }
        if (this.departamentos.contains(departamento)) {
            throw new ValidacionException("El departamento ya está registrado en la facultad.");
        }
        this.departamentos.add(departamento);
    }

    public void eliminarDepartamento(Departamento departamento) {
        if (departamento == null || !this.departamentos.contains(departamento)) {
            throw new excepciones.EntidadNoEncontradaException("El departamento no existe en la facultad.");
        }
        this.departamentos.remove(departamento);
    }

    public ArrayList<Estudiante> getEstudiantes() {
        return estudiantes;
    }

    public void agregarEstudiante(Estudiante estudiante) {
        if (estudiante == null) {
            throw new ValidacionException("El estudiante no puede ser nulo.");
        }
        if (this.estudiantes.contains(estudiante)) {
            throw new ValidacionException("El estudiante ya está registrado en la facultad.");
        }
        this.estudiantes.add(estudiante);
    }

    public void eliminarEstudiante(Estudiante estudiante) {
        if (estudiante == null || !this.estudiantes.contains(estudiante)) {
            throw new excepciones.EntidadNoEncontradaException("El estudiante no existe en la facultad.");
        }
        this.estudiantes.remove(estudiante);
    }

    public ArrayList<LineaInvestigacion> getLineas() {
        return lineas;
    }

    public void agregarLinea(LineaInvestigacion linea) {
        if (linea == null) {
            throw new ValidacionException("La línea no puede ser nula.");
        }
        if (this.lineas.contains(linea)) {
            throw new ValidacionException("La línea ya está registrada en la facultad.");
        }
        this.lineas.add(linea);
    }

    public void eliminarLinea(LineaInvestigacion linea) {
        if (linea == null || !this.lineas.contains(linea)) {
            throw new excepciones.EntidadNoEncontradaException("La línea no existe en la facultad.");
        }
        this.lineas.remove(linea);
    }

    public PlanEstudiosMaestria getPlan() {
        return plan;
    }

    public void setPlan(PlanEstudiosMaestria plan) {
        if (plan == null) {
            throw new ValidacionException("El plan de estudios no puede ser nulo.");
        }
        this.plan = plan;
    }

    public ArrayList<Investigador> obtenerInvestigadores() {
        ArrayList<Investigador> investigadores = new ArrayList<Investigador>();
        for (int i = 0; i < departamentos.size(); i++) {
            ArrayList<Docente> docentes = departamentos.get(i).getDocentes();
            for (int j = 0; j < docentes.size(); j++) {
                investigadores.add(docentes.get(j));
            }
        }
        for (int i = 0; i < estudiantes.size(); i++) {
            investigadores.add(estudiantes.get(i));
        }
        return investigadores;
    }

    public int contarCursosImpartidos(Departamento departamento, int year) {
        if (departamento == null || !this.departamentos.contains(departamento)) {
            throw new excepciones.EntidadNoEncontradaException("El departamento no existe en la facultad.");
        }
        if (this.plan == null) {
            throw new excepciones.OperacionNoPermitidaException("La facultad no tiene plan de estudios asignado.");
        }
        int total = 0;
        ArrayList<CursoPosgrado> cursos = this.plan.getCursos();
        ArrayList<Docente> docentes = departamento.getDocentes();
        for (int i = 0; i < cursos.size(); i++) {
            CursoPosgrado curso = cursos.get(i);
            Docente profesor = curso.getProfesor();
            if (profesor != null && docentes.contains(profesor) && curso.fueImpartido(year)) {
                total = total + 1;
            }
        }
        return total;
    }

    @Override
    public String toString() {
        return nombre;
    }
}