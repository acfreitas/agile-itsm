package br.com.citframework.util.cripto;

import java.io.Serializable;

public class SignedInfo implements Serializable {

    private static final long serialVersionUID = 4482765870093394127L;

    private String strCripto;
    private String strSigned;

    public String getStrCripto() {
        return strCripto;
    }

    public void setStrCripto(final String strCripto) {
        this.strCripto = strCripto;
    }

    public String getStrSigned() {
        return strSigned;
    }

    public void setStrSigned(final String strSigned) {
        this.strSigned = strSigned;
    }

}
