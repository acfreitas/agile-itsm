package br.com.centralit.citajax.config;

import java.io.Serializable;

public class RedirectItem implements Serializable {

    private static final long serialVersionUID = 89372097586178130L;

    private String pathIn;
    private String pathOut;

    public String getPathIn() {
        return pathIn;
    }

    public void setPathIn(final String pathIn) {
        this.pathIn = pathIn;
    }

    public String getPathOut() {
        return pathOut;
    }

    public void setPathOut(final String pathOut) {
        this.pathOut = pathOut;
    }

}
