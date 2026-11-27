package modelo.resultadoA;

import excepciones.ValidacionException;
import java.util.ArrayList;

public class CapituloLibro extends ResultadoInvestigativo {

    private ArrayList<String> autores;
    private String editores;
    private String editorial;
    private String issn;
    private String nombreLibro;
    private int volumen;

    public CapituloLibro(String titulo, int year, String editores, String editorial,
                         String issn, String nombreLibro, int volumen) {
        super(titulo, year);
        this.autores = new ArrayList<String>();
        setEditores(editores);
        setEditorial(editorial);
        setIssn(issn);
        setNombreLibro(nombreLibro);
        setVolumen(volumen);
    }

    public ArrayList<String> getAutores() {
        return autores;
    }

    public void agregarAutor(String autor) {
        if (autor == null || autor.trim().isEmpty()) {
            throw new ValidacionException("El autor no puede estar vacío.");
        }
        this.autores.add(autor.trim());
    }

    public String getEditores() {
        return editores;
    }

    public void setEditores(String editores) {
        if (editores == null || editores.trim().isEmpty()) {
            throw new ValidacionException("El nombre de los editores no puede estar vacío.");
        }
        this.editores = editores.trim();
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        if (editorial == null || editorial.trim().isEmpty()) {
            throw new ValidacionException("La editorial no puede estar vacía.");
        }
        this.editorial = editorial.trim();
    }

    public String getIssn() {
        return issn;
    }

    public void setIssn(String issn) {
        if (issn == null || issn.trim().isEmpty()) {
            throw new ValidacionException("El ISSN no puede estar vacío.");
        }
        this.issn = issn.trim();
    }

    public String getNombreLibro() {
        return nombreLibro;
    }

    public void setNombreLibro(String nombreLibro) {
        if (nombreLibro == null || nombreLibro.trim().isEmpty()) {
            throw new ValidacionException("El nombre del libro no puede estar vacío.");
        }
        this.nombreLibro = nombreLibro.trim();
    }

    public int getVolumen() {
        return volumen;
    }

    public void setVolumen(int volumen) {
        if (volumen <= 0) {
            throw new ValidacionException("El volumen del libro debe ser mayor que cero.");
        }
        this.volumen = volumen;
    }

    @Override
    public int calcularPuntaje() {
        return 3;
    }

    @Override
    public String obtenerTipo() {
        return "Capítulo de libro";
    }
}