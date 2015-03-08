package br.com.centralit.citcorpore.bean;

import java.sql.Date;
import java.sql.Timestamp;

import br.com.citframework.dto.IDto;

/**
 * DTO que contém atributos comuns aos DTOs usados para persistência/filtro da Gestão da Força de trabalho
 *
 * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
 *
 */
public abstract class AbstractGestaoForcaAtendimentoDTO implements IDto {

    private static final long serialVersionUID = -85203475551247789L;

    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    // atributos para filtro
    private Integer idUF;
    private Integer idCidade;
    private Integer idContrato;
    private Integer idGrupo;
    private Integer idUnidade;
    private Integer idAtendente;
    private Date dataInicio;
    private Date dataFim;
    private Timestamp timestampInicio;
    private Timestamp timestampFim;

    public Integer getIdUF() {
        return idUF;
    }

    public void setIdUF(final Integer idUF) {
        this.idUF = idUF;
    }

    public Integer getIdCidade() {
        return idCidade;
    }

    public void setIdCidade(final Integer idCidade) {
        this.idCidade = idCidade;
    }

    public Integer getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(final Integer idContrato) {
        this.idContrato = idContrato;
    }

    public Integer getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(final Integer idGrupo) {
        this.idGrupo = idGrupo;
    }

    public Integer getIdUnidade() {
        return idUnidade;
    }

    public void setIdUnidade(final Integer idUnidade) {
        this.idUnidade = idUnidade;
    }

    public Integer getIdAtendente() {
        return idAtendente;
    }

    public void setIdAtendente(final Integer idAtendente) {
        this.idAtendente = idAtendente;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(final Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(final Date dataFim) {
        this.dataFim = dataFim;
    }

    public Timestamp getTimestampInicio() {
        return timestampInicio;
    }

    public void setTimestampInicio(final Timestamp timestampInicio) {
        this.timestampInicio = timestampInicio;
    }

    public Timestamp getTimestampFim() {
        return timestampFim;
    }

    public void setTimestampFim(final Timestamp timestampFim) {
        this.timestampFim = timestampFim;
    }

}
