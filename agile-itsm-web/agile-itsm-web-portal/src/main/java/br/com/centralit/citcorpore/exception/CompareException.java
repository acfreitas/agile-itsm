package br.com.centralit.citcorpore.exception;

public class CompareException extends Exception {

    private static final long serialVersionUID = 6986408493039966907L;

    public CompareException(final String arg0, final Throwable arg1) {
        super(arg0, arg1);
    }

    public CompareException(final String arg0) {
        super(arg0);
    }

    public CompareException(final Throwable arg0) {
        super(arg0);
    }

}
