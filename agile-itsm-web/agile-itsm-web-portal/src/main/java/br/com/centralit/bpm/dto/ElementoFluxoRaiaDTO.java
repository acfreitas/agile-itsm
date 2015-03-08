package br.com.centralit.bpm.dto;

import java.util.List;

public class ElementoFluxoRaiaDTO extends ElementoFluxoDTO {

    private static final long serialVersionUID = -2182987248745850229L;

    private String siglaGrupo;
    private List<String> idElementosAgrupados;

    public List<String> getIdElementosAgrupados() {
        return idElementosAgrupados;
    }

    public void setIdElementosAgrupados(final List<String> idElementosAgrupados) {
        this.idElementosAgrupados = idElementosAgrupados;
    }

    public String getSiglaGrupo() {
        return siglaGrupo;
    }

    public void setSiglaGrupo(final String siglaGrupo) {
        this.siglaGrupo = siglaGrupo;
    }

}
