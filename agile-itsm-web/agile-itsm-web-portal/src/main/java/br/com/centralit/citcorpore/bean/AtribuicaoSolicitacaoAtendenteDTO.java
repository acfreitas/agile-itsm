package br.com.centralit.citcorpore.bean;

import java.sql.Date;
import java.sql.Timestamp;

import br.com.citframework.dto.IDto;

public class AtribuicaoSolicitacaoAtendenteDTO implements IDto {

    private static final long serialVersionUID = 117435070092875202L;

    private Integer id;
    private Integer idUsuario;
    private Integer idSolicitacao;
    private Integer priorityOrder;
    private Double latitude;
    private Double longitude;
    private Date dataExecucao;
    private Timestamp dataInicioAtendimento;
    private Integer active;

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(final Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getIdSolicitacao() {
        return idSolicitacao;
    }

    public void setIdSolicitacao(final Integer idSolicitacao) {
        this.idSolicitacao = idSolicitacao;
    }

    public Integer getPriorityOrder() {
        return priorityOrder;
    }

    public void setPriorityOrder(final Integer priorityOrder) {
        this.priorityOrder = priorityOrder;
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

    public Date getDataExecucao() {
        return dataExecucao;
    }

    public void setDataExecucao(final Date dataExecucao) {
        this.dataExecucao = dataExecucao;
    }

    public Timestamp getDataInicioAtendimento() {
        return dataInicioAtendimento;
    }

    public void setDataInicioAtendimento(final Timestamp dataInicioAtendimento) {
        this.dataInicioAtendimento = dataInicioAtendimento;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(final Integer active) {
        this.active = active;
    }

}
