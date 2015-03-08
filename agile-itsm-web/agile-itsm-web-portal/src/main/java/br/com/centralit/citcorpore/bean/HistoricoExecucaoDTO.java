package br.com.centralit.citcorpore.bean;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import br.com.centralit.citajax.util.JavaScriptUtil;
import br.com.citframework.dto.IDto;
import br.com.citframework.util.UtilDatas;

public class HistoricoExecucaoDTO implements IDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 175188654082235948L;
	private Integer idHistorico;
	private Integer idExecucao;
	private Date data;
	private String situacao;
	private Integer idEmpregadoExecutor;
	private String detalhamento;
	private Long hora;
	private String nomeEmpregado;
	
	private Integer idDemanda;
	
	public String getDataStr() {
		return UtilDatas.dateToSTR(data);
	}	
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public Integer getIdEmpregadoExecutor() {
		return idEmpregadoExecutor;
	}
	public void setIdEmpregadoExecutor(Integer idEmpregadoExecutor) {
		this.idEmpregadoExecutor = idEmpregadoExecutor;
	}
	public Integer getIdExecucao() {
		return idExecucao;
	}
	public void setIdExecucao(Integer idExecucao) {
		this.idExecucao = idExecucao;
	}
	public Integer getIdHistorico() {
		return idHistorico;
	}
	public void setIdHistorico(Integer idHistorico) {
		this.idHistorico = idHistorico;
	}
	public String getSituacaoDesc() {
		if (this.situacao == null) return "";
		if (this.situacao.equalsIgnoreCase("N")) return "Não Iniciada";
		if (this.situacao.equalsIgnoreCase("I")) return "Em Execução";
		if (this.situacao.equalsIgnoreCase("F")) return "Finalizada";
		if (this.situacao.equalsIgnoreCase("C")) return "Paralisada - Aguard. Cliente";
		if (this.situacao.equalsIgnoreCase("P")) return "Paralisada - Interno";
		if (this.situacao.equalsIgnoreCase("T")) return "Transferida";
		return situacao;
	}	
	public String getSituacao() {
		return situacao;
	}
	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
	public String getDetalhamentoConv() {
		return JavaScriptUtil.escapeJavaScript(detalhamento);
	}	
	public String getDetalhamento() {
		return detalhamento;
	}
	public void setDetalhamento(String detalhamento) {
		this.detalhamento = detalhamento;
	}
	public String getHoraStr() {
		if (hora == null) return "";
		Timestamp t = new Timestamp(hora.longValue());
		
    	SimpleDateFormat format = new SimpleDateFormat("HH:mm");
    	String s = format.format(t);
		return s;
	}	
	public String getNomeEmpregado() {
		return nomeEmpregado;
	}
	public void setNomeEmpregado(String nomeEmpregado) {
		this.nomeEmpregado = nomeEmpregado;
	}
	public Integer getIdDemanda() {
		return idDemanda;
	}
	public void setIdDemanda(Integer idDemanda) {
		this.idDemanda = idDemanda;
	}
	public Long getHora() {
		return hora;
	}
	public void setHora(Long hora) {
		this.hora = hora;
	}

}
