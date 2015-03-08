package br.com.centralit.citcorpore.bean;

import java.sql.Date;

import br.com.citframework.dto.IDto;
/**
 * @author thiago matias
 *  
 */
public class RequisicaoLiberacaoResponsavelDTO implements IDto {
	
	private static final long serialVersionUID = 1L;
	private Integer idRequisicaoLiberacao;
	private Integer idResponsavel;
	//private Integer idhistoricoliberacao;
	
	private Integer idRequisicaoLiberacaoResp;
	private String nomeResponsavel;
	private String papelResponsavel;
	private String telResponsavel;
	private String emailResponsavel;
	private String nomeCargo;
	private Date dataFim;
	private Integer sequenciaEmpregado;

	
	public Integer getIdRequisicaoLiberacaoResp() {
		return idRequisicaoLiberacaoResp;
	}
	public void setIdRequisicaoLiberacaoResp(Integer idRequisicaoLiberacaoResp) {
		this.idRequisicaoLiberacaoResp = idRequisicaoLiberacaoResp;
	}
	
	public Integer getIdRequisicaoLiberacao(){
		return this.idRequisicaoLiberacao;
	}
	public void setIdRequisicaoLiberacao(Integer parm){
		this.idRequisicaoLiberacao = parm;
	}

	public Integer getIdResponsavel(){
		return this.idResponsavel;
	}
	public void setIdResponsavel(Integer parm){
		this.idResponsavel = parm;
	}
    
	/*public Integer getIdhistoricoliberacao() {
		return idhistoricoliberacao;
	}
	public void setIdhistoricoliberacao(Integer idhistoricoliberacao) {
		this.idhistoricoliberacao = idhistoricoliberacao;
	}*/
	public String getNomeResponsavel() {
		return nomeResponsavel;
	}
	public void setNomeResponsavel(String nomeResponsavel) {
		this.nomeResponsavel = nomeResponsavel;
	}
	public String getPapelResponsavel() {
		return papelResponsavel;
	}
	public void setPapelResponsavel(String papelResponsavel) {
		this.papelResponsavel = papelResponsavel;
	}
	public String getTelResponsavel() {
		return telResponsavel;
	}
	public void setTelResponsavel(String telResponsavel) {
		this.telResponsavel = telResponsavel;
	}
	public String getEmailResponsavel() {
		return emailResponsavel;
	}
	public void setEmailResponsavel(String emailResponsavel) {
		this.emailResponsavel = emailResponsavel;
	}
	public String getNomeCargo() {
		return nomeCargo;
	}
	public void setNomeCargo(String nomeCargo) {
		this.nomeCargo = nomeCargo;
	}
	public Date getDataFim() {
		return dataFim;
	}
	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}
	/**
	 * @return the sequenciaEmpregado
	 */
	public Integer getSequenciaEmpregado() {
		return sequenciaEmpregado;
	}
	/**
	 * @param sequenciaEmpregado the sequenciaEmpregado to set
	 */
	public void setSequenciaEmpregado(Integer sequenciaEmpregado) {
		this.sequenciaEmpregado = sequenciaEmpregado;
	}

}
