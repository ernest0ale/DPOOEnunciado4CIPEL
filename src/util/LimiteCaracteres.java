package util;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class LimiteCaracteres extends DocumentFilter {

    private final int limite;

    public LimiteCaracteres(int limite) {
        this.limite = limite;
    }

    @Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
        if (string != null) {
            if ((fb.getDocument().getLength() + string.length()) <= limite) {
                super.insertString(fb, offset, string, attr);
            }
        }
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
        if (text != null) {
            int nuevaLongitud = fb.getDocument().getLength() - length + text.length();
            if (nuevaLongitud <= limite) {
                super.replace(fb, offset, length, text, attrs);
            }
        }
    }
}