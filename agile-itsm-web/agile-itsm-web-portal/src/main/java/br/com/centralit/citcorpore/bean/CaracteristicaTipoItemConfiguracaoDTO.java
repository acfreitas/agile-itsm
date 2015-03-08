/**
 * CentralIT - CITSmart.
 */
package br.com.centralit.citcorpore.bean;

import java.sql.Date;

import br.com.citframework.dto.IDto;

/**
 * DTO de CaracteriticaTipoItemConfiguracao.
 * 
 * @author valdoilo.damasceno
 * 
 */
public class CaracteristicaTipoItemConfiguracaoDTO implements IDto {

    private static final long serialVersionUID = 5249455279181128086L;

    private Integer id;

    private Integer idTipoItemConfiguracao;

    private Integer idCaracteristica;

    private Date dataInicio;

    private Date dataFim;

    private String nameInfoAgente;

    /**
     * @return valor do atributo id.
     */
    public Integer getId() {
	return id;
    }

    /**
     * Define valor do atributo id.
     * 
     * @param id
     */
    public void setId(Integer id) {
	this.id = id;
    }

    /**
     * @return valor do atributo idTipoItemConfiguracao.
     */
    public Integer getIdTipoItemConfiguracao() {
	return idTipoItemConfiguracao;
    }

    /**
     * Define valor do atributo idTipoItemConfiguracao.
     * 
     * @param idTipoItemConfiguracao
     */
    public void setIdTipoItemConfiguracao(Integer idTipoItemConfiguracao) {
	this.idTipoItemConfiguracao = idTipoItemConfiguracao;
    }

    /**
     * @return valor do atributo idCaracteristica.
     */
    public Integer getIdCaracteristica() {
	return idCaracteristica;
    }

    /**
     * Define valor do atributo idCaracteristica.
     * 
     * @param idCaracteristica
     */
    public void setIdCaracteristica(Integer idCaracteristica) {
	this.idCaracteristica = idCaracteristica;
    }

    /**
     * @return valor do atributo dataInicio.
     */
    public Date getDataInicio() {
	return dataInicio;
    }

    /**
     * Define valor do atributo dataInicio.
     * 
     * @param dataInicio
     */
    public void setDataInicio(Date dataInicio) {
	this.dataInicio = dataInicio;
    }

    /**
     * @return valor do atributo dataFim.
     */
    public Date getDataFim() {
	return dataFim;
    }

    /**
     * Define valor do atributo dataFim.
     * 
     * @param dataFim
     */
    public void setDataFim(Date dataFim) {
	this.dataFim = dataFim;
    }

    /**
     * @return valor do atributo nameInfoAgente.
     */
    public String getNameInfoAgente() {
	return nameInfoAgente;
    }

    /**
     * Define valor do atributo nameInfoAgente.
     * 
     * @param nameInfoAgente
     */
    public void setNameInfoAgente(String nameInfoAgente) {
	this.nameInfoAgente = nameInfoAgente;
    }

}
