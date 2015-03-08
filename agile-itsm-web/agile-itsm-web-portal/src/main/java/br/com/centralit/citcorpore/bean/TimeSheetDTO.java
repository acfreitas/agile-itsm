package br.com.centralit.citcorpore.bean;

import java.sql.Date;

import br.com.centralit.citajax.util.JavaScriptUtil;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilFormatacao;

public class TimeSheetDTO implements IDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8184473698460030815L;
	private Integer idTimeSheet;
	private Integer idDemanda;
	private Integer idEmpregado;
	private Integer idCliente;
	private Integer idProjeto;
	private Double qtdeHoras;
	private Date data;
	private Double custoPorHora;
	private String detalhamento;
	
	private String nomeCliente;
	private String nomeProjeto;
	private String nomeEmpregado;
	
	private String detalhamentoDemanda;
	public String getDataStr() {
		if (this.data == null) return "";
		return UtilDatas.dateToSTR(data);
	}	
	public String getDataStrDet() {
		if (this.data == null) return "";
		try {
			return UtilDatas.dateToSTR(data) + " (" + UtilDatas.getDiaSemana(data) + ")";
		} catch (LogicException e) {
			e.printStackTrace();
			return "";
		}
	}		
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public Integer getIdDemanda() {
		return idDemanda;
	}
	public void setIdDemanda(Integer idDemanda) {
		this.idDemanda = idDemanda;
	}
	public Integer getIdEmpregado() {
		return idEmpregado;
	}
	public void setIdEmpregado(Integer idEmpregado) {
		this.idEmpregado = idEmpregado;
	}
	public Integer getIdProjeto() {
		return idProjeto;
	}
	public void setIdProjeto(Integer idProjeto) {
		this.idProjeto = idProjeto;
	}
	public Integer getIdTimeSheet() {
		return idTimeSheet;
	}
	public void setIdTimeSheet(Integer idTimeSheet) {
		this.idTimeSheet = idTimeSheet;
	}
	public Integer getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}
	public Double getCustoPorHora() {
		return custoPorHora;
	}
	public void setCustoPorHora(Double custoPorHora) {
		this.custoPorHora = custoPorHora;
	}
	public String getQtdeHorasStr() {
		return UtilFormatacao.formatDouble(qtdeHoras,2);
	}	
	public String getQtdeHorasStr2() {
		if (qtdeHoras == 0){
			return "--";
		}
		return UtilFormatacao.formatDouble(qtdeHoras,2);
	}	
	public Double getQtdeHoras() {
		return qtdeHoras;
	}
	public void setQtdeHoras(Double qtdeHoras) {
		this.qtdeHoras = qtdeHoras;
	}
	public String getNomeCliente() {
		return nomeCliente;
	}
	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}
	public String getNomeProjeto() {
		return nomeProjeto;
	}
	public void setNomeProjeto(String nomeProjeto) {
		this.nomeProjeto = nomeProjeto;
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
	public String getNomeEmpregado() {
		return nomeEmpregado;
	}
	public void setNomeEmpregado(String nomeEmpregado) {
		this.nomeEmpregado = nomeEmpregado;
	}
	public String getDetalhamentoDemanda() {
		return detalhamentoDemanda;
	}
	public void setDetalhamentoDemanda(String detalhamentoDemanda) {
		this.detalhamentoDemanda = detalhamentoDemanda;
	}
}
