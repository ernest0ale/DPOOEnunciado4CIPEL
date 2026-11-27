package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
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
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import excepciones.ValidacionException;
import modelo.cursoA.CursoPosgrado;
import modelo.cursoA.MatriculaCurso;
import modelo.investigadorA.Docente;
import modelo.investigadorA.Investigador;
import servicio.ServicioCurso;
import servicio.ServicioInvestigador;
import util.Validador;

public class VentanaCurso extends JFrame {

    private static final long serialVersionUID = 1L;

    private JPanel contentPane;
    private JTextField txtTema;
    private JTextField txtObjetivos;
    private JTextField txtCreditos;
    private JComboBox<Docente> cmbProfesor;
    private JLabel lblAviso;
    private JComboBox<CursoPosgrado> cmbCursoNota;
    private JLabel lblProfesorCurso;
    private JComboBox<MatriculaCurso> cmbMatriculado;
    private JTextField txtNota;

    private ServicioCurso servicioCurso = new ServicioCurso();
    private ServicioInvestigador servicioInvestigador = new ServicioInvestigador();

    public VentanaCurso() {
        setTitle("Curso de posgrado");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 780, 340);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(15, 15, 15, 15));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 10));

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        contentPane.add(tabbedPane, BorderLayout.CENTER);

        JPanel panelCrear = new JPanel();
        panelCrear.setBorder(new EmptyBorder(10, 10, 10, 10));
        panelCrear.setLayout(new BorderLayout(0, 8));
        tabbedPane.addTab("Crear curso", null, panelCrear, null);

        JPanel camposCrear = new JPanel();
        camposCrear.setLayout(new GridLayout(4, 2, 8, 8));
        panelCrear.add(camposCrear, BorderLayout.CENTER);

        camposCrear.add(new JLabel("Tema:"));
        txtTema = new JTextField();
        camposCrear.add(txtTema);

        camposCrear.add(new JLabel("Objetivos:"));
        txtObjetivos = new JTextField();
        camposCrear.add(txtObjetivos);

        camposCrear.add(new JLabel("Créditos:"));
        txtCreditos = new JTextField();
        camposCrear.add(txtCreditos);

        camposCrear.add(new JLabel("Profesor a cargo (Doctor):"));
        cmbProfesor = new JComboBox<Docente>();
        camposCrear.add(cmbProfesor);

        JPanel surCrear = new JPanel();
        surCrear.setLayout(new BorderLayout(0, 4));
        panelCrear.add(surCrear, BorderLayout.SOUTH);

        lblAviso = new JLabel(" ");
        lblAviso.setForeground(new Color(150, 60, 0));
        surCrear.add(lblAviso, BorderLayout.NORTH);

        JButton btnCrear = new JButton("Crear curso");
        btnCrear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                crearCurso();
            }
        });
        surCrear.add(btnCrear, BorderLayout.SOUTH);

        JPanel panelNota = new JPanel();
        panelNota.setBorder(new EmptyBorder(10, 10, 10, 10));
        panelNota.setLayout(new BorderLayout(0, 8));
        tabbedPane.addTab("Asignar nota", null, panelNota, null);

        JPanel camposNota = new JPanel();
        camposNota.setLayout(new GridLayout(4, 2, 8, 8));
        panelNota.add(camposNota, BorderLayout.CENTER);

        camposNota.add(new JLabel("Curso:"));
        cmbCursoNota = new JComboBox<CursoPosgrado>();
        cmbCursoNota.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarMatriculados();
            }
        });
        camposNota.add(cmbCursoNota);

        camposNota.add(new JLabel("Profesor a cargo:"));
        lblProfesorCurso = new JLabel("-");
        camposNota.add(lblProfesorCurso);

        camposNota.add(new JLabel("Docente matriculado:"));
        cmbMatriculado = new JComboBox<MatriculaCurso>();
        camposNota.add(cmbMatriculado);

        camposNota.add(new JLabel("Nota (2-5):"));
        txtNota = new JTextField();
        camposNota.add(txtNota);

        JButton btnNota = new JButton("Asignar nota");
        btnNota.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                asignarNota();
            }
        });
        panelNota.add(btnNota, BorderLayout.SOUTH);

        JPanel panelCerrar = new JPanel();
        panelCerrar.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));
        contentPane.add(panelCerrar, BorderLayout.SOUTH);

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        panelCerrar.add(btnCerrar);

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
            cmbProfesor.removeAllItems();
            for (int i = 0; i < servicioInvestigador.listarInvestigadores().size(); i++) {
                Investigador inv = servicioInvestigador.listarInvestigadores().get(i);
                if (inv instanceof Docente && ((Docente) inv).puedeImpartir()) {
                    cmbProfesor.addItem((Docente) inv);
                }
            }
            if (cmbProfesor.getItemCount() == 0) {
                lblAviso.setText("No hay docentes con categoría científica Doctor. Regístrelos en Investigadores.");
            } else {
                lblAviso.setText(" ");
            }
            cargarCursos();
        } catch (RuntimeException ex) {
            ManejadorErrores.mostrar(this, ex);
        }
    }

    private void cargarCursos() {
        cmbCursoNota.removeAllItems();
        for (int i = 0; i < servicioCurso.listarCursos().size(); i++) {
            cmbCursoNota.addItem(servicioCurso.listarCursos().get(i));
        }
        actualizarMatriculados();
    }

    private void actualizarMatriculados() {
        try {
            cmbMatriculado.removeAllItems();
            CursoPosgrado curso = (CursoPosgrado) cmbCursoNota.getSelectedItem();
            if (curso == null) {
                lblProfesorCurso.setText("-");
            } else {
                lblProfesorCurso.setText(curso.getProfesor().getNombre());
                for (int i = 0; i < curso.getMatriculas().size(); i++) {
                    cmbMatriculado.addItem(curso.getMatriculas().get(i));
                }
            }
        } catch (RuntimeException ex) {
            ManejadorErrores.mostrar(this, ex);
        }
    }

    private void crearCurso() {
        try {
            int creditos = Validador.parsearEntero(txtCreditos.getText(), "créditos");
            CursoPosgrado curso = servicioCurso.crearCurso(
                    txtTema.getText(), txtObjetivos.getText(), creditos,
                    (Docente) cmbProfesor.getSelectedItem());
            cargarCursos();
            JOptionPane.showMessageDialog(this, "Curso creado: " + curso);
            txtTema.setText("");
            txtObjetivos.setText("");
            txtCreditos.setText("");
        } catch (RuntimeException ex) {
            ManejadorErrores.mostrar(this, ex);
        }
    }

    private void asignarNota() {
        try {
            CursoPosgrado curso = (CursoPosgrado) cmbCursoNota.getSelectedItem();
            MatriculaCurso matricula = (MatriculaCurso) cmbMatriculado.getSelectedItem();
            if (curso == null) {
                throw new ValidacionException("Debe seleccionar un curso.");
            }
            if (matricula == null) {
                throw new ValidacionException("El curso no tiene docentes matriculados.");
            }
            int nota = Validador.parsearEntero(txtNota.getText(), "nota");
            servicioCurso.emitirNota(curso.getProfesor(), matricula, nota);
            JOptionPane.showMessageDialog(this, "Nota asignada correctamente.");
            txtNota.setText("");
            actualizarMatriculados();
        } catch (RuntimeException ex) {
            ManejadorErrores.mostrar(this, ex);
        }
    }
}
