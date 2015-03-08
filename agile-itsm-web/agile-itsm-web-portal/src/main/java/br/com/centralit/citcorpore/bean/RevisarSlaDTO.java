package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class RevisarSlaDTO implements IDto {
	
	private Integer idRevisarSla;
	private Integer idAcordoNivelServico;
	private java.sql.Date dataRevisao;
	private String detalheRevisao;
	private String observacao;
	private String deleted;
	
	public Integer getIdRevisarSla() {
		return idRevisarSla;
	}
	public void setIdRevisarSla(Integer idRevisarSla) {
		this.idRevisarSla = idRevisarSla;
	}
	public Integer getIdAcordoNivelServico() {
		return idAcordoNivelServico;
	}
	public void setIdAcordoNivelServico(Integer idAcordoNivelServico) {
		this.idAcordoNivelServico = idAcordoNivelServico;
	}
	public java.sql.Date getDataRevisao() {
		return dataRevisao;
	}
	public void setDataRevisao(java.sql.Date dataRevisao) {
		this.dataRevisao = dataRevisao;
	}
	public String getDetalheRevisao() {
		return detalheRevisao;
	}
	public void setDetalheRevisao(String detalheRevisao) {
		this.detalheRevisao = detalheRevisao;
	}
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	public String getDeleted() {
		return deleted;
	}
	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}
	
}
