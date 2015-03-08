package br.com.citframework.excecao;

public class LogicException extends Exception {

    private static final long serialVersionUID = -9146385277453620722L;

    public LogicException() {
        super();
    }

    public LogicException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public LogicException(final String message) {
        super(message);
    }

    public LogicException(final Throwable cause) {
        super(cause);
    }

}
