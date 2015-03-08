package br.com.centralit.citcorpore.bean;

import java.sql.Timestamp;

import br.com.citframework.dto.IDto;

public class CheckinDTO implements IDto {

    private static final long serialVersionUID = -8948858796588907823L;

    private Integer idCheckin;
    private Integer idSolicitacao;
    private Integer idTarefa;
    private Double latitude;
    private Double longitude;
    private Timestamp dataHoraCheckin;
    private Integer idUsuario;
    private String nomeEmpregado;

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

    public Timestamp getDataHoraCheckin() {
        return dataHoraCheckin;
    }

    public void setDataHoraCheckin(final Timestamp dataHoraCheckin) {
        this.dataHoraCheckin = dataHoraCheckin;
    }

    public Integer getIdCheckin() {
        return idCheckin;
    }

    public void setIdCheckin(final Integer idCheckin) {
        this.idCheckin = idCheckin;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(final Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNomeEmpregado() {
        return nomeEmpregado;
    }

    public void setNomeEmpregado(final String nomeEmpregado) {
        this.nomeEmpregado = nomeEmpregado;
    }

}
