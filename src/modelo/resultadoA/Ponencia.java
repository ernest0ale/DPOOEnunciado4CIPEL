package modelo.resultadoA;

import excepciones.ValidacionException;

public class Ponencia extends ResultadoInvestigativo {

    private String lugar;
    private String fecha;
    private String isbn;

    public Ponencia(String titulo, int year, String lugar, String fecha, String isbn) {
        super(titulo, year);
        setLugar(lugar);
        setFecha(fecha);
        setIsbn(isbn);
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        if (lugar == null || lugar.trim().isEmpty()) {
            throw new ValidacionException("El lugar de la ponencia no puede estar vacío.");
        }
        this.lugar = lugar.trim();
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        if (fecha == null || fecha.trim().isEmpty()) {
            throw new ValidacionException("La fecha de la ponencia no puede estar vacía.");
        }
        this.fecha = fecha.trim();
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        if (isbn == null || isbn.trim().isEmpty()) {
            throw new ValidacionException("El ISBN de la ponencia no puede estar vacío.");
        }
        this.isbn = isbn.trim();
    }

    @Override
    public int calcularPuntaje() {
        return 1;
    }

    @Override
    public String obtenerTipo() {
        return "Ponencia";
    }
}