package br.com.citframework.excecao;

public class InvalidTransactionObjectException extends PersistenceException {

    private static final long serialVersionUID = 1749546277700450972L;

    public InvalidTransactionObjectException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public InvalidTransactionObjectException(final String message) {
        super(message);
    }

    public InvalidTransactionObjectException(final Throwable cause) {
        super(cause);
    }

}
