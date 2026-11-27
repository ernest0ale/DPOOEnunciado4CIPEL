package servicio;

import excepciones.EntidadNoEncontradaException;
import excepciones.ValidacionException;
import java.util.ArrayList;
import modelo.cursoA.CursoPosgrado;
import modelo.cursoA.MatriculaCurso;
import modelo.estructuraA.PlanEstudiosMaestria;
import modelo.investigadorA.Docente;

public class ServicioCurso {

    private final SistemaCipel sistema;

    public ServicioCurso() {
        this.sistema = SistemaCipel.getInstancia();
    }

    public CursoPosgrado crearCurso(String tema, String objetivos, int creditos, Docente profesor) {
        CursoPosgrado curso = new CursoPosgrado(tema, objetivos, creditos, profesor);
        this.sistema.getCursos().add(curso);
        if (this.sistema.getPlan() != null) {
            this.sistema.getPlan().agregarCurso(curso);
        }
        return curso;
    }

    public void modificarCurso(CursoPosgrado curso, String tema, String objetivos, int creditos, Docente profesor) {
        if (curso == null || !this.sistema.getCursos().contains(curso)) {
            throw new EntidadNoEncontradaException("El curso no existe.");
        }
        curso.setTema(tema);
        curso.setObjetivos(objetivos);
        curso.setCreditos(creditos);
        curso.setProfesor(profesor);
    }

    public void eliminarCurso(CursoPosgrado curso) {
        if (curso == null || !this.sistema.getCursos().contains(curso)) {
            throw new EntidadNoEncontradaException("El curso no existe.");
        }
        if (this.sistema.getPlan() != null && this.sistema.getPlan().contieneCurso(curso)) {
            this.sistema.getPlan().eliminarCurso(curso);
        }
        this.sistema.getCursos().remove(curso);
    }

    public MatriculaCurso matricularDocenteEnCurso(Docente docente, CursoPosgrado curso, int year) {
        if (docente == null || curso == null) {
            throw new ValidacionException("Docente y curso son obligatorios.");
        }
        MatriculaCurso matricula = new MatriculaCurso(docente, curso, year);
        registrarMatricula(matricula);
        return matricula;
    }

    public MatriculaCurso matricularDocenteEnCurso(Docente docente, CursoPosgrado curso, int year, int nota) {
        if (docente == null || curso == null) {
            throw new ValidacionException("Docente y curso son obligatorios.");
        }
        MatriculaCurso matricula = new MatriculaCurso(docente, curso, year, nota);
        registrarMatricula(matricula);
        return matricula;
    }

    private void registrarMatricula(MatriculaCurso matricula) {
        matricula.getCurso().agregarMatricula(matricula);
        matricula.getDocente().agregarMatricula(matricula);
    }

    public void emitirNota(Docente profesor, MatriculaCurso matricula, int nota) {
        if (profesor == null || matricula == null) {
            throw new ValidacionException("Profesor y matrícula son obligatorios.");
        }
        profesor.emitirNota(matricula, nota);
    }

    public void eliminarMatricula(MatriculaCurso matricula) {
        if (matricula == null) {
            throw new ValidacionException("La matrícula es obligatoria.");
        }
        matricula.getDocente().getMatriculas().remove(matricula);
        matricula.getCurso().eliminarMatricula(matricula);
    }

    public ArrayList<CursoPosgrado> listarCursos() {
        return this.sistema.getCursos();
    }

    public ArrayList<CursoPosgrado> listarCursosImpartidosPor(Docente docente, int year) {
        ArrayList<CursoPosgrado> lista = new ArrayList<CursoPosgrado>();
        for (int i = 0; i < this.sistema.getCursos().size(); i++) {
            CursoPosgrado curso = this.sistema.getCursos().get(i);
            if (curso.getProfesor() != null && curso.getProfesor().equals(docente)
                    && curso.fueImpartido(year)) {
                lista.add(curso);
            }
        }
        return lista;
    }

    public PlanEstudiosMaestria getPlan() {
        return this.sistema.getPlan();
    }
}