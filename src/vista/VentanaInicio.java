package vista;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import servicio.ServicioDatosIniciales;
import servicio.Sesion;
import servicio.SistemaCipel;

public class VentanaInicio extends JFrame {

    private static final long serialVersionUID = 1L;

    private JPanel contentPane;

    public VentanaInicio() {
        setTitle("CIPEL - Control de Actividad Investigativa");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setBounds(100, 100, 600, 500);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                cerrarPrograma();
            }
        });

        contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        // Encabezado
        JPanel panelEncabezado = EstiloUI.crearPanelEncabezado("Control de actividad investigativa");
        panelEncabezado.setPreferredSize(new Dimension(600, 70));
        contentPane.add(panelEncabezado, BorderLayout.NORTH);

        // Cuerpo
        JPanel panelCuerpo = EstiloUI.crearPanelCuerpo();
        panelCuerpo.setLayout(new GridBagLayout());
        panelCuerpo.setBorder(new EmptyBorder(20, 20, 20, 20));
        contentPane.add(panelCuerpo, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Panel Izquierdo (Crear datos)
        JPanel panelIzquierdo = new JPanel(new GridLayout(2, 1, 0, 15));
        panelIzquierdo.setOpaque(false);
        JButton btnManual = EstiloUI.crearBotonPrimario("Iniciar datos manualmente");
        btnManual.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirDatosManuales();
            }
        });
        JButton btnAutomatico = EstiloUI.crearBotonPrimario("Iniciar datos automáticamente");
        btnAutomatico.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                iniciarDatosAutomaticamente();
            }
        });
        panelIzquierdo.add(btnManual);
        panelIzquierdo.add(btnAutomatico);

        // Panel Derecho (Consultas)
        JPanel panelDerecho = new JPanel(new GridLayout(2, 1, 0, 15));
        panelDerecho.setOpaque(false);
        JButton btnVerDatos = EstiloUI.crearBotonPrimario("Ver datos");
        btnVerDatos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirVerDatos();
            }
        });
        JButton btnReportes = EstiloUI.crearBotonPrimario("Ver reportes");
        btnReportes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirReportes();
            }
        });
        panelDerecho.add(btnVerDatos);
        panelDerecho.add(btnReportes);

        // Agregar paneles al centro
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panelCuerpo.add(panelIzquierdo, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        panelCuerpo.add(panelDerecho, gbc);

        // Panel Inferior (Ayuda y Cerrar)
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelInferior.setOpaque(false);
        JButton btnCreditos = EstiloUI.crearBotonSecundario("Ayuda y Créditos");
        btnCreditos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirCreditos();
            }
        });
        JButton btnCerrar = EstiloUI.crearBotonSecundario("Cerrar");
        btnCerrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cerrarPrograma();
            }
        });
        panelInferior.add(btnCreditos);
        panelInferior.add(btnCerrar);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        panelCuerpo.add(panelInferior, gbc);

        setLocationRelativeTo(null);
     
        boolean esAdmin = Sesion.getInstancia().esAdmin();
        btnManual.setEnabled(esAdmin);
        btnAutomatico.setEnabled(esAdmin);
        btnVerDatos.setEnabled(true);  // Todos pueden ver
        btnReportes.setEnabled(true);  // Todos pueden ver
    }

    private void abrirDatosManuales() {
        try {
            new VentanaDatosManuales().setVisible(true);
        } catch (RuntimeException ex) {
            ManejadorErrores.mostrar(this, ex);
        }
    }

    private void iniciarDatosAutomaticamente() {
        try {
            boolean continuar = true;
            if (SistemaCipel.getInstancia().hayDatos()) {
                int respuesta = JOptionPane.showConfirmDialog(this,
                        "Ya existen datos registrados. ¿Desea reemplazarlos por los datos de prueba?",
                        "Iniciar datos automáticamente", JOptionPane.YES_NO_OPTION);
                continuar = respuesta == JOptionPane.YES_OPTION;
            }
            if (continuar) {
                ServicioDatosIniciales servicio = new ServicioDatosIniciales();
                servicio.cargarDatosDePrueba();
                JOptionPane.showMessageDialog(this, "Datos de prueba cargados correctamente.");
            }
        } catch (RuntimeException ex) {
            ManejadorErrores.mostrar(this, ex);
        }
    }

    private void abrirVerDatos() {
        try {
            new VentanaListados().setVisible(true);
        } catch (RuntimeException ex) {
            ManejadorErrores.mostrar(this, ex);
        }
    }

    private void abrirReportes() {
        try {
            if (!SistemaCipel.getInstancia().hayDatos()) {
                JOptionPane.showMessageDialog(this,
                        "Debe iniciar los datos (manual o automáticamente) antes de consultar los reportes.",
                        "Sin datos", JOptionPane.WARNING_MESSAGE);
            } else {
                new VentanaReportes().setVisible(true);
            }
        } catch (RuntimeException ex) {
            ManejadorErrores.mostrar(this, ex);
        }
    }

    private void abrirCreditos() {
        try {
            new VentanaCreditos().setVisible(true);
        } catch (RuntimeException ex) {
            ManejadorErrores.mostrar(this, ex);
        }
    }

    private void cerrarPrograma() {
        int respuesta = JOptionPane.showConfirmDialog(this,
                "¿Desea cerrar sesión?", "Cerrar sesión", JOptionPane.YES_NO_OPTION);
        if (respuesta == JOptionPane.YES_OPTION) {
            Sesion.getInstancia().cerrarSesion();
            dispose();
            new VentanaLogin(null).setVisible(true);
        }
    }
}