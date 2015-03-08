package br.com.centralit.citcorpore.bean;

import java.sql.Date;

import br.com.citframework.dto.IDto;

/**
 * @author rosana.godinho
 * 
 */
public class EmpresaDTO implements IDto {

    private static final long serialVersionUID = 1582364224581163482L;
    private Integer idEmpresa;
    private String nomeEmpresa;
    private Date dataInicio;
    private Date dataFim;
    private String detalhamento;

    /**
     * @return valor do atributo idEmpresa.
     */
    public Integer getIdEmpresa() {
	return idEmpresa;
    }

    /**
     * Define valor do atributo idEmpresa.
     * 
     * @param idEmpresa
     */
    public void setIdEmpresa(Integer idEmpresa) {
	this.idEmpresa = idEmpresa;
    }

    /**
     * @return valor do atributo nomeEmpresa.
     */
    public String getNomeEmpresa() {
	return nomeEmpresa;
    }

    /**
     * Define valor do atributo nomeEmpresa.
     * 
     * @param nomeEmpresa
     */
    public void setNomeEmpresa(String nomeEmpresa) {
	this.nomeEmpresa = nomeEmpresa;
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
     * @return valor do atributo detalhamento.
     */
    public String getDetalhamento() {
	return detalhamento;
    }

    /**
     * Define valor do atributo detalhamento.
     * 
     * @param detalhamento
     */
    public void setDetalhamento(String detalhamento) {
	this.detalhamento = detalhamento;
    }

}
