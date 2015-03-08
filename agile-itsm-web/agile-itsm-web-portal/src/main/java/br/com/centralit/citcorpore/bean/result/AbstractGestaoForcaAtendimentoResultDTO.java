package br.com.centralit.citcorpore.bean.result;

import java.io.Serializable;

/**
 * DTO que contém atributos comuns aos DTOs usados para retorno às telas da Gestão da Força de trabalho
 *
 * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
 *
 */
public class AbstractGestaoForcaAtendimentoResultDTO implements Serializable {

    private static final long serialVersionUID = 2640914796382482552L;

    private Integer numeroSolicitacao;
    private Integer prazoHH;
    private Integer prazoMM;
    private String situacao;
    private String descricao;
    private String nomeAtendente;

    public Integer getNumeroSolicitacao() {
        return numeroSolicitacao;
    }

    public void setNumeroSolicitacao(final Integer numeroSolicitacao) {
        this.numeroSolicitacao = numeroSolicitacao;
    }

    public Integer getPrazoHH() {
        return prazoHH;
    }

    public void setPrazoHH(final Integer prazoHH) {
        this.prazoHH = prazoHH;
    }

    public Integer getPrazoMM() {
        return prazoMM;
    }

    public void setPrazoMM(final Integer prazoMM) {
        this.prazoMM = prazoMM;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(final String situacao) {
        this.situacao = situacao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(final String descricao) {
        this.descricao = descricao;
    }

    public String getNomeAtendente() {
        return nomeAtendente;
    }

    public void setNomeAtendente(final String nomeAtendente) {
        this.nomeAtendente = nomeAtendente;
    }

}
