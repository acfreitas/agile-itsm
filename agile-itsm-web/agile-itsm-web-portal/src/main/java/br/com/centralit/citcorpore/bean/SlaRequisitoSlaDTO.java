package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

/**
 * @since 14/06/2013
 * @author rodrigo.oliveira
 *
 */
public class SlaRequisitoSlaDTO implements IDto {
	
	private Integer idRequisitoSLA;
	private Integer idAcordoNivelServico;
	private java.sql.Date dataVinculacao;
	private java.sql.Date dataUltModificacao;
	private String deleted;
	
	public Integer getIdRequisitoSLA() {
		return idRequisitoSLA;
	}
	public void setIdRequisitoSLA(Integer idRequisitoSLA) {
		this.idRequisitoSLA = idRequisitoSLA;
	}
	public Integer getIdAcordoNivelServico() {
		return idAcordoNivelServico;
	}
	public void setIdAcordoNivelServico(Integer idAcordoNivelServico) {
		this.idAcordoNivelServico = idAcordoNivelServico;
	}
	public java.sql.Date getDataVinculacao() {
		return dataVinculacao;
	}
	public void setDataVinculacao(java.sql.Date dataVinculacao) {
		this.dataVinculacao = dataVinculacao;
	}
	public java.sql.Date getDataUltModificacao() {
		return dataUltModificacao;
	}
	public void setDataUltModificacao(java.sql.Date dataUltModificacao) {
		this.dataUltModificacao = dataUltModificacao;
	}
	public String getDeleted() {
		return deleted;
	}
	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}

}
