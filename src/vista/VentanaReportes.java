package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import reportes.Reporte;
import reportes.ReporteActividadDepartamento;
import reportes.ReporteAvanceMaestria;
import reportes.ReporteRankingInvestigadores;
import reportes.ReporteRendimientoCurso;
import util.Validador;

public class VentanaReportes extends JFrame {

    private static final long serialVersionUID = 1L;

    private JPanel contentPane;
    private JComboBox<Reporte> cmbReporte;
    private JTextField txtYear;
    private JLabel lblTitulo;
    private JLabel lblEstado;
    private JTable tabla;

    public VentanaReportes() {
        setTitle("Reportes");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 1000, 580);
        contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        JPanel panelEncabezado = EstiloUI.crearPanelEncabezado("Reportes");
        panelEncabezado.setPreferredSize(new Dimension(1000, 60));
        contentPane.add(panelEncabezado, BorderLayout.NORTH);

        JPanel panelCuerpo = EstiloUI.crearPanelCuerpo();
        panelCuerpo.setLayout(new BorderLayout(0, 6));
        panelCuerpo.setBorder(new EmptyBorder(8, 8, 8, 8));
        contentPane.add(panelCuerpo, BorderLayout.CENTER);

        JPanel panelNorte = new JPanel();
        panelNorte.setLayout(new BorderLayout(0, 4));
        panelNorte.setOpaque(false);
        panelCuerpo.add(panelNorte, BorderLayout.NORTH);

        JPanel panelSeleccion = new JPanel();
        panelSeleccion.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 6));
        panelSeleccion.setOpaque(false);
        panelNorte.add(panelSeleccion, BorderLayout.NORTH);

        panelSeleccion.add(new JLabel("Reporte:"));
        cmbReporte = new JComboBox<Reporte>();
        cmbReporte.setPreferredSize(new Dimension(480, 26));
        cmbReporte.addItem(new ReporteActividadDepartamento());
        cmbReporte.addItem(new ReporteRankingInvestigadores());
        cmbReporte.addItem(new ReporteAvanceMaestria());
        cmbReporte.addItem(new ReporteRendimientoCurso());
        cmbReporte.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarCampoYear();
            }
        });
        panelSeleccion.add(cmbReporte);

        panelSeleccion.add(new JLabel("Año:"));
        txtYear = EstiloUI.crearCampoTexto(4);
        txtYear.setText("2026");
        txtYear.setPreferredSize(new Dimension(80, 26));
        panelSeleccion.add(txtYear);

        lblTitulo = new JLabel("Seleccione un reporte y pulse Generar.");
        lblTitulo.setFont(EstiloUI.FUENTE_SUBTITULO);
        lblTitulo.setBorder(new EmptyBorder(0, 10, 0, 10));
        panelNorte.add(lblTitulo, BorderLayout.SOUTH);

        JScrollPane scrollPane = new JScrollPane();
        panelCuerpo.add(scrollPane, BorderLayout.CENTER);

        tabla = new JTable();
        tabla.setRowHeight(24);
        tabla.setFillsViewportHeight(true);
        scrollPane.setViewportView(tabla);

        JPanel panelSur = new JPanel();
        panelSur.setLayout(new BorderLayout(0, 6));
        panelSur.setOpaque(false);
        panelCuerpo.add(panelSur, BorderLayout.SOUTH);

        lblEstado = new JLabel(" ");
        lblEstado.setForeground(new Color(150, 60, 0));
        lblEstado.setBorder(new EmptyBorder(0, 10, 0, 10));
        panelSur.add(lblEstado, BorderLayout.NORTH);

        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(1, 2, 10, 10));
        panelBotones.setOpaque(false);
        panelSur.add(panelBotones, BorderLayout.SOUTH);

        JButton btnGenerar = EstiloUI.crearBotonPrimario("Generar");
        btnGenerar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generar();
            }
        });
        panelBotones.add(btnGenerar);

        JButton btnCerrar = EstiloUI.crearBotonSecundario("Cerrar");
        btnCerrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        panelBotones.add(btnCerrar);

        setLocationRelativeTo(null);
    }

    public void seleccionarReporte(int indice) {
        if (indice >= 0 && indice < cmbReporte.getItemCount()) {
            cmbReporte.setSelectedIndex(indice);
        }
    }

    private void actualizarCampoYear() {
        try {
            Reporte reporte = (Reporte) cmbReporte.getSelectedItem();
            txtYear.setEnabled(reporte.requiereYear());
        } catch (RuntimeException ex) {
            ManejadorErrores.mostrar(this, ex);
        }
    }

    private void generar() {
        try {
            Reporte reporte = (Reporte) cmbReporte.getSelectedItem();
            int year = 0;
            if (reporte.requiereYear()) {
                year = Validador.parsearEntero(txtYear.getText(), "año");
                Validador.validarYear(year);
            }
            ArrayList<String[]> filas = reporte.generar(year);
            mostrarTabla(reporte.getColumnas(), filas);
            lblTitulo.setText(reporte.getTitulo(year));
            if (filas.isEmpty()) {
                lblEstado.setText("No hay datos para mostrar con los criterios seleccionados.");
            } else {
                lblEstado.setText(filas.size() + " fila(s) en el reporte.");
            }
        } catch (RuntimeException ex) {
            ManejadorErrores.mostrar(this, ex);
        }
    }

    private void mostrarTabla(String[] columnas, ArrayList<String[]> filas) {
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        for (int i = 0; i < filas.size(); i++) {
            modelo.addRow(filas.get(i));
        }
        tabla.setModel(modelo);
        tabla.getTableHeader().setReorderingAllowed(false);
        DefaultTableCellRenderer centrado = new DefaultTableCellRenderer();
        centrado.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < tabla.getColumnCount(); i++) {
            if (esColumnaCorta(i, filas)) {
                tabla.getColumnModel().getColumn(i).setCellRenderer(centrado);
            }
        }
    }

    private boolean esColumnaCorta(int columna, ArrayList<String[]> filas) {
        boolean corta = true;
        for (int i = 0; i < filas.size() && corta; i++) {
            corta = filas.get(i)[columna].matches("[0-9.,]+|Sí|No");
        }
        return corta;
    }
}