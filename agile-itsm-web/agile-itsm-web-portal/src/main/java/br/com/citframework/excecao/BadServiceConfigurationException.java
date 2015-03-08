package br.com.citframework.excecao;

public class BadServiceConfigurationException extends ServiceException {

    private static final long serialVersionUID = -1309195191204102259L;

    public BadServiceConfigurationException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public BadServiceConfigurationException(final String message) {
        super(message);
    }

    public BadServiceConfigurationException(final Throwable cause) {
        super(cause);
    }

}
