package util;

import excepciones.ValidacionException;

public final class Validador {

    private Validador() {
    }

    public static void validarTexto(String valor, String campo) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new ValidacionException("El campo " + campo + " no puede estar vacío.");
        }
    }

    public static void validarEnteroPositivo(int valor, String campo) {
        if (valor <= 0) {
            throw new ValidacionException("El campo " + campo + " debe ser mayor que cero.");
        }
    }

    public static void validarYear(int year) {
        if (year < 1900 || year > 2100) {
            throw new ValidacionException("El año debe estar entre 1900 y 2100.");
        }
    }

    public static void validarNota(int nota) {
        if (nota < 2 || nota > 5) {
            throw new ValidacionException("La nota debe estar entre 2 y 5.");
        }
    }

    public static void validarCi(String ci) {
        validarTexto(ci, "CI");
        if (!ci.trim().matches("\\d{11}")) {
            throw new ValidacionException("El CI debe tener 11 dígitos numéricos.");
        }
    }

    public static int parsearEntero(String valor, String campo) {
        int numero = 0;
        if (valor == null) {
            throw new ValidacionException("El campo " + campo + " es obligatorio.");
        }
        try {
            numero = Integer.parseInt(valor.trim());
        } catch (NumberFormatException e) {
            throw new ValidacionException("El campo " + campo + " debe ser un número entero.", e);
        }
        return numero;
    }

    public static double parsearDouble(String valor, String campo) {
        double numero = 0.0;
        if (valor == null) {
            throw new ValidacionException("El campo " + campo + " es obligatorio.");
        }
        try {
            numero = Double.parseDouble(valor.trim());
        } catch (NumberFormatException e) {
            throw new ValidacionException("El campo " + campo + " debe ser un número decimal.", e);
        }
        return numero;
    }
}