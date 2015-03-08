package br.com.citframework.excecao;

public class ServiceException extends Exception {

    private static final long serialVersionUID = 5848619343161025723L;

    public ServiceException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ServiceException(final String message) {
        super(message);
    }

    public ServiceException(final Throwable cause) {
        super(cause);
    }

}
