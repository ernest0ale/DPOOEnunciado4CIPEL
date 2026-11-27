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
import modelo.enums.CategoriaCientifica;
import modelo.enums.CategoriaDocente;
import modelo.estructuraA.Departamento;
import modelo.investigadorA.Docente;
import modelo.investigadorA.Estudiante;
import servicio.ServicioEstructura;
import servicio.ServicioInvestigador;

public class VentanaInvestigador extends JFrame {

    private static final long serialVersionUID = 1L;

    private JPanel contentPane;
    private JComboBox<String> cmbTipo;
    private JTextField txtNombre;
    private JTextField txtCi;
    private JComboBox<CategoriaDocente> cmbCategoriaDocente;
    private JComboBox<CategoriaCientifica> cmbCategoriaCientifica;
    private JComboBox<Departamento> cmbDepartamento;

    private ServicioInvestigador servicioInvestigador = new ServicioInvestigador();
    private ServicioEstructura servicioEstructura = new ServicioEstructura();

    public VentanaInvestigador() {
        setTitle("Gestión de Investigadores");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 560, 420);
        contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        JPanel panelEncabezado = EstiloUI.crearPanelEncabezado("Gestión de Investigadores");
        panelEncabezado.setPreferredSize(new Dimension(560, 60));
        contentPane.add(panelEncabezado, BorderLayout.NORTH);

        JPanel panelCuerpo = EstiloUI.crearPanelCuerpo();
        panelCuerpo.setLayout(new BorderLayout(0, 10));
        panelCuerpo.setBorder(new EmptyBorder(15, 15, 15, 15));
        contentPane.add(panelCuerpo, BorderLayout.CENTER);

        JPanel panelCampos = new JPanel();
        panelCampos.setLayout(new GridLayout(6, 2, 8, 8));
        panelCampos.setOpaque(false);
        panelCuerpo.add(panelCampos, BorderLayout.CENTER);

        panelCampos.add(new JLabel("Tipo:"));
        cmbTipo = new JComboBox<String>();
        cmbTipo.addItem("Docente");
        cmbTipo.addItem("Estudiante");
        cmbTipo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarCampos();
            }
        });
        panelCampos.add(cmbTipo);

        panelCampos.add(new JLabel("Nombre:"));
        txtNombre = EstiloUI.crearCampoTexto(EstiloUI.LIMITE_NOMBRE_PERSONA);
        panelCampos.add(txtNombre);

        panelCampos.add(new JLabel("CI (11 dígitos):"));
        txtCi = EstiloUI.crearCampoTexto(EstiloUI.LIMITE_CI);
        panelCampos.add(txtCi);

        panelCampos.add(new JLabel("Categoría docente:"));
        cmbCategoriaDocente = new JComboBox<CategoriaDocente>();
        panelCampos.add(cmbCategoriaDocente);

        panelCampos.add(new JLabel("Categoría científica:"));
        cmbCategoriaCientifica = new JComboBox<CategoriaCientifica>();
        panelCampos.add(cmbCategoriaCientifica);

        panelCampos.add(new JLabel("Departamento:"));
        cmbDepartamento = new JComboBox<Departamento>();
        panelCampos.add(cmbDepartamento);

        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(1, 2, 10, 10));
        panelBotones.setOpaque(false);
        panelCuerpo.add(panelBotones, BorderLayout.SOUTH);

        JButton btnGuardar = EstiloUI.crearBotonPrimario("Guardar");
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardar();
            }
        });
        panelBotones.add(btnGuardar);

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
            cmbCategoriaDocente.removeAllItems();
            for (int i = 0; i < CategoriaDocente.values().length; i++) {
                cmbCategoriaDocente.addItem(CategoriaDocente.values()[i]);
            }
            cmbCategoriaCientifica.removeAllItems();
            for (int i = 0; i < CategoriaCientifica.values().length; i++) {
                cmbCategoriaCientifica.addItem(CategoriaCientifica.values()[i]);
            }
            cmbDepartamento.removeAllItems();
            for (int i = 0; i < servicioEstructura.listarDepartamentos().size(); i++) {
                cmbDepartamento.addItem(servicioEstructura.listarDepartamentos().get(i));
            }
            actualizarCampos();
        } catch (RuntimeException ex) {
            ManejadorErrores.mostrar(this, ex);
        }
    }

    private void actualizarCampos() {
        boolean esDocente = "Docente".equals(cmbTipo.getSelectedItem());
        cmbCategoriaDocente.setEnabled(esDocente);
        cmbCategoriaCientifica.setEnabled(esDocente);
        cmbDepartamento.setEnabled(esDocente);
    }

    private void guardar() {
        try {
            String nombre = txtNombre.getText();
            String ci = txtCi.getText();
            if ("Docente".equals(cmbTipo.getSelectedItem())) {
                Departamento dep = (Departamento) cmbDepartamento.getSelectedItem();
                Docente docente = servicioInvestigador.crearDocente(
                        nombre, ci,
                        ((CategoriaDocente) cmbCategoriaDocente.getSelectedItem()).name(),
                        ((CategoriaCientifica) cmbCategoriaCientifica.getSelectedItem()).name(),
                        dep);
                JOptionPane.showMessageDialog(this, "Docente registrado: " + docente);
            } else {
                Estudiante estudiante = servicioInvestigador.crearEstudiante(nombre, ci);
                JOptionPane.showMessageDialog(this, "Estudiante registrado: " + estudiante);
            }
            txtNombre.setText("");
            txtCi.setText("");
        } catch (RuntimeException ex) {
            ManejadorErrores.mostrar(this, ex);
        }
    }
}