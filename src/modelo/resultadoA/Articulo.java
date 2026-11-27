package modelo.resultadoA;

import excepciones.ValidacionException;
import modelo.enums.GrupoImpacto;

public class Articulo extends ResultadoInvestigativo {

    private int numero;
    private int volumen;
    private String paginas;
    private GrupoImpacto grupoImpacto;

    public Articulo(String titulo, int year, int numero, int volumen, String paginas, GrupoImpacto grupoImpacto) {
        super(titulo, year);
        setNumero(numero);
        setVolumen(volumen);
        setPaginas(paginas);
        setGrupoImpacto(grupoImpacto);
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        if (numero <= 0) {
            throw new ValidacionException("El número del artículo debe ser mayor que cero.");
        }
        this.numero = numero;
    }

    public int getVolumen() {
        return volumen;
    }

    public void setVolumen(int volumen) {
        if (volumen <= 0) {
            throw new ValidacionException("El volumen del artículo debe ser mayor que cero.");
        }
        this.volumen = volumen;
    }

    public String getPaginas() {
        return paginas;
    }

    public void setPaginas(String paginas) {
        if (paginas == null || paginas.trim().isEmpty()) {
            throw new ValidacionException("Las páginas del artículo no pueden estar vacías.");
        }
        this.paginas = paginas.trim();
    }

    public GrupoImpacto getGrupoImpacto() {
        return grupoImpacto;
    }

    public void setGrupoImpacto(GrupoImpacto grupoImpacto) {
        if (grupoImpacto == null) {
            throw new ValidacionException("El grupo de impacto es obligatorio.");
        }
        this.grupoImpacto = grupoImpacto;
    }

    @Override
    public int calcularPuntaje() {
        return grupoImpacto.getPuntaje();
    }

    @Override
    public String obtenerTipo() {
        return "Artículo";
    }
}