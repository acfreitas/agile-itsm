package br.com.centralit.citcorpore.bean.result;

import java.sql.Timestamp;

public class HistoricoAtendimentoSearchResultDTO {

    private Integer atendenteId;
    private String atendenteNome;
    private Integer solicitacaoNumero;
    private String solicitacaoDescricao;
    private String solicitacaoServico;
    private String solicitacaoSituacao;
    private String solicitacaoSolicitante;
    private Integer solicitacaoPrazoHH;
    private Integer solicitacaoPrazoMM;
    private Double solicitacaoLatitude;
    private Double solicitacaoLongitude;
    private String unidadeNome;
    private Double posicaoLatitude;
    private Double posicaoLongitude;
    private Integer atribuicaoId;
    private Timestamp atribuicaoDatetime;

    public Integer getAtendenteId() {
        return atendenteId;
    }

    public void setAtendenteId(final Integer atendenteId) {
        this.atendenteId = atendenteId;
    }

    public String getAtendenteNome() {
        return atendenteNome;
    }

    public void setAtendenteNome(final String atendenteNome) {
        this.atendenteNome = atendenteNome;
    }

    public Integer getSolicitacaoNumero() {
        return solicitacaoNumero;
    }

    public void setSolicitacaoNumero(final Integer solicitacaoNumero) {
        this.solicitacaoNumero = solicitacaoNumero;
    }

    public String getSolicitacaoDescricao() {
        return solicitacaoDescricao;
    }

    public void setSolicitacaoDescricao(final String solicitacaoDescricao) {
        this.solicitacaoDescricao = solicitacaoDescricao;
    }

    public String getSolicitacaoServico() {
        return solicitacaoServico;
    }

    public void setSolicitacaoServico(final String solicitacaoServico) {
        this.solicitacaoServico = solicitacaoServico;
    }

    public String getSolicitacaoSituacao() {
        return solicitacaoSituacao;
    }

    public void setSolicitacaoSituacao(final String solicitacaoSituacao) {
        this.solicitacaoSituacao = solicitacaoSituacao;
    }

    public String getSolicitacaoSolicitante() {
        return solicitacaoSolicitante;
    }

    public void setSolicitacaoSolicitante(final String solicitacaoSolicitante) {
        this.solicitacaoSolicitante = solicitacaoSolicitante;
    }

    public Integer getSolicitacaoPrazoHH() {
        return solicitacaoPrazoHH;
    }

    public void setSolicitacaoPrazoHH(final Integer solicitacaoPrazoHH) {
        this.solicitacaoPrazoHH = solicitacaoPrazoHH;
    }

    public Integer getSolicitacaoPrazoMM() {
        return solicitacaoPrazoMM;
    }

    public void setSolicitacaoPrazoMM(final Integer solicitacaoPrazoMM) {
        this.solicitacaoPrazoMM = solicitacaoPrazoMM;
    }

    public Double getSolicitacaoLatitude() {
        return solicitacaoLatitude;
    }

    public void setSolicitacaoLatitude(final Double solicitacaoLatitude) {
        this.solicitacaoLatitude = solicitacaoLatitude;
    }

    public Double getSolicitacaoLongitude() {
        return solicitacaoLongitude;
    }

    public void setSolicitacaoLongitude(final Double solicitacaoLongitude) {
        this.solicitacaoLongitude = solicitacaoLongitude;
    }

    public String getUnidadeNome() {
        return unidadeNome;
    }

    public void setUnidadeNome(final String unidadeNome) {
        this.unidadeNome = unidadeNome;
    }

    public Double getPosicaoLatitude() {
        return posicaoLatitude;
    }

    public void setPosicaoLatitude(final Double posicaoLatitude) {
        this.posicaoLatitude = posicaoLatitude;
    }

    public Double getPosicaoLongitude() {
        return posicaoLongitude;
    }

    public void setPosicaoLongitude(final Double posicaoLongitude) {
        this.posicaoLongitude = posicaoLongitude;
    }

    public Integer getAtribuicaoId() {
        return atribuicaoId;
    }

    public void setAtribuicaoId(final Integer atribuicaoId) {
        this.atribuicaoId = atribuicaoId;
    }

    public Timestamp getAtribuicaoDatetime() {
        return atribuicaoDatetime;
    }

    public void setAtribuicaoDatetime(final Timestamp atribuicaoDatetime) {
        this.atribuicaoDatetime = atribuicaoDatetime;
    }

}
