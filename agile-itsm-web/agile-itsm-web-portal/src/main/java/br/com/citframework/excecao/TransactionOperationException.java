package br.com.citframework.excecao;

public class TransactionOperationException extends PersistenceException {

    private static final long serialVersionUID = 5324157786326109801L;

    public TransactionOperationException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public TransactionOperationException(final String message) {
        super(message);
    }

    public TransactionOperationException(final Throwable cause) {
        super(cause);
    }

}
