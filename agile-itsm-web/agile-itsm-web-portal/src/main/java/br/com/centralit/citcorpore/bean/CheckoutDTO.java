package br.com.centralit.citcorpore.bean;

import java.sql.Timestamp;

import br.com.citframework.dto.IDto;

public class CheckoutDTO implements IDto {

    private static final long serialVersionUID = 4676707924223750301L;

    private Integer idCheckout;
    private Integer idSolicitacao;
    private Integer idTarefa;
    private Integer idUsuario;
    private Integer status;
    private Integer idResposta;
    private double latitude;
    private double longitude;
    private String descricao;
    private Timestamp dataHoraCheckout;

    public Integer getIdCheckout() {
        return idCheckout;
    }

    public void setIdCheckout(final Integer idCheckout) {
        this.idCheckout = idCheckout;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(final double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(final double longitude) {
        this.longitude = longitude;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(final Integer status) {
        this.status = status;
    }

    public Integer getIdResposta() {
        return idResposta;
    }

    public void setIdResposta(final Integer idResposta) {
        this.idResposta = idResposta;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(final String descricao) {
        this.descricao = descricao;
    }

    public Integer getIdTarefa() {
        return idTarefa;
    }

    public void setIdTarefa(final Integer idTarefa) {
        this.idTarefa = idTarefa;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(final Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Timestamp getDataHoraCheckout() {
        return dataHoraCheckout;
    }

    public void setDataHoraCheckout(final Timestamp dataHoraCheckout) {
        this.dataHoraCheckout = dataHoraCheckout;
    }

    public Integer getIdSolicitacao() {
        return idSolicitacao;
    }

    public void setIdSolicitacao(final Integer idSolicitacao) {
        this.idSolicitacao = idSolicitacao;
    }

}
