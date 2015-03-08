package br.com.citframework.excecao;

public class PersistenceException extends Exception {

    private static final long serialVersionUID = 8077222710412268526L;

    public PersistenceException() {
        super();
    }

    public PersistenceException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public PersistenceException(final String message) {
        super(message);
    }

    public PersistenceException(final Throwable cause) {
        super(cause);
    }

}
