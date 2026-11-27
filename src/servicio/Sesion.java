package servicio;

import modelo.investigadorA.Investigador;

public class Sesion {

    public enum Rol {
        ADMIN, DOCENTE, ESTUDIANTE
    }

    private static Sesion instancia;
    private Investigador usuario;
    private Rol rol;

    private Sesion() {
    }

    public static Sesion getInstancia() {
        if (instancia == null) {
            instancia = new Sesion();
        }
        return instancia;
    }

    public Investigador getUsuario() {
        return usuario;
    }

    public Rol getRol() {
        return rol;
    }

    public void iniciarSesion(Investigador usuario, Rol rol) {
        this.usuario = usuario;
        this.rol = rol;
    }

    public void cerrarSesion() {
        this.usuario = null;
        this.rol = null;
    }

    public boolean esAdmin() {
        return rol == Rol.ADMIN;
    }
}