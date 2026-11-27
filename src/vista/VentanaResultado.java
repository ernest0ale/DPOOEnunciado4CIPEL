package vista;

import java.awt.BorderLayout;
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
import modelo.enums.GrupoImpacto;
import modelo.investigadorA.Investigador;
import servicio.ServicioInvestigador;
import servicio.ServicioResultado;
import util.Validador;

public class VentanaResultado extends JFrame {

    private static final long serialVersionUID = 1L;

    private JPanel contentPane;
    private JComboBox<Investigador> cmbInvestigador;
    private JComboBox<String> cmbTipo;
    private JTextField txtTitulo;
    private JTextField txtYear;
    private JTextField txtDatoAdicional;
    private JComboBox<GrupoImpacto> cmbGrupo;

    private ServicioResultado servicioResultado = new ServicioResultado();
    private ServicioInvestigador servicioInvestigador = new ServicioInvestigador();

    public VentanaResultado() {
        setTitle("Gestión de Resultados Investigativos");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 560, 380);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(15, 15, 15, 15));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 10));

        JPanel panelCampos = new JPanel();
        panelCampos.setLayout(new GridLayout(6, 2, 8, 8));
        contentPane.add(panelCampos, BorderLayout.CENTER);

        panelCampos.add(new JLabel("Investigador:"));
        cmbInvestigador = new JComboBox<Investigador>();
        panelCampos.add(cmbInvestigador);

        panelCampos.add(new JLabel("Tipo de resultado:"));
        cmbTipo = new JComboBox<String>();
        cmbTipo.addItem("Artículo");
        cmbTipo.addItem("Capítulo");
        cmbTipo.addItem("Ponencia");
        cmbTipo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarCampos();
            }
        });
        panelCampos.add(cmbTipo);

        panelCampos.add(new JLabel("Título:"));
        txtTitulo = new JTextField();
        panelCampos.add(txtTitulo);

        panelCampos.add(new JLabel("Año:"));
        txtYear = new JTextField();
        panelCampos.add(txtYear);

        panelCampos.add(new JLabel("Editores (capítulo) / Lugar (ponencia):"));
        txtDatoAdicional = new JTextField();
        panelCampos.add(txtDatoAdicional);

        panelCampos.add(new JLabel("Grupo de impacto (artículo):"));
        cmbGrupo = new JComboBox<GrupoImpacto>();
        panelCampos.add(cmbGrupo);

        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(1, 2, 10, 10));
        contentPane.add(panelBotones, BorderLayout.SOUTH);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardar();
            }
        });
        panelBotones.add(btnGuardar);

        JButton btnCerrar = new JButton("Cerrar");
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
                cmbInvestigador.addItem(servicioInvestigador.listarInvestigadores().get(i));
            }
            cmbGrupo.removeAllItems();
            for (int i = 0; i < GrupoImpacto.values().length; i++) {
                cmbGrupo.addItem(GrupoImpacto.values()[i]);
            }
            actualizarCampos();
    
        } catch (RuntimeException ex) {
            ManejadorErrores.mostrar(this, ex);
        }
    }

    private void actualizarCampos() {
        cmbGrupo.setEnabled("Artículo".equals(cmbTipo.getSelectedItem()));
        txtDatoAdicional.setEnabled(!"Artículo".equals(cmbTipo.getSelectedItem()));
    }

    private void guardar() {
        try {
            Investigador inv = (Investigador) cmbInvestigador.getSelectedItem();
            String titulo = txtTitulo.getText();
            int year = Validador.parsearEntero(txtYear.getText(), "año");
            String tipo = (String) cmbTipo.getSelectedItem();
            if ("Artículo".equals(tipo)) {
                servicioResultado.crearArticulo(inv, titulo, year, 1, 1,
                        "1-10", (GrupoImpacto) cmbGrupo.getSelectedItem());
            } else if ("Capítulo".equals(tipo)) {
                servicioResultado.crearCapitulo(inv, titulo, year,
                        txtDatoAdicional.getText(), "Editorial", "ISSN-000", "Libro", 1);
            } else {
                servicioResultado.crearPonencia(inv, titulo, year,
                        txtDatoAdicional.getText(), "2026-01-01", "ISBN-000");
            }
            JOptionPane.showMessageDialog(this, "Resultado registrado correctamente.");
            txtTitulo.setText("");
            txtYear.setText("");
            txtDatoAdicional.setText("");
        } catch (RuntimeException ex) {
            ManejadorErrores.mostrar(this, ex);
        }
    }
}
