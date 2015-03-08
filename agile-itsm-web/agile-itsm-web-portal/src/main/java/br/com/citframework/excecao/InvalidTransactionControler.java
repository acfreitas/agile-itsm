package br.com.citframework.excecao;

public class InvalidTransactionControler extends Exception {

    private static final long serialVersionUID = 3690400574717330647L;

    public InvalidTransactionControler(final String message, final Throwable cause) {
        super(message, cause);
    }

    public InvalidTransactionControler(final String message) {
        super(message);
    }

    public InvalidTransactionControler(final Throwable cause) {
        super(cause);
    }

}
