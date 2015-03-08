package br.com.citframework.excecao;

public class InvalidParameterException extends PersistenceException {

    private static final long serialVersionUID = 4748754751954771620L;

    public InvalidParameterException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public InvalidParameterException(final String message) {
        super(message);
    }

    public InvalidParameterException(final Throwable cause) {
        super(cause);
    }

}
