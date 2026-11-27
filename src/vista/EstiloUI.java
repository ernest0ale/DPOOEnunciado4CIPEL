package vista;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.text.PlainDocument;

import util.LimiteCaracteres;

public final class EstiloUI {

    // Colores RGB solicitados
    public static final Color CIAN_OSCURO = new Color(0, 105, 148);
    public static final Color CIAN_OSCURO_HOVER = new Color(0, 85, 120);
    public static final Color GRIS_CUERPO = new Color(240, 240, 240);
    public static final Color GRIS_BORDE = new Color(200, 200, 200);
    public static final Color BLANCO_TEXTO = new Color(255, 255, 255);
    public static final Color TEXTO_OSCURO = new Color(50, 50, 50);

    // Fuentes
    public static final Font FUENTE_TITULO = new Font("Segoe UI", Font.BOLD, 20);
    public static final Font FUENTE_SUBTITULO = new Font("Segoe UI", Font.BOLD, 14);
    public static final Font FUENTE_NORMAL = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font FUENTE_BOTON = new Font("Segoe UI", Font.BOLD, 13);

    // Límites de caracteres solicitados
    public static final int LIMITE_NOMBRE_PERSONA = 40;
    public static final int LIMITE_CURSO = 35;
    public static final int LIMITE_GENERAL = 30;
    public static final int LIMITE_CI = 11;

    private EstiloUI() {
    }

    public static JPanel crearPanelEncabezado(String titulo) {
        JPanel panel = new JPanel();
        panel.setBackground(CIAN_OSCURO);
        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setForeground(BLANCO_TEXTO);
        lblTitulo.setFont(FUENTE_TITULO);
        panel.add(lblTitulo);
        return panel;
    }

    public static JPanel crearPanelCuerpo() {
        JPanel panel = new JPanel();
        panel.setBackground(GRIS_CUERPO);
        return panel;
    }

    public static JButton crearBotonPrimario(String texto) {
        JButton boton = new JButton(texto);
        boton.setBackground(CIAN_OSCURO);
        boton.setForeground(BLANCO_TEXTO);
        boton.setFont(FUENTE_BOTON);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        boton.setOpaque(true);
        boton.setContentAreaFilled(true);
        return boton;
    }

    public static JButton crearBotonSecundario(String texto) {
        JButton boton = new JButton(texto);
        boton.setBackground(GRIS_BORDE);
        boton.setForeground(TEXTO_OSCURO);
        boton.setFont(FUENTE_BOTON);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        boton.setOpaque(true);
        boton.setContentAreaFilled(true);
        return boton;
    }

    public static JTextField crearCampoTexto(int limiteCaracteres) {
        JTextField campo = new JTextField();
        campo.setFont(FUENTE_NORMAL);
        PlainDocument doc = new PlainDocument();
        doc.setDocumentFilter(new LimiteCaracteres(limiteCaracteres));
        campo.setDocument(doc);
        return campo;
    }

    public static Border crearBordeTitulo() {
        return BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(CIAN_OSCURO, 2),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        );
    }
}