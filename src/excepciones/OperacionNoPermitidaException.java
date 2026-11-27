package excepciones;

public class OperacionNoPermitidaException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public OperacionNoPermitidaException(String mensaje) {
        super(mensaje);
    }

    public OperacionNoPermitidaException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}