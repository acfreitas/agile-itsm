package br.com.citframework.excecao;

public class FKReferenceException extends PersistenceException {

    private static final long serialVersionUID = 8924754433488426943L;

    public FKReferenceException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public FKReferenceException(final String message) {
        super(message);
    }

    public FKReferenceException(final Throwable cause) {
        super(cause);
    }

}
