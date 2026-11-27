package modelo.enums;

public enum CategoriaCientifica {

    NINGUNA("Ninguna"),
    MASTER("Máster"),
    DOCTOR("Doctor");

    private final String nombre;

    private CategoriaCientifica(String nombre) {
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