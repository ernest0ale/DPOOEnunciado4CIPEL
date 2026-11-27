package modelo.estructuraA;

import excepciones.ValidacionException;
import java.util.ArrayList;
import modelo.cursoA.CursoPosgrado;
import modelo.investigadorA.Docente;

public class MatriculaMaestria {

    private Docente docente;
    private PlanEstudiosMaestria plan;

    public MatriculaMaestria(Docente docente, PlanEstudiosMaestria plan) {
        setDocente(docente);
        setPlan(plan);
    }

    public Docente getDocente() {
        return docente;
    }

    public void setDocente(Docente docente) {
        if (docente == null) {
            throw new ValidacionException("El docente de la matrícula es obligatorio.");
        }
        this.docente = docente;
    }

    public PlanEstudiosMaestria getPlan() {
        return plan;
    }

    public void setPlan(PlanEstudiosMaestria plan) {
        if (plan == null) {
            throw new ValidacionException("El plan de la matrícula es obligatorio.");
        }
        this.plan = plan;
    }

    public int calcularCreditosAcumulados() {
        int total = 0;
        ArrayList<CursoPosgrado> cursos = this.plan.getCursos();
        for (int i = 0; i < cursos.size(); i++) {
            CursoPosgrado curso = cursos.get(i);
            if (this.docente.haAprobado(curso)) {
                total = total + curso.getCreditos();
            }
        }
        return total;
    }

    public ArrayList<CursoPosgrado> cursosPendientes() {
        ArrayList<CursoPosgrado> pendientes = new ArrayList<CursoPosgrado>();
        ArrayList<CursoPosgrado> cursos = this.plan.getCursos();
        for (int i = 0; i < cursos.size(); i++) {
            CursoPosgrado curso = cursos.get(i);
            if (!this.docente.haAprobado(curso)) {
                pendientes.add(curso);
            }
        }
        return pendientes;
    }

    public boolean recibeVistoBueno() {
        return this.calcularCreditosAcumulados() >= this.plan.calcularTotalCreditos()
                && this.cursosPendientes().isEmpty();
    }

    @Override
    public String toString() {
        return docente.getNombre() + " en " + plan.getNombre();
    }
}