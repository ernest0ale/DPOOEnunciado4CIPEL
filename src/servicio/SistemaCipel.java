package servicio;

import java.util.ArrayList;
import modelo.cursoA.CursoPosgrado;
import modelo.estructuraA.Departamento;
import modelo.estructuraA.Facultad;
import modelo.estructuraA.LineaInvestigacion;
import modelo.estructuraA.PlanEstudiosMaestria;
import modelo.investigadorA.Docente;
import modelo.investigadorA.Estudiante;
import modelo.resultadoA.ResultadoInvestigativo;

public class SistemaCipel {

    private static SistemaCipel instancia;

    private Facultad facultad;
    private ArrayList<Departamento> departamentos;
    private ArrayList<Docente> docentes;
    private ArrayList<Estudiante> estudiantes;
    private ArrayList<LineaInvestigacion> lineas;
    private ArrayList<CursoPosgrado> cursos;
    private ArrayList<ResultadoInvestigativo> resultados;
    private PlanEstudiosMaestria plan;

    private SistemaCipel() {
        this.departamentos = new ArrayList<Departamento>();
        this.docentes = new ArrayList<Docente>();
        this.estudiantes = new ArrayList<Estudiante>();
        this.lineas = new ArrayList<LineaInvestigacion>();
        this.cursos = new ArrayList<CursoPosgrado>();
        this.resultados = new ArrayList<ResultadoInvestigativo>();
    }

    public static SistemaCipel getInstancia() {
        if (instancia == null) {
            instancia = new SistemaCipel();
        }
        return instancia;
    }

    public Facultad getFacultad() {
        return facultad;
    }

    public void setFacultad(Facultad facultad) {
        this.facultad = facultad;
    }

    public ArrayList<Departamento> getDepartamentos() {
        return departamentos;
    }

    public ArrayList<Docente> getDocentes() {
        return docentes;
    }

    public ArrayList<Estudiante> getEstudiantes() {
        return estudiantes;
    }

    public ArrayList<LineaInvestigacion> getLineas() {
        return lineas;
    }

    public ArrayList<CursoPosgrado> getCursos() {
        return cursos;
    }

    public ArrayList<ResultadoInvestigativo> getResultados() {
        return resultados;
    }

    public PlanEstudiosMaestria getPlan() {
        return plan;
    }

    public void setPlan(PlanEstudiosMaestria plan) {
        this.plan = plan;
    }

    public boolean hayDatos() {
        return !docentes.isEmpty() || !estudiantes.isEmpty() || !cursos.isEmpty()
                || !resultados.isEmpty() || !departamentos.isEmpty();
    }

    public void limpiar() {
        this.departamentos.clear();
        this.docentes.clear();
        this.estudiantes.clear();
        this.lineas.clear();
        this.cursos.clear();
        this.resultados.clear();
        this.plan = null;
        this.facultad = null;
    }
}