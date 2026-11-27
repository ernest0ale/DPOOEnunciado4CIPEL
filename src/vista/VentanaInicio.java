package vista;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import servicio.ServicioDatosIniciales;
import servicio.SistemaCipel;

public class VentanaInicio extends JFrame {

    private static final long serialVersionUID = 1L;

    private JPanel contentPane;

    public VentanaInicio() {
        setTitle("CIPEL - Control de Actividad Investigativa");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setBounds(100, 100, 480, 480);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                cerrarPrograma();
            }
        });
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(20, 60, 20, 60));
        setContentPane(contentPane);
        contentPane.setLayout(new GridLayout(7, 1, 0, 10));

        JLabel lblTitulo = new JLabel("Control de la actividad investigativa");
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Dialog", Font.BOLD, 16));
        contentPane.add(lblTitulo);

        JButton btnManual = new JButton("Iniciar datos manualmente");
        btnManual.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirDatosManuales();
            }
        });
        contentPane.add(btnManual);

        JButton btnAutomatico = new JButton("Iniciar datos automáticamente");
        btnAutomatico.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                iniciarDatosAutomaticamente();
            }
        });
        contentPane.add(btnAutomatico);

        JButton btnVerDatos = new JButton("Ver datos");
        btnVerDatos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirVerDatos();
            }
        });
        contentPane.add(btnVerDatos);

        JButton btnReportes = new JButton("Ver reportes");
        btnReportes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirReportes();
            }
        });
        contentPane.add(btnReportes);

        JButton btnCreditos = new JButton("Ayuda y Créditos");
        btnCreditos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirCreditos();
            }
        });
        contentPane.add(btnCreditos);

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cerrarPrograma();
            }
        });
        contentPane.add(btnCerrar);

        setLocationRelativeTo(null);
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
                "¿Desea cerrar el programa?", "Cerrar", JOptionPane.YES_NO_OPTION);
        if (respuesta == JOptionPane.YES_OPTION) {
            dispose();
            System.exit(0);
        }
    }
}
