package br.com.citframework.excecao;

public class ConnectionException extends PersistenceException {

    private static final long serialVersionUID = 8065909272818855345L;

    public ConnectionException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ConnectionException(final String message) {
        super(message);
    }

    public ConnectionException(final Throwable cause) {
        super(cause);
    }

}
