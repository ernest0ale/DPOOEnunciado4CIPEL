package modelo.investigadorA;

import excepciones.ValidacionException;
import java.util.ArrayList;
import modelo.resultadoA.ResultadoInvestigativo;

public abstract class Investigador {

    private String nombre;
    private String ci;
    private ArrayList<ResultadoInvestigativo> resultados;

    public Investigador(String nombre, String ci) {
        this.resultados = new ArrayList<ResultadoInvestigativo>();
        setNombre(nombre);
        setCi(ci);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new ValidacionException("El nombre del investigador no puede estar vacío.");
        }
        this.nombre = nombre.trim();
    }

    public String getCi() {
        return ci;
    }

    public void setCi(String ci) {
        if (ci == null || ci.trim().isEmpty()) {
            throw new ValidacionException("El CI del investigador no puede estar vacío.");
        }
        if (!ci.trim().matches("\\d{11}")) {
            throw new ValidacionException("El CI debe tener 11 dígitos numéricos.");
        }
        this.ci = ci.trim();
    }

    public ArrayList<ResultadoInvestigativo> getResultados() {
        return resultados;
    }

    public void agregarResultado(ResultadoInvestigativo resultado) {
        if (resultado == null) {
            throw new ValidacionException("El resultado no puede ser nulo.");
        }
        this.resultados.add(resultado);
    }

    public int calcularPuntaje() {
        int total = 0;
        for (int i = 0; i < resultados.size(); i++) {
            total = total + resultados.get(i).calcularPuntaje();
        }
        return total;
    }

    public int calcularPuntaje(int year) {
        int total = 0;
        for (int i = 0; i < resultados.size(); i++) {
            ResultadoInvestigativo r = resultados.get(i);
            if (r.getYear() == year) {
                total = total + r.calcularPuntaje();
            }
        }
        return total;
    }

    @Override
    public String toString() {
        return nombre + " (" + ci + ")";
    }
}