package modelo.estructuraA;

import excepciones.ValidacionException;
import java.util.ArrayList;
import modelo.investigadorA.Docente;

public class Departamento {

    private String nombre;
    private ArrayList<Docente> docentes;

    public Departamento(String nombre) {
        this.docentes = new ArrayList<Docente>();
        setNombre(nombre);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new ValidacionException("El nombre del departamento no puede estar vacío.");
        }
        this.nombre = nombre.trim();
    }

    public ArrayList<Docente> getDocentes() {
        return docentes;
    }

    public void agregarDocente(Docente docente) {
        if (docente == null) {
            throw new ValidacionException("El docente no puede ser nulo.");
        }
        if (this.docentes.contains(docente)) {
            throw new ValidacionException("El docente ya pertenece al departamento.");
        }
        this.docentes.add(docente);
    }

    public void eliminarDocente(Docente docente) {
        if (docente == null || !this.docentes.contains(docente)) {
            throw new excepciones.EntidadNoEncontradaException("El docente no pertenece al departamento.");
        }
        this.docentes.remove(docente);
    }

    public boolean contieneDocente(Docente docente) {
        return this.docentes.contains(docente);
    }

    public int calcularPuntajeInvestigativo(int year) {
        int total = 0;
        for (int i = 0; i < docentes.size(); i++) {
            total = total + docentes.get(i).calcularPuntaje(year);
        }
        return total;
    }

    public int calcularCreditos(int year) {
        int total = 0;
        for (int i = 0; i < docentes.size(); i++) {
            total = total + docentes.get(i).calcularCreditos(year);
        }
        return total;
    }

    @Override
    public String toString() {
        return nombre;
    }
}