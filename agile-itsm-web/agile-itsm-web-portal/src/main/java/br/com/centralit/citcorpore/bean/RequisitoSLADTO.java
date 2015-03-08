package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class RequisitoSLADTO implements IDto {
	private Integer idRequisitoSLA;
	private Integer idEmpregado;
	private String assunto;
	private String detalhamento;
	private String situacao;
	private java.sql.Date requisitadoEm;
	private java.sql.Date criadoEm;
	private java.sql.Date modificadoEm;
	private String criadoPor;
	private String modificadoPor;
	private String deleted;
	private java.sql.Date dataVinculacao;

	public Integer getIdRequisitoSLA(){
		return this.idRequisitoSLA;
	}
	public void setIdRequisitoSLA(Integer parm){
		this.idRequisitoSLA = parm;
	}

	public Integer getIdEmpregado(){
		return this.idEmpregado;
	}
	public void setIdEmpregado(Integer parm){
		this.idEmpregado = parm;
	}

	public String getAssunto(){
		return this.assunto;
	}
	public void setAssunto(String parm){
		this.assunto = parm;
	}

	public String getDetalhamento(){
		return this.detalhamento;
	}
	public void setDetalhamento(String parm){
		this.detalhamento = parm;
	}

	public String getSituacao(){
		return this.situacao;
	}
	public void setSituacao(String parm){
		this.situacao = parm;
	}

	public java.sql.Date getRequisitadoEm(){
		return this.requisitadoEm;
	}
	public void setRequisitadoEm(java.sql.Date parm){
		this.requisitadoEm = parm;
	}

	public java.sql.Date getCriadoEm(){
		return this.criadoEm;
	}
	public void setCriadoEm(java.sql.Date parm){
		this.criadoEm = parm;
	}

	public java.sql.Date getModificadoEm(){
		return this.modificadoEm;
	}
	public void setModificadoEm(java.sql.Date parm){
		this.modificadoEm = parm;
	}

	public String getCriadoPor(){
		return this.criadoPor;
	}
	public void setCriadoPor(String parm){
		this.criadoPor = parm;
	}

	public String getModificadoPor(){
		return this.modificadoPor;
	}
	public void setModificadoPor(String parm){
		this.modificadoPor = parm;
	}

	public String getDeleted(){
		return this.deleted;
	}
	public void setDeleted(String parm){
		this.deleted = parm;
	}
	public java.sql.Date getDataVinculacao() {
		return dataVinculacao;
	}
	public void setDataVinculacao(java.sql.Date dataVinculacao) {
		this.dataVinculacao = dataVinculacao;
	}

}
