/*
 * Created on 18/03/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package br.com.centralit.citcorpore.exception;

import java.io.Serializable;
import java.util.StringTokenizer;

/**
 * @author Tellus SA
 *
 *         To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class GeneralException extends Exception implements Serializable {

    private static final long serialVersionUID = -4719734145817383483L;

    protected String code;
    protected String arg1;
    protected String arg2;
    protected String arg3;
    protected Throwable causa;

    protected GeneralException() {}

    protected GeneralException(final Throwable cause) {
        causa = cause;
    }

    /**
     *
     * @param code
     *            ( String ) :: Código da exceção. Deve estar definido no arquivo resources.properties
     */
    public GeneralException(final String code) {
        super(code);
        this.code = code;
        arg2 = null;
        arg3 = null;
        arg1 = null;
    }

    /**
     *
     * @param code
     * @param arg1
     */
    public GeneralException(final String code, final String arg1) {
        super(code);
        this.code = code;
        this.arg1 = arg1;
        arg2 = null;
        arg3 = null;
    }

    /**
     *
     * @param code
     * @param arg1
     * @param arg2
     */
    public GeneralException(final String code, final String arg1, final String arg2) {
        super(code);
        this.code = code;
        this.arg1 = arg1;
        this.arg2 = arg2;
        arg3 = null;
    }

    /**
     *
     * @param code
     * @param arg1
     * @param arg2
     * @param arg3
     */
    public GeneralException(final String code, final String arg1, final String arg2, final String arg3) {
        super(code);
        this.code = code;
        this.arg1 = arg1;
        this.arg2 = arg2;
        this.arg3 = arg3;
    }

    /**
     *
     * @param code
     * @param cause
     */
    public GeneralException(final String code, final Throwable cause) {
        causa = cause;
        this.code = code;
        arg2 = null;
        arg3 = null;
        arg1 = null;
    }

    /**
     *
     * @param code
     * @param arg1
     * @param cause
     */
    public GeneralException(final String code, final String arg1, final Throwable cause) {
        causa = cause;
        this.code = code;
        this.arg1 = arg1;
        arg2 = null;
        arg3 = null;
    }

    /**
     *
     * @param code
     * @param arg1
     * @param arg2
     * @param cause
     */
    public GeneralException(final String code, final String arg1, final String arg2, final Throwable cause) {
        causa = cause;
        this.code = code;
        this.arg1 = arg1;
        this.arg2 = arg2;
        arg3 = null;
    }

    /**
     *
     * @param code
     * @param arg1
     * @param arg2
     * @param arg3
     * @param cause
     */
    public GeneralException(final String code, final String arg1, final String arg2, final String arg3, final Throwable cause) {
        causa = cause;
        this.code = code;
        this.arg1 = arg1;
        this.arg2 = arg2;
        this.arg3 = arg3;
    }

    public String toHTML() {
        final String html = "<li><b>" + this.toMessage() + "</b></li><br>";
        return html;
    }

    public String getMensagem() {
        return this.toMessage();
    }

    @Override
    public String getMessage() {
        return this.toMessage();
    }

    /**
     * Texto HTML das mensagens de erro
     *
     * @return String
     */
    public String toMessage() {
        TradutorExcecao tradutor;
        String message = "";
        try {
            if (code == null) {
                return "Codigo de erro nao informado (nulo)!";
            }
            tradutor = TradutorExcecao.getInstance();
            final String resource = tradutor.getMensagem(code);
            if (resource == null) {
                message = "Recurso não encontrado: " + code;
            }
            final StringTokenizer st = new StringTokenizer(resource, "|");
            if (!st.hasMoreElements()) {
                message = resource;
            }
            while (st.hasMoreElements()) {
                String arg = null;
                final String partMessage = st.nextToken();
                if (this.isArg(partMessage)) {
                    arg = this.getArg(partMessage);
                    if (arg == null) {
                        arg = "";
                    }
                    message = message + arg + " ";
                } else {
                    message = message + partMessage + " ";
                }
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return message;
    }

    /**
     *
     * @param partMessage
     * @return
     */
    private boolean isArg(final String partMessage) {
        if (partMessage.equals("1") || partMessage.equals("2") || partMessage.equals("3")) {
            return true;
        }
        return false;
    }

    /**
     *
     * @param number
     * @return
     */
    private String getArg(final String number) {
        String arg = null;
        if ("1".equals(number)) {
            arg = arg1;
        }
        if ("2".equals(number)) {
            arg = arg2;
        }
        if ("3".equals(number)) {
            arg = arg3;
        }
        return arg;
    }

    public String toStackTrace() {

        Writer writer = new Writer();
        String mensagem = "";

        if (this.getCause() != null) {
            this.getCause().printStackTrace(writer);
            mensagem = "Causa =======================================================\n";
            mensagem = mensagem + writer.getBuffer().toString() + "\n";
        }

        writer = new Writer();
        this.printStackTrace(writer);
        mensagem = mensagem + "Excecao =====================================================\n";
        mensagem = mensagem + writer.getBuffer().toString() + "\n";

        return mensagem;
    }

    @Override
    public Throwable getCause() {
        return causa;
    }

    public String getCode() {
        return code;
    }

    public void setCode(final String string) {
        code = string;
    }

}
