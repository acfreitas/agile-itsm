package br.com.centralit.bpm.dto;

public class EventoFluxoDTO extends ItemTrabalhoFluxoDTO {

    private static final long serialVersionUID = -5213322225724376035L;

    private Integer intervalo;
    private Integer idTipoFluxo;
    private Integer idFluxo;
    private String nomeClasseFluxo;

    public Integer getIntervalo() {
        return intervalo;
    }

    public void setIntervalo(final Integer intervalo) {
        this.intervalo = intervalo;
    }

    public Integer getIdTipoFluxo() {
        return idTipoFluxo;
    }

    public void setIdTipoFluxo(final Integer idTipoFluxo) {
        this.idTipoFluxo = idTipoFluxo;
    }

    @Override
    public Integer getIdFluxo() {
        return idFluxo;
    }

    @Override
    public void setIdFluxo(final Integer idFluxo) {
        this.idFluxo = idFluxo;
    }

    public String getNomeClasseFluxo() {
        return nomeClasseFluxo;
    }

    public void setNomeClasseFluxo(final String nomeClasseFluxo) {
        this.nomeClasseFluxo = nomeClasseFluxo;
    }

}
