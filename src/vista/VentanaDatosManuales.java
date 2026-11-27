package vista;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class VentanaDatosManuales extends JFrame {

    private static final long serialVersionUID = 1L;

    private JPanel contentPane;

    public VentanaDatosManuales() {
        setTitle("Iniciar datos manualmente");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 460, 480);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(20, 60, 20, 60));
        setContentPane(contentPane);
        contentPane.setLayout(new GridLayout(7, 1, 0, 10));

        JLabel lblTitulo = new JLabel("¿Qué desea registrar?");
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Dialog", Font.BOLD, 16));
        contentPane.add(lblTitulo);

        JButton btnEstructura = new JButton("Estructura");
        btnEstructura.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirEstructura();
            }
        });
        contentPane.add(btnEstructura);

        JButton btnInvestigadores = new JButton("Investigadores");
        btnInvestigadores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirInvestigadores();
            }
        });
        contentPane.add(btnInvestigadores);

        JButton btnCurso = new JButton("Curso de posgrado");
        btnCurso.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirCurso();
            }
        });
        contentPane.add(btnCurso);

        JButton btnMatricula = new JButton("Matrícula de posgrado");
        btnMatricula.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirMatricula();
            }
        });
        contentPane.add(btnMatricula);

        JButton btnResultado = new JButton("Resultado investigativo");
        btnResultado.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirResultado();
            }
        });
        contentPane.add(btnResultado);

        JButton btnVolver = new JButton("Volver");
        btnVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        contentPane.add(btnVolver);

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
