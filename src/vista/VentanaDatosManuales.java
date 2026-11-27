package vista;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class VentanaDatosManuales extends JFrame {

    private static final long serialVersionUID = 1L;

    private JPanel contentPane;

    public VentanaDatosManuales() {
        setTitle("Iniciar datos manualmente");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 500, 500);
        contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        JPanel panelEncabezado = EstiloUI.crearPanelEncabezado("Iniciar datos manualmente");
        panelEncabezado.setPreferredSize(new Dimension(500, 60));
        contentPane.add(panelEncabezado, BorderLayout.NORTH);

        JPanel panelCuerpo = EstiloUI.crearPanelCuerpo();
        panelCuerpo.setLayout(new BorderLayout(0, 10));
        panelCuerpo.setBorder(new EmptyBorder(20, 60, 20, 60));
        contentPane.add(panelCuerpo, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(6, 1, 0, 10));
        panelBotones.setOpaque(false);
        panelCuerpo.add(panelBotones, BorderLayout.CENTER);

        JButton btnEstructura = EstiloUI.crearBotonPrimario("Estructura");
        btnEstructura.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirEstructura();
            }
        });
        panelBotones.add(btnEstructura);

        JButton btnInvestigadores = EstiloUI.crearBotonPrimario("Investigadores");
        btnInvestigadores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirInvestigadores();
            }
        });
        panelBotones.add(btnInvestigadores);

        JButton btnCurso = EstiloUI.crearBotonPrimario("Curso de posgrado");
        btnCurso.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirCurso();
            }
        });
        panelBotones.add(btnCurso);

        JButton btnMatricula = EstiloUI.crearBotonPrimario("Matrícula de posgrado");
        btnMatricula.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirMatricula();
            }
        });
        panelBotones.add(btnMatricula);

        JButton btnResultado = EstiloUI.crearBotonPrimario("Resultado investigativo");
        btnResultado.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirResultado();
            }
        });
        panelBotones.add(btnResultado);

        JButton btnVolver = EstiloUI.crearBotonSecundario("Volver");
        btnVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        panelBotones.add(btnVolver);

        setLocationRelativeTo(null);
    }

    private void abrirEstructura() {
        try {
            new VentanaEstructura().setVisible(true);
        } catch (RuntimeException ex) {
            ManejadorErrores.mostrar(this, ex);
        }
    }

    private void abrirInvestigadores() {
        try {
            new VentanaInvestigador().setVisible(true);
        } catch (RuntimeException ex) {
            ManejadorErrores.mostrar(this, ex);
        }
    }

    private void abrirCurso() {
        try {
            new VentanaCurso().setVisible(true);
        } catch (RuntimeException ex) {
            ManejadorErrores.mostrar(this, ex);
        }
    }

    private void abrirMatricula() {
        try {
            new VentanaMatricula().setVisible(true);
        } catch (RuntimeException ex) {
            ManejadorErrores.mostrar(this, ex);
        }
    }

    private void abrirResultado() {
        try {
            new VentanaResultado().setVisible(true);
        } catch (RuntimeException ex) {
            ManejadorErrores.mostrar(this, ex);
        }
    }
}