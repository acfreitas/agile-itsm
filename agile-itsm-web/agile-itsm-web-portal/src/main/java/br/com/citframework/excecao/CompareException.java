package br.com.citframework.excecao;

public class CompareException extends Exception {

    private static final long serialVersionUID = 6986408493039966907L;

    public CompareException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public CompareException(final String message) {
        super(message);
    }

    public CompareException(final Throwable cause) {
        super(cause);
    }

}
