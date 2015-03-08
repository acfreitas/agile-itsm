package br.com.centralit.citcorpore.bean;

public class HistoricoAtendimentoDTO extends AbstractGestaoForcaAtendimentoDTO {

    private static final long serialVersionUID = 7703638809083548899L;

    private Integer idSituacao;

    public Integer getIdSituacao() {
        return idSituacao;
    }

    public void setIdSituacao(final Integer idSituacao) {
        this.idSituacao = idSituacao;
    }

}
