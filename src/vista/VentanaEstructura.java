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
import modelo.estructuraA.LineaInvestigacion;
import modelo.investigadorA.Docente;
import modelo.investigadorA.Investigador;
import servicio.ServicioEstructura;
import servicio.ServicioInvestigador;

public class VentanaEstructura extends JFrame {

    private static final long serialVersionUID = 1L;

    private static final String FACULTAD = "Facultad";
    private static final String DEPARTAMENTO = "Departamento";
    private static final String LINEA = "Línea de investigación";
    private static final String PLAN = "Plan de estudios";
    private static final String MATRICULA = "Matrícula en la maestría";
    private static final String PARTICIPACION = "Participación en una línea";

    private JPanel contentPane;
    private JComboBox<String> cmbEntidad;
    private JTextField txtNombre;
    private JComboBox<Docente> cmbDocente;
    private JComboBox<Investigador> cmbInvestigador;
    private JComboBox<LineaInvestigacion> cmbLinea;

    private ServicioEstructura servicioEstructura = new ServicioEstructura();
    private ServicioInvestigador servicioInvestigador = new ServicioInvestigador();

    public VentanaEstructura() {
        setTitle("Gestión de Estructura");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 600, 420);
        contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        JPanel panelEncabezado = EstiloUI.crearPanelEncabezado("Gestión de Estructura");
        panelEncabezado.setPreferredSize(new Dimension(600, 60));
        contentPane.add(panelEncabezado, BorderLayout.NORTH);

        JPanel panelCuerpo = EstiloUI.crearPanelCuerpo();
        panelCuerpo.setLayout(new BorderLayout(0, 10));
        panelCuerpo.setBorder(new EmptyBorder(15, 15, 15, 15));
        contentPane.add(panelCuerpo, BorderLayout.CENTER);

        JPanel panelCampos = new JPanel();
        panelCampos.setLayout(new GridLayout(5, 2, 8, 8));
        panelCampos.setOpaque(false);
        panelCuerpo.add(panelCampos, BorderLayout.CENTER);

        panelCampos.add(new JLabel("Operación:"));
        cmbEntidad = new JComboBox<String>();
        cmbEntidad.addItem(FACULTAD);
        cmbEntidad.addItem(DEPARTAMENTO);
        cmbEntidad.addItem(LINEA);
        cmbEntidad.addItem(PLAN);
        cmbEntidad.addItem(MATRICULA);
        cmbEntidad.addItem(PARTICIPACION);
        cmbEntidad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarCampos();
            }
        });
        panelCampos.add(cmbEntidad);

        panelCampos.add(new JLabel("Nombre (o tema de la participación):"));
        txtNombre = EstiloUI.crearCampoTexto(EstiloUI.LIMITE_GENERAL);
        panelCampos.add(txtNombre);

        panelCampos.add(new JLabel("Docente (responsable o matriculado):"));
        cmbDocente = new JComboBox<Docente>();
        panelCampos.add(cmbDocente);

        panelCampos.add(new JLabel("Investigador:"));
        cmbInvestigador = new JComboBox<Investigador>();
        panelCampos.add(cmbInvestigador);

        panelCampos.add(new JLabel("Línea de investigación:"));
        cmbLinea = new JComboBox<LineaInvestigacion>();
        panelCampos.add(cmbLinea);

        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(1, 2, 10, 10));
        panelBotones.setOpaque(false);
        panelCuerpo.add(panelBotones, BorderLayout.SOUTH);

        JButton btnCrear = EstiloUI.crearBotonPrimario("Registrar");
        btnCrear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrar();
            }
        });
        panelBotones.add(btnCrear);

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
            cmbDocente.removeAllItems();
            cmbInvestigador.removeAllItems();
            for (int i = 0; i < servicioInvestigador.listarInvestigadores().size(); i++) {
                Investigador inv = servicioInvestigador.listarInvestigadores().get(i);
                cmbInvestigador.addItem(inv);
                if (inv instanceof Docente) {
                    cmbDocente.addItem((Docente) inv);
                }
            }
            cmbLinea.removeAllItems();
            for (int i = 0; i < servicioEstructura.listarLineas().size(); i++) {
                cmbLinea.addItem(servicioEstructura.listarLineas().get(i));
            }
            actualizarCampos();
        } catch (RuntimeException ex) {
            ManejadorErrores.mostrar(this, ex);
        }
    }

    private void actualizarCampos() {
        String tipo = (String) cmbEntidad.getSelectedItem();
        boolean usaNombre = !MATRICULA.equals(tipo);
        boolean usaDocente = LINEA.equals(tipo) || MATRICULA.equals(tipo);
        boolean usaParticipacion = PARTICIPACION.equals(tipo);
        txtNombre.setEnabled(usaNombre);
        cmbDocente.setEnabled(usaDocente);
        cmbInvestigador.setEnabled(usaParticipacion);
        cmbLinea.setEnabled(usaParticipacion);
        
        // Ajustar límite de caracteres dinámicamente
        if (PARTICIPACION.equals(tipo)) {
            txtNombre = EstiloUI.crearCampoTexto(EstiloUI.LIMITE_NOMBRE_PERSONA);
        } else {
            txtNombre = EstiloUI.crearCampoTexto(EstiloUI.LIMITE_GENERAL);
        }
    }

    private void registrar() {
        try {
            String tipo = (String) cmbEntidad.getSelectedItem();
            String mensaje = "Registro realizado correctamente.";
            if (FACULTAD.equals(tipo)) {
                servicioEstructura.crearFacultad(txtNombre.getText());
                mensaje = "Facultad creada correctamente.";
            } else if (DEPARTAMENTO.equals(tipo)) {
                servicioEstructura.crearDepartamento(txtNombre.getText());
                mensaje = "Departamento creado correctamente.";
            } else if (LINEA.equals(tipo)) {
                LineaInvestigacion linea = servicioEstructura.crearLinea(txtNombre.getText(),
                        (Docente) cmbDocente.getSelectedItem());
                cmbLinea.addItem(linea);
                mensaje = "Línea creada correctamente.";
            } else if (PLAN.equals(tipo)) {
                servicioEstructura.crearPlan(txtNombre.getText());
                mensaje = "Plan de estudios creado correctamente.";
            } else if (MATRICULA.equals(tipo)) {
                servicioEstructura.matricularDocenteEnMaestria((Docente) cmbDocente.getSelectedItem());
                mensaje = "Docente matriculado en la maestría.";
            } else {
                servicioEstructura.agregarParticipacion((Investigador) cmbInvestigador.getSelectedItem(),
                        (LineaInvestigacion) cmbLinea.getSelectedItem(), txtNombre.getText());
                mensaje = "Participación registrada correctamente.";
            }
            JOptionPane.showMessageDialog(this, mensaje);
            txtNombre.setText("");
        } catch (RuntimeException ex) {
            ManejadorErrores.mostrar(this, ex);
        }
    }
}