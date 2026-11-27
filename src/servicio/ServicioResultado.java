package servicio;

import excepciones.EntidadNoEncontradaException;
import java.util.ArrayList;
import modelo.enums.GrupoImpacto;
import modelo.investigadorA.Investigador;
import modelo.resultadoA.Articulo;
import modelo.resultadoA.CapituloLibro;
import modelo.resultadoA.Ponencia;
import modelo.resultadoA.ResultadoInvestigativo;

public class ServicioResultado {

    private final SistemaCipel sistema;

    public ServicioResultado() {
        this.sistema = SistemaCipel.getInstancia();
    }

    public Articulo crearArticulo(Investigador investigador, String titulo, int year, int numero,
                                  int volumen, String paginas, GrupoImpacto grupo) {
        Articulo articulo = new Articulo(titulo, year, numero, volumen, paginas, grupo);
        registrar(investigador, articulo);
        return articulo;
    }

    public CapituloLibro crearCapitulo(Investigador investigador, String titulo, int year, String editores,
                                       String editorial, String issn, String nombreLibro, int volumen) {
        CapituloLibro capitulo = new CapituloLibro(titulo, year, editores, editorial, issn, nombreLibro, volumen);
        registrar(investigador, capitulo);
        return capitulo;
    }

    public Ponencia crearPonencia(Investigador investigador, String titulo, int year, String lugar,
                                  String fecha, String isbn) {
        Ponencia ponencia = new Ponencia(titulo, year, lugar, fecha, isbn);
        registrar(investigador, ponencia);
        return ponencia;
    }

    private void registrar(Investigador investigador, ResultadoInvestigativo resultado) {
        if (investigador == null) {
            throw new excepciones.ValidacionException("El investigador es obligatorio.");
        }
        investigador.agregarResultado(resultado);
        this.sistema.getResultados().add(resultado);
    }

    public void eliminarResultado(Investigador investigador, ResultadoInvestigativo resultado) {
        if (investigador == null || resultado == null
                || !investigador.getResultados().contains(resultado)) {
            throw new EntidadNoEncontradaException("El resultado no pertenece al investigador.");
        }
        investigador.getResultados().remove(resultado);
        this.sistema.getResultados().remove(resultado);
    }

    public ArrayList<ResultadoInvestigativo> listarResultados() {
        return this.sistema.getResultados();
    }

    public ArrayList<ResultadoInvestigativo> listarPorInvestigador(Investigador investigador) {
        ArrayList<ResultadoInvestigativo> lista = new ArrayList<ResultadoInvestigativo>();
        if (investigador != null) {
            lista = investigador.getResultados();
        }
        return lista;
    }
}