package vista;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import modelo.investigadorA.Docente;
import modelo.investigadorA.Estudiante;
import modelo.investigadorA.Investigador;
import servicio.Sesion;
import servicio.SistemaCipel;
import util.Validador;

public class VentanaLogin extends JDialog {

    private static final long serialVersionUID = 1L;

    private JTextField txtCi;
    private JPasswordField txtPassword;
    private JButton btnIngresar;

    public VentanaLogin(JFrame padre) {
        super(padre, "Iniciar sesión", true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 420, 320);
        setLayout(new BorderLayout(0, 0));

        JPanel panelEncabezado = EstiloUI.crearPanelEncabezado("Iniciar sesión");
        panelEncabezado.setPreferredSize(new Dimension(420, 60));
        add(panelEncabezado, BorderLayout.NORTH);

        JPanel panelCuerpo = EstiloUI.crearPanelCuerpo();
        panelCuerpo.setLayout(new BorderLayout(0, 10));
        panelCuerpo.setBorder(new EmptyBorder(20, 20, 20, 20));
        add(panelCuerpo, BorderLayout.CENTER);

        JPanel panelCampos = new JPanel(new GridLayout(2, 2, 8, 8));
        panelCampos.setOpaque(false);
        panelCuerpo.add(panelCampos, BorderLayout.CENTER);

        panelCampos.add(new JLabel("CI:"));
        txtCi = EstiloUI.crearCampoTexto(EstiloUI.LIMITE_CI);
        panelCampos.add(txtCi);

        panelCampos.add(new JLabel("Contraseña:"));
        txtPassword = new JPasswordField();
        txtPassword.setFont(EstiloUI.FUENTE_NORMAL);
        panelCampos.add(txtPassword);

        JPanel panelBotones = new JPanel(new GridLayout(1, 2, 10, 10));
        panelBotones.setOpaque(false);
        panelCuerpo.add(panelBotones, BorderLayout.SOUTH);

        btnIngresar = EstiloUI.crearBotonPrimario("Ingresar");
        btnIngresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ingresar();
            }
        });
        panelBotones.add(btnIngresar);

        JButton btnCancelar = EstiloUI.crearBotonSecundario("Cancelar");
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                System.exit(0);
            }
        });
        panelBotones.add(btnCancelar);

        setLocationRelativeTo(null);
    }

    private void ingresar() {
        try {
            String ci = txtCi.getText().trim();
            String password = new String(txtPassword.getPassword());
            // Admin fijo
            if ("00000000000".equals(ci) && "admin".equals(password)) {
                Sesion.getInstancia().iniciarSesion(null, Sesion.Rol.ADMIN);
                dispose();
                new VentanaInicio().setVisible(true);
            } else {
                Investigador inv = buscarInvestigadorPorCi(ci);
                if (inv != null && inv.getCi().equals(password)) {
                    Sesion.Rol rol = Sesion.Rol.ESTUDIANTE;
                    if (inv instanceof Docente) {
                        rol = Sesion.Rol.DOCENTE;
                    }
                    Sesion.getInstancia().iniciarSesion(inv, rol);
                    dispose();
                    new VentanaInicio().setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "CI o contraseña incorrectos. La contraseña por defecto es su CI.",
                            "Error de autenticación", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (RuntimeException ex) {
            ManejadorErrores.mostrar(this, ex);
        }
    }

    private Investigador buscarInvestigadorPorCi(String ci) {
        Investigador encontrado = null;
        for (int i = 0; i < SistemaCipel.getInstancia().getDocentes().size() && encontrado == null; i++) {
            if (SistemaCipel.getInstancia().getDocentes().get(i).getCi().equals(ci)) {
                encontrado = SistemaCipel.getInstancia().getDocentes().get(i);
            }
        }
        if (encontrado == null) {
            for (int i = 0; i < SistemaCipel.getInstancia().getEstudiantes().size() && encontrado == null; i++) {
                if (SistemaCipel.getInstancia().getEstudiantes().get(i).getCi().equals(ci)) {
                    encontrado = SistemaCipel.getInstancia().getEstudiantes().get(i);
                }
            }
        }
        return encontrado;
    }
}