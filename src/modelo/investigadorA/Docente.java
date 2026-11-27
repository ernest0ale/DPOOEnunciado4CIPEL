package modelo.investigadorA;

import excepciones.ValidacionException;
import java.util.ArrayList;
import modelo.cursoA.CursoPosgrado;
import modelo.cursoA.MatriculaCurso;
import modelo.enums.CategoriaCientifica;
import modelo.enums.CategoriaDocente;

public class Docente extends Investigador {

    private CategoriaDocente categoriaDocente;
    private CategoriaCientifica categoriaCientifica;
    private ArrayList<MatriculaCurso> matriculas;

    public Docente(String nombre, String ci, CategoriaDocente categoriaDocente, CategoriaCientifica categoriaCientifica) {
        super(nombre, ci);
        this.matriculas = new ArrayList<MatriculaCurso>();
        setCategoriaDocente(categoriaDocente);
        setCategoriaCientifica(categoriaCientifica);
    }

    public CategoriaDocente getCategoriaDocente() {
        return categoriaDocente;
    }

    public void setCategoriaDocente(CategoriaDocente categoriaDocente) {
        if (categoriaDocente == null) {
            throw new ValidacionException("La categoría docente es obligatoria.");
        }
        this.categoriaDocente = categoriaDocente;
    }

    public CategoriaCientifica getCategoriaCientifica() {
        return categoriaCientifica;
    }

    public void setCategoriaCientifica(CategoriaCientifica categoriaCientifica) {
        if (categoriaCientifica == null) {
            throw new ValidacionException("La categoría científica es obligatoria.");
        }
        this.categoriaCientifica = categoriaCientifica;
    }

    public boolean puedeImpartir() {
        return this.categoriaCientifica == CategoriaCientifica.DOCTOR;
    }

    public ArrayList<MatriculaCurso> getMatriculas() {
        return matriculas;
    }

    public void agregarMatricula(MatriculaCurso matricula) {
        if (matricula == null) {
            throw new ValidacionException("La matrícula no puede ser nula.");
        }
        this.matriculas.add(matricula);
    }

    public void emitirNota(MatriculaCurso matricula, int nota) {
        if (matricula == null) {
            throw new ValidacionException("La matrícula no puede ser nula.");
        }
        if (!this.equals(matricula.getCurso().getProfesor())) {
            throw new excepciones.OperacionNoPermitidaException("Solo el profesor del curso puede emitir la nota.");
        }
        matricula.setNota(nota);
    }

    public boolean haAprobado(CursoPosgrado curso) {
        boolean aprobado = false;
        for (int i = 0; i < matriculas.size(); i++) {
            MatriculaCurso m = matriculas.get(i);
            if (m.getCurso().equals(curso) && m.estaAprobado()) {
                aprobado = true;
            }
        }
        return aprobado;
    }

    public int calcularCreditos() {
        int total = 0;
        for (int i = 0; i < matriculas.size(); i++) {
            total = total + matriculas.get(i).obtenerCreditos();
        }
        return total;
    }

    public int calcularCreditos(int year) {
        int total = 0;
        for (int i = 0; i < matriculas.size(); i++) {
            MatriculaCurso m = matriculas.get(i);
            if (m.getYear() == year) {
                total = total + m.obtenerCreditos();
            }
        }
        return total;
    }
}
