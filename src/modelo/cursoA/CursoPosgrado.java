package modelo.cursoA;

import excepciones.ValidacionException;
import java.util.ArrayList;
import modelo.investigadorA.Docente;

public class CursoPosgrado {

    private String tema;
    private String objetivos;
    private int creditos;
    private Docente profesor;
    private ArrayList<MatriculaCurso> matriculas;

    public CursoPosgrado(String tema, String objetivos, int creditos, Docente profesor) {
        this.matriculas = new ArrayList<MatriculaCurso>();
        setTema(tema);
        setObjetivos(objetivos);
        setCreditos(creditos);
        setProfesor(profesor);
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        if (tema == null || tema.trim().isEmpty()) {
            throw new ValidacionException("El tema del curso no puede estar vacío.");
        }
        this.tema = tema.trim();
    }

    public String getObjetivos() {
        return objetivos;
    }

    public void setObjetivos(String objetivos) {
        if (objetivos == null || objetivos.trim().isEmpty()) {
            throw new ValidacionException("Los objetivos del curso no pueden estar vacíos.");
        }
        this.objetivos = objetivos.trim();
    }

    public int getCreditos() {
        return creditos;
    }

    public void setCreditos(int creditos) {
        if (creditos <= 0) {
            throw new ValidacionException("Los créditos del curso deben ser mayores que cero.");
        }
        this.creditos = creditos;
    }

    public Docente getProfesor() {
        return profesor;
    }

    public void setProfesor(Docente profesor) {
        if (profesor == null) {
            throw new ValidacionException("El profesor del curso es obligatorio.");
        }
        if (!profesor.puedeImpartir()) {
            throw new excepciones.OperacionNoPermitidaException("El profesor debe tener categoría científica Doctor.");
        }
        for (int i = 0; i < matriculas.size(); i++) {
            if (matriculas.get(i).getDocente().equals(profesor)) {
                throw new excepciones.OperacionNoPermitidaException("El docente " + profesor.getNombre()
                        + " está matriculado en este curso y no puede ser su profesor.");
            }
        }
        this.profesor = profesor;
    }

    public ArrayList<MatriculaCurso> getMatriculas() {
        return matriculas;
    }

    public void agregarMatricula(MatriculaCurso matricula) {
        if (matricula == null) {
            throw new ValidacionException("La matrícula no puede ser nula.");
        }
        if (matricula.getCurso() != this) {
            throw new ValidacionException("La matrícula no corresponde a este curso.");
        }
        if (this.matriculas.contains(matricula)) {
            throw new ValidacionException("La matrícula ya está registrada en el curso.");
        }
        for (int i = 0; i < matriculas.size(); i++) {
            if (matriculas.get(i).getDocente().equals(matricula.getDocente())
                    && matriculas.get(i).getYear() == matricula.getYear()) {
                throw new ValidacionException("El docente " + matricula.getDocente().getNombre()
                        + " ya está matriculado en este curso en el año " + matricula.getYear() + ".");
            }
        }
        this.matriculas.add(matricula);
    }

    public void eliminarMatricula(MatriculaCurso matricula) {
        if (matricula == null || !this.matriculas.contains(matricula)) {
            throw new excepciones.EntidadNoEncontradaException("La matrícula no pertenece al curso.");
        }
        this.matriculas.remove(matricula);
    }

    public boolean fueImpartido(int year) {
        boolean impartido = false;
        for (int i = 0; i < matriculas.size() && !impartido; i++) {
            if (matriculas.get(i).getYear() == year) {
                impartido = true;
            }
        }
        return impartido;
    }

    public int contarMatriculados(int year) {
        int total = 0;
        for (int i = 0; i < matriculas.size(); i++) {
            if (matriculas.get(i).getYear() == year) {
                total = total + 1;
            }
        }
        return total;
    }

    public int contarAprobados(int year) {
        int total = 0;
        for (int i = 0; i < matriculas.size(); i++) {
            MatriculaCurso m = matriculas.get(i);
            if (m.getYear() == year && m.estaAprobado()) {
                total = total + 1;
            }
        }
        return total;
    }

    public double calcularNotaPromedio(int year) {
        int suma = 0;
        int cantidad = 0;
        for (int i = 0; i < matriculas.size(); i++) {
            MatriculaCurso m = matriculas.get(i);
            if (m.getYear() == year && m.estaEvaluada()) {
                suma = suma + m.getNota();
                cantidad = cantidad + 1;
            }
        }
        double promedio = 0.0;
        if (cantidad > 0) {
            promedio = (double) suma / cantidad;
        }
        return promedio;
    }

    @Override
    public String toString() {
        return tema + " - " + profesor.getNombre() + " (" + creditos + " créditos)";
    }
}