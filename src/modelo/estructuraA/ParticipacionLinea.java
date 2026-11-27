package modelo.estructuraA;

import excepciones.ValidacionException;
import modelo.investigadorA.Investigador;

public class ParticipacionLinea {

    private Investigador investigador;
    private LineaInvestigacion linea;
    private String tema;

    public ParticipacionLinea(Investigador investigador, LineaInvestigacion linea, String tema) {
        setInvestigador(investigador);
        setLinea(linea);
        setTema(tema);
    }

    public Investigador getInvestigador() {
        return investigador;
    }

    public void setInvestigador(Investigador investigador) {
        if (investigador == null) {
            throw new ValidacionException("El investigador de la participación es obligatorio.");
        }
        this.investigador = investigador;
    }

    public LineaInvestigacion getLinea() {
        return linea;
    }

    public void setLinea(LineaInvestigacion linea) {
        if (linea == null) {
            throw new ValidacionException("La línea de la participación es obligatoria.");
        }
        this.linea = linea;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        if (tema == null || tema.trim().isEmpty()) {
            throw new ValidacionException("El tema de investigación no puede estar vacío.");
        }
        this.tema = tema.trim();
    }

    @Override
    public String toString() {
        return investigador.getNombre() + " - " + tema;
    }
}