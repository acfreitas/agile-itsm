package br.com.centralit.citcorpore.bean;

import java.sql.Date;

public class GerenciamentoRotasDTO extends AbstractGestaoForcaAtendimentoDTO {

    private static final long serialVersionUID = 457088775900104022L;

    private Date dataExecucao;
    private Integer idUsuario;
    private Integer idTipoSolicitacao;

    public Date getDataExecucao() {
        return dataExecucao;
    }

    public void setDataExecucao(final Date dataExecucao) {
        this.dataExecucao = dataExecucao;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(final Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getIdTipoSolicitacao() {
        return idTipoSolicitacao;
    }

    public void setIdTipoSolicitacao(final Integer idTipoSolicitacao) {
        this.idTipoSolicitacao = idTipoSolicitacao;
    }

}
