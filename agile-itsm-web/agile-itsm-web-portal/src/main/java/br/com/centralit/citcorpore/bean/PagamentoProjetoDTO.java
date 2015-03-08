package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class PagamentoProjetoDTO implements IDto {
	private Integer idPagamentoProjeto;
	private Integer idProjeto;
	private java.sql.Date dataPagamento;
	private Double valorPagamento;
	private Double valorGlosa;
	private String detPagamento;
	private String situacao;
	private java.sql.Date dataUltAlteracao;
	private String horaUltAlteracao;
	private String usuarioUltAlteracao;
	
	private java.sql.Date dataPagamentoAtu;
	
	private Integer idMarcoPagamentoPrj;
	
	private Integer[] idTarefasParaPagamento;

	public Integer getIdPagamentoProjeto(){
		return this.idPagamentoProjeto;
	}
	public void setIdPagamentoProjeto(Integer parm){
		this.idPagamentoProjeto = parm;
	}

	public Integer getIdProjeto(){
		return this.idProjeto;
	}
	public void setIdProjeto(Integer parm){
		this.idProjeto = parm;
	}

	public java.sql.Date getDataPagamento(){
		return this.dataPagamento;
	}
	public void setDataPagamento(java.sql.Date parm){
		this.dataPagamento = parm;
	}

	public Double getValorPagamento(){
		return this.valorPagamento;
	}
	public void setValorPagamento(Double parm){
		this.valorPagamento = parm;
	}

	public String getDetPagamento(){
		return this.detPagamento;
	}
	public void setDetPagamento(String parm){
		this.detPagamento = parm;
	}

	public String getSituacao(){
		return this.situacao;
	}
	public void setSituacao(String parm){
		this.situacao = parm;
	}

	public java.sql.Date getDataUltAlteracao(){
		return this.dataUltAlteracao;
	}
	public void setDataUltAlteracao(java.sql.Date parm){
		this.dataUltAlteracao = parm;
	}

	public String getHoraUltAlteracao(){
		return this.horaUltAlteracao;
	}
	public void setHoraUltAlteracao(String parm){
		this.horaUltAlteracao = parm;
	}

	public String getUsuarioUltAlteracao(){
		return this.usuarioUltAlteracao;
	}
	public void setUsuarioUltAlteracao(String parm){
		this.usuarioUltAlteracao = parm;
	}
	public Double getValorGlosa() {
		return valorGlosa;
	}
	public void setValorGlosa(Double valorGlosa) {
		this.valorGlosa = valorGlosa;
	}
	public Integer[] getIdTarefasParaPagamento() {
		return idTarefasParaPagamento;
	}
	public void setIdTarefasParaPagamento(Integer[] idTarefasParaPagamento) {
		this.idTarefasParaPagamento = idTarefasParaPagamento;
	}
	public Integer getIdMarcoPagamentoPrj() {
		return idMarcoPagamentoPrj;
	}
	public void setIdMarcoPagamentoPrj(Integer idMarcoPagamentoPrj) {
		this.idMarcoPagamentoPrj = idMarcoPagamentoPrj;
	}
	public java.sql.Date getDataPagamentoAtu() {
		return dataPagamentoAtu;
	}
	public void setDataPagamentoAtu(java.sql.Date dataPagamentoAtu) {
		this.dataPagamentoAtu = dataPagamentoAtu;
	}

}
