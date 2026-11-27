package vista;

import java.awt.Component;
import javax.swing.JOptionPane;
import excepciones.EntidadNoEncontradaException;
import excepciones.OperacionNoPermitidaException;
import excepciones.ValidacionException;

public final class ManejadorErrores {

    private ManejadorErrores() {
    }

    public static void mostrar(Component padre, Exception ex) {
        String titulo = "Error";
        int tipo = JOptionPane.ERROR_MESSAGE;
        if (ex instanceof ValidacionException) {
            titulo = "Datos no válidos";
            tipo = JOptionPane.WARNING_MESSAGE;
        } else if (ex instanceof EntidadNoEncontradaException) {
            titulo = "Elemento no encontrado";
            tipo = JOptionPane.WARNING_MESSAGE;
        } else if (ex instanceof OperacionNoPermitidaException) {
            titulo = "Operación no permitida";
            tipo = JOptionPane.WARNING_MESSAGE;
        }
        JOptionPane.showMessageDialog(padre, obtenerMensaje(ex), titulo, tipo);
    }

    public static String obtenerMensaje(Throwable ex) {
        String mensaje = ex.getMessage();
        if (mensaje == null || mensaje.trim().isEmpty()) {
            mensaje = "Ocurrió un error inesperado (" + ex.getClass().getSimpleName() + ").";
        }
        return mensaje;
    }
}