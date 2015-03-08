package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class GlosaOSBIDTO implements IDto {

	private static final long serialVersionUID = -5747818983870515883L;
	private Integer idGlosaOS;
	private Integer idOs;
	private java.sql.Date dataCriacao;
	private java.sql.Date dataUltModificacao;
	private String descricaoGlosa;
	private String ocorrencias;
	private Double percAplicado;
	private Double custoGlosa;
	private Double numeroOcorrencias;
	private Integer sequencia;
	private Integer idConexaoBI;

	public Integer getIdGlosaOS(){
		return this.idGlosaOS;
	}
	public void setIdGlosaOS(Integer parm){
		this.idGlosaOS = parm;
	}

	public Integer getIdOs(){
		return this.idOs;
	}
	public void setIdOs(Integer parm){
		this.idOs = parm;
	}

	public java.sql.Date getDataCriacao(){
		return this.dataCriacao;
	}
	public void setDataCriacao(java.sql.Date parm){
		this.dataCriacao = parm;
	}

	public java.sql.Date getDataUltModificacao(){
		return this.dataUltModificacao;
	}
	public void setDataUltModificacao(java.sql.Date parm){
		this.dataUltModificacao = parm;
	}

	public String getDescricaoGlosa(){
		return this.descricaoGlosa;
	}
	public void setDescricaoGlosa(String parm){
		this.descricaoGlosa = parm;
	}

	public String getOcorrencias(){
		return this.ocorrencias;
	}
	public void setOcorrencias(String parm){
		this.ocorrencias = parm;
	}

	public Double getPercAplicado(){
		return this.percAplicado;
	}
	public void setPercAplicado(Double parm){
		this.percAplicado = parm;
	}

	public Double getCustoGlosa(){
		return this.custoGlosa;
	}
	public void setCustoGlosa(Double parm){
		this.custoGlosa = parm;
	}

	public Double getNumeroOcorrencias(){
		return this.numeroOcorrencias;
	}
	public void setNumeroOcorrencias(Double parm){
		this.numeroOcorrencias = parm;
	}
	public Integer getSequencia() {
		return sequencia;
	}
	public void setSequencia(Integer sequencia) {
		this.sequencia = sequencia;
	}
	public Integer getIdConexaoBI() {
		return idConexaoBI;
	}
	public void setIdConexaoBI(Integer idConexaoBI) {
		this.idConexaoBI = idConexaoBI;
	}

}
