package br.com.centralit.citcorpore.bean;

import java.sql.Timestamp;

import br.com.citframework.dto.IDto;

public class CheckinDeniedDTO implements IDto {

    private static final long serialVersionUID = -7846800541358391118L;

    private Integer idCheckinDenied;
    private Integer idTarefa;
    private Integer idUsuario;
    private Integer idJustificativa;
    private Double latitude;
    private Double longitude;
    private Timestamp dataHora;

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

    public Timestamp getDataHora() {
        return dataHora;
    }

    public void setDataHora(final Timestamp dataHora) {
        this.dataHora = dataHora;
    }

    public Integer getIdJustificativa() {
        return idJustificativa;
    }

    public void setIdJustificativa(final Integer idJustificativa) {
        this.idJustificativa = idJustificativa;
    }

    public Integer getIdCheckinDenied() {
        return idCheckinDenied;
    }

    public void setIdCheckinDenied(final Integer idCheckinDenied) {
        this.idCheckinDenied = idCheckinDenied;
    }

}
