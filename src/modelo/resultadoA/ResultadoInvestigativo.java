package modelo.resultadoA;

import excepciones.ValidacionException;

public abstract class ResultadoInvestigativo {

    private String titulo;
    private int year;

    public ResultadoInvestigativo(String titulo, int year) {
        setTitulo(titulo);
        setYear(year);
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new ValidacionException("El título del resultado no puede estar vacío.");
        }
        this.titulo = titulo.trim();
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        if (year < 1900 || year > 2100) {
            throw new ValidacionException("El año debe estar entre 1900 y 2100.");
        }
        this.year = year;
    }

    public abstract int calcularPuntaje();

    public abstract String obtenerTipo();

    @Override
    public String toString() {
        return obtenerTipo() + ": " + titulo + " (" + year + ")";
    }
}