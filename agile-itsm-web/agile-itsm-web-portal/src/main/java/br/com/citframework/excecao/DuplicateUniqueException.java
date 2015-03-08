package br.com.citframework.excecao;

public class DuplicateUniqueException extends PersistenceException {

    private static final long serialVersionUID = 4115099327420847198L;

    public DuplicateUniqueException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public DuplicateUniqueException(final String message) {
        super(message);
    }

    public DuplicateUniqueException(final Throwable cause) {
        super(cause);
    }

}
