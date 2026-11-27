package vista;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import modelo.cursoA.CursoPosgrado;
import modelo.cursoA.MatriculaCurso;
import modelo.investigadorA.Docente;
import modelo.investigadorA.Investigador;
import servicio.ServicioCurso;
import servicio.ServicioInvestigador;
import util.Validador;

public class VentanaMatricula extends JFrame {

    private static final long serialVersionUID = 1L;

    private JPanel contentPane;
    private JComboBox<Docente> cmbInvestigador;
    private JComboBox<CursoPosgrado> cmbCurso;
    private JTextField txtYear;

    private ServicioCurso servicioCurso = new ServicioCurso();
    private ServicioInvestigador servicioInvestigador = new ServicioInvestigador();

    public VentanaMatricula() {
        setTitle("Matrícula de posgrado");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 800, 320);
        contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        JPanel panelEncabezado = EstiloUI.crearPanelEncabezado("Matrícula de posgrado");
        panelEncabezado.setPreferredSize(new Dimension(800, 60));
        contentPane.add(panelEncabezado, BorderLayout.NORTH);

        JPanel panelCuerpo = EstiloUI.crearPanelCuerpo();
        panelCuerpo.setLayout(new BorderLayout(0, 10));
        panelCuerpo.setBorder(new EmptyBorder(15, 15, 15, 15));
        contentPane.add(panelCuerpo, BorderLayout.CENTER);

        JPanel panelCampos = new JPanel();
        panelCampos.setLayout(new GridLayout(3, 2, 8, 8));
        panelCampos.setOpaque(false);
        panelCuerpo.add(panelCampos, BorderLayout.CENTER);

        panelCampos.add(new JLabel("Investigador (docente):"));
        cmbInvestigador = new JComboBox<Docente>();
        panelCampos.add(cmbInvestigador);

        panelCampos.add(new JLabel("Curso de posgrado:"));
        cmbCurso = new JComboBox<CursoPosgrado>();
        panelCampos.add(cmbCurso);

        panelCampos.add(new JLabel("Año:"));
        txtYear = EstiloUI.crearCampoTexto(4);
        panelCampos.add(txtYear);

        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(1, 2, 10, 10));
        panelBotones.setOpaque(false);
        panelCuerpo.add(panelBotones, BorderLayout.SOUTH);

        JButton btnMatricular = EstiloUI.crearBotonPrimario("Matricular");
        btnMatricular.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                matricular();
            }
        });
        panelBotones.add(btnMatricular);

        JButton btnCerrar = EstiloUI.crearBotonSecundario("Cerrar");
        btnCerrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        panelBotones.add(btnCerrar);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                cargarDatos();
            }
        });
        setLocationRelativeTo(null);
    }

    private void cargarDatos() {
        try {
            cmbInvestigador.removeAllItems();
            for (int i = 0; i < servicioInvestigador.listarInvestigadores().size(); i++) {
                Investigador inv = servicioInvestigador.listarInvestigadores().get(i);
                if (inv instanceof Docente) {
                    cmbInvestigador.addItem((Docente) inv);
                }
            }
            cmbCurso.removeAllItems();
            for (int i = 0; i < servicioCurso.listarCursos().size(); i++) {
                cmbCurso.addItem(servicioCurso.listarCursos().get(i));
            }
        } catch (RuntimeException ex) {
            ManejadorErrores.mostrar(this, ex);
        }
    }

    private void matricular() {
        try {
            Docente docente = (Docente) cmbInvestigador.getSelectedItem();
            CursoPosgrado curso = (CursoPosgrado) cmbCurso.getSelectedItem();
            int year = Validador.parsearEntero(txtYear.getText(), "año");
            MatriculaCurso matricula = servicioCurso.matricularDocenteEnCurso(docente, curso, year);
            JOptionPane.showMessageDialog(this, "Matrícula registrada: " + matricula
                    + "\nLa nota se asigna desde la ventana Curso de posgrado.");
            txtYear.setText("");
        } catch (RuntimeException ex) {
            ManejadorErrores.mostrar(this, ex);
        }
    }
}