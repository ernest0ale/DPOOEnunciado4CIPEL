package modelo.cursoA;

import excepciones.OperacionNoPermitidaException;
import excepciones.ValidacionException;
import modelo.investigadorA.Docente;

public class MatriculaCurso {

    public static final int SIN_NOTA = 0;

    private Docente docente;
    private CursoPosgrado curso;
    private int year;
    private int nota;

    public MatriculaCurso(Docente docente, CursoPosgrado curso, int year) {
        this.nota = SIN_NOTA;
        setDocente(docente);
        setCurso(curso);
        setYear(year);
    }

    public MatriculaCurso(Docente docente, CursoPosgrado curso, int year, int nota) {
        this(docente, curso, year);
        setNota(nota);
    }

    public Docente getDocente() {
        return docente;
    }

    public void setDocente(Docente docente) {
        if (docente == null) {
            throw new ValidacionException("El docente de la matrícula es obligatorio.");
        }
        validarQueNoSeaElProfesor(docente, this.curso);
        this.docente = docente;
    }

    public CursoPosgrado getCurso() {
        return curso;
    }

    public void setCurso(CursoPosgrado curso) {
        if (curso == null) {
            throw new ValidacionException("El curso de la matrícula es obligatorio.");
        }
        validarQueNoSeaElProfesor(this.docente, curso);
        this.curso = curso;
    }

    private void validarQueNoSeaElProfesor(Docente docente, CursoPosgrado curso) {
        if (docente != null && curso != null && docente.equals(curso.getProfesor())) {
            throw new OperacionNoPermitidaException("El docente " + docente.getNombre()
                    + " es el profesor a cargo del curso \"" + curso.getTema()
                    + "\" y no puede matricularse en él.");
        }
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        if (year < 1900 || year > 2100) {
            throw new ValidacionException("El año de la matrícula debe estar entre 1900 y 2100.");
        }
        this.year = year;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        if (nota != SIN_NOTA && (nota < 2 || nota > 5)) {
            throw new ValidacionException("La nota debe estar entre 2 y 5.");
        }
        this.nota = nota;
    }

    public boolean estaEvaluada() {
        return this.nota != SIN_NOTA;
    }

    public boolean estaAprobado() {
        return this.nota >= 3;
    }

    public int obtenerCreditos() {
        int creditos = 0;
        if (this.estaAprobado()) {
            creditos = this.curso.getCreditos();
        }
        return creditos;
    }

    @Override
    public String toString() {
        String textoNota = "sin nota";
        if (estaEvaluada()) {
            textoNota = "nota " + nota;
        }
        return docente.getNombre() + " - " + curso.getTema() + " (" + year + ", " + textoNota + ")";
    }
}