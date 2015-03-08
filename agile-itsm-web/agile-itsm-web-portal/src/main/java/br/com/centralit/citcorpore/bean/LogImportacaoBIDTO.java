package br.com.centralit.citcorpore.bean;

import java.sql.Timestamp;

import br.com.citframework.dto.IDto;

public class LogImportacaoBIDTO implements IDto {

	private static final long serialVersionUID = 1L;
	private Integer idLogImportacao;
	private Timestamp dataHoraInicio;
	private Timestamp dataHoraFim;
	private String status;
	private StringBuilder detalhamento;
	private String tipo;
	private Integer idConexaoBI;
	private Integer paginaSelecionada;
	
	public LogImportacaoBIDTO() {
		super();
		this.detalhamento = new StringBuilder();
	}
	
	public Integer getIdLogImportacao() {
		return idLogImportacao;
	}
	public void setIdLogImportacao(Integer idLogImportacao) {
		this.idLogImportacao = idLogImportacao;
	}
	public Timestamp getDataHoraInicio() {
		return dataHoraInicio;
	}
	public void setDataHoraInicio(Timestamp dataHoraInicio) {
		this.dataHoraInicio = dataHoraInicio;
	}
	public Timestamp getDataHoraFim() {
		return dataHoraFim;
	}
	public void setDataHoraFim(Timestamp dataHoraFim) {
		this.dataHoraFim = dataHoraFim;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDetalhamento() {
		return detalhamento.toString();
	}
	public void setDetalhamento(String detalhamento) {
		this.clear();
		this.concatDetalhamento(detalhamento);
	}
	public void concatDetalhamento(String detalhamento){
		if (this.detalhamento.length()>0){
			this.detalhamento.append("\r\n");
		}
		this.detalhamento.append(detalhamento);
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public Integer getIdConexaoBI() {
		return idConexaoBI;
	}
	public void setIdConexaoBI(Integer idConexaoBI) {
		this.idConexaoBI = idConexaoBI;
	}
	public Integer getPaginaSelecionada() {
		return paginaSelecionada;
	}
	public void setPaginaSelecionada(Integer paginaSelecionada) {
		this.paginaSelecionada = paginaSelecionada;
	}
	public void clear(){
		this.detalhamento.delete(0, this.detalhamento.length());
	}
	
}
