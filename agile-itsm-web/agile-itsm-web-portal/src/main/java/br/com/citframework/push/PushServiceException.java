package br.com.citframework.push;

public class PushServiceException extends Exception {

    private static final long serialVersionUID = -3000519228157883208L;

    public PushServiceException(final String message) {
        super(message);
    }

    public PushServiceException(final String message, final Throwable cause) {
        super(message, cause);
    }

}
