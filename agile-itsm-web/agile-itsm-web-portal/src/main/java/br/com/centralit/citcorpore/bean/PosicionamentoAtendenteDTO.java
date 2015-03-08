package br.com.centralit.citcorpore.bean;

import java.sql.Timestamp;

public class PosicionamentoAtendenteDTO extends AbstractGestaoForcaAtendimentoDTO {

    private static final long serialVersionUID = 3700982149345503945L;

    private Double latitude;
    private Double longitude;
    private Timestamp dateTime;
    private Integer idUsuario;

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

    public Timestamp getDateTime() {
        return dateTime;
    }

    public void setDateTime(final Timestamp dateTime) {
        this.dateTime = dateTime;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(final Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

}
