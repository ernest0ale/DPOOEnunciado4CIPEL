package servicio;

import excepciones.EntidadNoEncontradaException;
import excepciones.OperacionNoPermitidaException;
import excepciones.ValidacionException;
import java.util.ArrayList;
import modelo.estructuraA.Departamento;
import modelo.estructuraA.Facultad;
import modelo.estructuraA.LineaInvestigacion;
import modelo.estructuraA.MatriculaMaestria;
import modelo.estructuraA.ParticipacionLinea;
import modelo.estructuraA.PlanEstudiosMaestria;
import modelo.investigadorA.Docente;
import modelo.investigadorA.Estudiante;
import modelo.investigadorA.Investigador;

public class ServicioEstructura {

    private final SistemaCipel sistema;

    public ServicioEstructura() {
        this.sistema = SistemaCipel.getInstancia();
    }

    public Facultad crearFacultad(String nombre) {
        Facultad facultad = new Facultad(nombre);
        this.sistema.setFacultad(facultad);
        for (int i = 0; i < this.sistema.getDepartamentos().size(); i++) {
            facultad.agregarDepartamento(this.sistema.getDepartamentos().get(i));
        }
        for (int i = 0; i < this.sistema.getEstudiantes().size(); i++) {
            facultad.agregarEstudiante(this.sistema.getEstudiantes().get(i));
        }
        for (int i = 0; i < this.sistema.getLineas().size(); i++) {
            facultad.agregarLinea(this.sistema.getLineas().get(i));
        }
        if (this.sistema.getPlan() != null) {
            facultad.setPlan(this.sistema.getPlan());
        }
        return facultad;
    }

    public Departamento crearDepartamento(String nombre) {
        Departamento departamento = new Departamento(nombre);
        this.sistema.getDepartamentos().add(departamento);
        if (this.sistema.getFacultad() != null) {
            this.sistema.getFacultad().agregarDepartamento(departamento);
        }
        return departamento;
    }

    public void asignarDocenteADepartamento(Docente docente, Departamento departamento) {
        if (docente == null || departamento == null) {
            throw new ValidacionException("Docente y departamento son obligatorios.");
        }
        departamento.agregarDocente(docente);
    }

    public void eliminarDepartamento(Departamento departamento) {
        if (departamento == null || !this.sistema.getDepartamentos().contains(departamento)) {
            throw new EntidadNoEncontradaException("El departamento no existe.");
        }
        if (this.sistema.getFacultad() != null) {
            this.sistema.getFacultad().eliminarDepartamento(departamento);
        }
        this.sistema.getDepartamentos().remove(departamento);
    }

    public LineaInvestigacion crearLinea(String nombre, Docente responsable) {
        LineaInvestigacion linea = new LineaInvestigacion(nombre, responsable);
        this.sistema.getLineas().add(linea);
        if (this.sistema.getFacultad() != null) {
            this.sistema.getFacultad().agregarLinea(linea);
        }
        return linea;
    }

    public ParticipacionLinea agregarParticipacion(Investigador investigador, LineaInvestigacion linea, String tema) {
        ParticipacionLinea participacion = new ParticipacionLinea(investigador, linea, tema);
        linea.agregarParticipacion(participacion);
        return participacion;
    }

    public void eliminarLinea(LineaInvestigacion linea) {
        if (linea == null || !this.sistema.getLineas().contains(linea)) {
            throw new EntidadNoEncontradaException("La línea no existe.");
        }
        if (this.sistema.getFacultad() != null) {
            this.sistema.getFacultad().eliminarLinea(linea);
        }
        this.sistema.getLineas().remove(linea);
    }

    public PlanEstudiosMaestria crearPlan(String nombre) {
        PlanEstudiosMaestria plan = new PlanEstudiosMaestria(nombre);
        this.sistema.setPlan(plan);
        if (this.sistema.getFacultad() != null) {
            this.sistema.getFacultad().setPlan(plan);
        }
        for (int i = 0; i < this.sistema.getCursos().size(); i++) {
            plan.agregarCurso(this.sistema.getCursos().get(i));
        }
        return plan;
    }

    public void matricularDocenteEnPlan(Docente docente, PlanEstudiosMaestria plan) {
        if (docente == null || plan == null) {
            throw new ValidacionException("Docente y plan son obligatorios.");
        }
        plan.matricular(docente);
    }

    public void matricularDocenteEnMaestria(Docente docente) {
        if (this.sistema.getPlan() == null) {
            throw new OperacionNoPermitidaException("Debe crear primero el plan de estudios.");
        }
        matricularDocenteEnPlan(docente, this.sistema.getPlan());
    }

    public void eliminarMatriculaMaestria(MatriculaMaestria matricula) {
        if (matricula == null || this.sistema.getPlan() == null) {
            throw new ValidacionException("La matrícula y el plan son obligatorios.");
        }
        this.sistema.getPlan().eliminarMatricula(matricula);
    }

    public MatriculaMaestria buscarMatriculaMaestria(Docente docente) {
        MatriculaMaestria encontrada = null;
        if (this.sistema.getPlan() != null) {
            encontrada = this.sistema.getPlan().buscarMatricula(docente);
        }
        return encontrada;
    }

    public ArrayList<Estudiante> listarEstudiantes() {
        return this.sistema.getEstudiantes();
    }

    public ArrayList<Departamento> listarDepartamentos() {
        return this.sistema.getDepartamentos();
    }

    public ArrayList<LineaInvestigacion> listarLineas() {
        return this.sistema.getLineas();
    }
}