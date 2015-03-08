package br.com.citframework.excecao;

public class ServiceNotFoundException extends ServiceException {

    private static final long serialVersionUID = -7789096066055617011L;

    public ServiceNotFoundException(final String message) {
        super(message);
    }

    public ServiceNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ServiceNotFoundException(final Throwable cause) {
        super(cause);
    }

}
