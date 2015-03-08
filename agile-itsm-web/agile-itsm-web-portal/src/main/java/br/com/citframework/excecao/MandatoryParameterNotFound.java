package br.com.citframework.excecao;

public class MandatoryParameterNotFound extends PersistenceException {

    private static final long serialVersionUID = -634065631248031124L;

    public MandatoryParameterNotFound(final String message, final Throwable cause) {
        super(message, cause);
    }

    public MandatoryParameterNotFound(final String message) {
        super(message);
    }

    public MandatoryParameterNotFound(final Throwable cause) {
        super(cause);
    }

}
