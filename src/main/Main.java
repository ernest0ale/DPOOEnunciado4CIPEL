package main;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import persistencia.PersistenciaTxt;
import vista.ManejadorErrores;
import vista.VentanaLogin;

public class Main {

    public static void main(String[] args) {
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread hilo, Throwable error) {
                JOptionPane.showMessageDialog(null,
                        "Se produjo un error inesperado: " + ManejadorErrores.obtenerMensaje(error),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    PersistenciaTxt.getInstancia().cargarTodo();
                    new VentanaLogin(null).setVisible(true);
                } catch (RuntimeException ex) {
                    ManejadorErrores.mostrar(null, ex);
                }
            }
        });
    }
}