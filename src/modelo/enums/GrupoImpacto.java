package modelo.enums;

public enum GrupoImpacto {

    WOS("WoS", 10),
    GRUPO_1("Grupo 1", 8),
    GRUPO_2("Grupo 2", 6),
    GRUPO_3("Grupo 3", 4),
    GRUPO_4("Grupo 4", 4);

    private final String nombre;
    private final int puntaje;

    private GrupoImpacto(String nombre, int puntaje) {
        this.nombre = nombre;
        this.puntaje = puntaje;
    }

    public String getNombre() {
        return nombre;
    }

    public int getPuntaje() {
        return puntaje;
    }

    @Override
    public String toString() {
        return nombre;
    }
}