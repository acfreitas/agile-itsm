/*
 * Created on 20/03/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package br.com.centralit.citcorpore.exception;

import java.rmi.RemoteException;

/**
 * @author tellus SA
 *
 */
public class SystemException extends GeneralException {

    private static final long serialVersionUID = -2589117992071865237L;

    public SystemException() {
        super();
    }

    public SystemException(final Throwable cause) {
        super(cause);
    }

    public SystemException(final RuntimeException cause) {
        super(cause);
        code = "sis004";
    }

    public SystemException(final RemoteException cause) {
        super(cause);
        code = "sis005";
    }

    /**
     * @param code
     */
    public SystemException(final String code) {
        super(code);
    }

    /**
     * @param code
     * @param arg1
     */
    public SystemException(final String code, final String arg1) {
        super(code, arg1);
    }

    /**
     * @param code
     * @param arg1
     * @param arg2
     */
    public SystemException(final String code, final String arg1, final String arg2) {
        super(code, arg1, arg2);
    }

    /**
     * @param code
     * @param arg1
     * @param arg2
     * @param arg3
     */
    public SystemException(final String code, final String arg1, final String arg2, final String arg3) {
        super(code, arg1, arg2, arg3);
    }

    /**
     * @param code
     * @param cause
     */
    public SystemException(final String code, final Throwable cause) {
        super(code, cause);
    }

    /**
     * @param code
     * @param arg1
     * @param cause
     */
    public SystemException(final String code, final String arg1, final Throwable cause) {
        super(code, arg1, cause);
    }

    /**
     * @param code
     * @param arg1
     * @param arg2
     * @param cause
     */
    public SystemException(final String code, final String arg1, final String arg2, final Throwable cause) {
        super(code, arg1, arg2, cause);
    }

    /**
     * @param code
     * @param arg1
     * @param arg2
     * @param arg3
     * @param cause
     */
    public SystemException(final String code, final String arg1, final String arg2, final String arg3, final Throwable cause) {
        super(code, arg1, arg2, arg3, cause);
    }

}
