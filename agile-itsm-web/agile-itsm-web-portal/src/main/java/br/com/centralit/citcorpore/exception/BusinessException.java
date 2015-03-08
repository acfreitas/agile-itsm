/*
 * Created on 20/03/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package br.com.centralit.citcorpore.exception;

/**
 * @author Tellus SA
 *
 *         To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class BusinessException extends GeneralException {

    private static final long serialVersionUID = -1595760355392522066L;

    public BusinessException() {
        super();
    }

    public BusinessException(final Throwable cause) {
        super(cause);
    }

    /**
     * @param code
     */
    public BusinessException(final String code) {
        super(code);
    }

    /**
     * @param code
     * @param arg1
     */
    public BusinessException(final String code, final String arg1) {
        super(code, arg1);
    }

    /**
     * @param code
     * @param arg1
     * @param arg2
     */
    public BusinessException(final String code, final String arg1, final String arg2) {
        super(code, arg1, arg2);
    }

    /**
     * @param code
     * @param arg1
     * @param arg2
     * @param arg3
     */
    public BusinessException(final String code, final String arg1, final String arg2, final String arg3) {
        super(code, arg1, arg2, arg3);
    }

    /**
     * @param code
     * @param cause
     */
    public BusinessException(final String code, final Throwable cause) {
        super(code, cause);
    }

    /**
     * @param code
     * @param arg1
     * @param cause
     */
    public BusinessException(final String code, final String arg1, final Throwable cause) {
        super(code, arg1, cause);
    }

    /**
     * @param code
     * @param arg1
     * @param arg2
     * @param cause
     */
    public BusinessException(final String code, final String arg1, final String arg2, final Throwable cause) {
        super(code, arg1, arg2, cause);
    }

    /**
     * @param code
     * @param arg1
     * @param arg2
     * @param arg3
     * @param cause
     */
    public BusinessException(final String code, final String arg1, final String arg2, final String arg3, final Throwable cause) {
        super(code, arg1, arg2, arg3, cause);
    }

}
