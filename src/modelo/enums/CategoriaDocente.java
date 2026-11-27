package modelo.enums;

public enum CategoriaDocente {

    INSTRUCTOR("Instructor"),
    ASISTENTE("Asistente"),
    PROFESOR_AUXILIAR("Profesor Auxiliar"),
    PROFESOR_TITULAR("Profesor Titular");

    private final String nombre;

    private CategoriaDocente(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }
}