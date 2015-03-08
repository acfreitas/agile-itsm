package br.com.centralit.citcorpore.bean.result;

import java.sql.Timestamp;

public class PosicionamentoAtendenteResultDTO extends AbstractGestaoForcaAtendimentoResultDTO {

    private static final long serialVersionUID = 8056482820824089171L;

    private Integer idUsuario;
    private Double latitude;
    private Double longitude;
    private String lastSeem;
    private Timestamp ultimaVisualizacao;

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

    public String getLastSeem() {
        return lastSeem;
    }

    public void setLastSeem(final String lastSeem) {
        this.lastSeem = lastSeem;
    }

    public Timestamp getUltimaVisualizacao() {
        return ultimaVisualizacao;
    }

    public void setUltimaVisualizacao(final Timestamp ultimaVisualizacao) {
        this.ultimaVisualizacao = ultimaVisualizacao;
    }

}
