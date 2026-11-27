package modelo.estructuraA;

import excepciones.ValidacionException;
import java.util.ArrayList;
import modelo.investigadorA.Docente;
import modelo.investigadorA.Investigador;

public class LineaInvestigacion {

    private String nombre;
    private Docente responsable;
    private ArrayList<ParticipacionLinea> participaciones;

    public LineaInvestigacion(String nombre, Docente responsable) {
        this.participaciones = new ArrayList<ParticipacionLinea>();
        setNombre(nombre);
        setResponsable(responsable);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new ValidacionException("El nombre de la línea no puede estar vacío.");
        }
        this.nombre = nombre.trim();
    }

    public Docente getResponsable() {
        return responsable;
    }

    public void setResponsable(Docente responsable) {
        if (responsable == null) {
            throw new ValidacionException("El responsable de la línea es obligatorio.");
        }
        this.responsable = responsable;
    }

    public ArrayList<ParticipacionLinea> getParticipaciones() {
        return participaciones;
    }

    public void agregarParticipacion(ParticipacionLinea participacion) {
        if (participacion == null) {
            throw new ValidacionException("La participación no puede ser nula.");
        }
        if (participacion.getLinea() != this) {
            throw new ValidacionException("La participación no corresponde a esta línea.");
        }
        if (buscarParticipacion(participacion.getInvestigador()) != null) {
            throw new ValidacionException("El investigador ya participa en esta línea.");
        }
        this.participaciones.add(participacion);
    }

    public ParticipacionLinea buscarParticipacion(Investigador investigador) {
        ParticipacionLinea encontrada = null;
        for (int i = 0; i < participaciones.size() && encontrada == null; i++) {
            if (participaciones.get(i).getInvestigador().equals(investigador)) {
                encontrada = participaciones.get(i);
            }
        }
        return encontrada;
    }

    public ArrayList<Investigador> obtenerIntegrantes() {
        ArrayList<Investigador> integrantes = new ArrayList<Investigador>();
        for (int i = 0; i < participaciones.size(); i++) {
            integrantes.add(participaciones.get(i).getInvestigador());
        }
        return integrantes;
    }

    @Override
    public String toString() {
        return nombre;
    }
}