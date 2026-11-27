package vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

public class VentanaCreditos extends JFrame {

    private static final long serialVersionUID = 1L;

    private JPanel contentPane;

    public VentanaCreditos() {
        setTitle("Ayuda y Créditos");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 560, 400);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 8));

        JScrollPane scrollPane = new JScrollPane();
        contentPane.add(scrollPane, BorderLayout.CENTER);

        JTextArea txtAyuda = new JTextArea();
        txtAyuda.setEditable(false);
        txtAyuda.setLineWrap(true);
        txtAyuda.setWrapStyleWord(true);
        txtAyuda.setText("CIPEL - Control de Actividad Investigativa\n\n"
                + "Finalidad:\n"
                + "Sistema de gestión de la actividad investigativa y de posgrado de la facultad, "
                + "que permite registrar investigadores, resultados, líneas, cursos y matrículas, "
                + "y consultar reportes por año.\n\n"
                + "Cómo usarlo:\n"
                + "1. Inicie los datos automáticamente (datos de prueba) o manualmente.\n"
                + "2. En Iniciar datos manualmente registre, en este orden: la estructura, los "
                + "investigadores, los cursos de posgrado, las matrículas de posgrado y los resultados "
                + "investigativos.\n"
                + "3. Las notas se asignan desde la ventana Curso de posgrado, pestaña Asignar nota.\n"
                + "4. Con Ver datos consulte, modifique o elimine los registros, y con Ver reportes "
                + "consulte los reportes indicando el año.\n\n"
                + "Autores:\n"
                + "- Armando Villavicencio Meneses\n"
                + "- Ernesto Alejandro García Seuret\n\n"
                + "Asignatura: Diseño y Programación Orientada a Objetos\n"
                + "Carrera: Ingeniería Informática\n"
                + "CUJAE, 2026");
        txtAyuda.setCaretPosition(0);
        scrollPane.setViewportView(txtAyuda);

        JPanel panelBoton = new JPanel();
        panelBoton.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        contentPane.add(panelBoton, BorderLayout.SOUTH);

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        panelBoton.add(btnCerrar);

        setLocationRelativeTo(null);
    }
}
