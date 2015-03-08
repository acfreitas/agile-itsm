package br.com.centralit.citcorpore.bean.result;

import java.io.Serializable;
import java.sql.Timestamp;

public class GerenciamentoRotasResultDTO implements Serializable {

    private static final long serialVersionUID = -5409365859829245983L;

    private Integer idSolicitacao;
    private Integer idTarefa;
    private String nomeContrato;
    private Integer prazoHH;
    private Integer prazoMM;
    private String tipo;
    private String situacao;
    private String nomeUnidade;
    private Double latitude;
    private Double longitude;
    private String descricao;
    private Integer idAtribuicao;
    private Integer priorityOrder;
    private Timestamp dataInicioAtendimento;
    private Boolean iniciada;

    public Integer getIdSolicitacao() {
        return idSolicitacao;
    }

    public void setIdSolicitacao(final Integer idSolicitacao) {
        this.idSolicitacao = idSolicitacao;
    }

    public Integer getIdTarefa() {
        return idTarefa;
    }

    public void setIdTarefa(final Integer idTarefa) {
        this.idTarefa = idTarefa;
    }

    public String getNomeContrato() {
        return nomeContrato;
    }

    public void setNomeContrato(final String nomeContrato) {
        this.nomeContrato = nomeContrato;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(final String tipo) {
        this.tipo = tipo;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(final String situacao) {
        this.situacao = situacao;
    }

    public String getNomeUnidade() {
        return nomeUnidade;
    }

    public void setNomeUnidade(final String nomeUnidade) {
        this.nomeUnidade = nomeUnidade;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(final Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(final Double longitude) {
        this.longitude = longitude;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(final String descricao) {
        this.descricao = descricao;
    }

    public Integer getIdAtribuicao() {
        return idAtribuicao;
    }

    public void setIdAtribuicao(final Integer idAtribuicao) {
        this.idAtribuicao = idAtribuicao;
    }

    public Integer getPriorityOrder() {
        return priorityOrder;
    }

    public void setPriorityOrder(final Integer priorityOrder) {
        this.priorityOrder = priorityOrder;
    }

    public Timestamp getDataInicioAtendimento() {
        return dataInicioAtendimento;
    }

    public void setDataInicioAtendimento(final Timestamp dataInicioAtendimento) {
        this.dataInicioAtendimento = dataInicioAtendimento;
    }

    public Boolean getIniciada() {
        return iniciada;
    }

    public void setIniciada(final Boolean iniciada) {
        this.iniciada = iniciada;
    }

}
