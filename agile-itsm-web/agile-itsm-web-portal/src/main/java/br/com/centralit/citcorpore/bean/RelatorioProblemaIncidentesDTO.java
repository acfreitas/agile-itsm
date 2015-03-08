package br.com.centralit.citcorpore.bean;

import java.sql.Date;
import java.util.Collection;

import br.com.citframework.dto.IDto;

public class RelatorioProblemaIncidentesDTO implements IDto{


	private static final long serialVersionUID = 1L;
	
	private Integer idProblema;
	private String tituloProblema;
	private String descricao;
	private Integer idContrato;
	private String contrato;
	private Collection<SolicitacaoServicoDTO> colSolicitacaoServico;
	
	private Date dataInicio;
	private Date dataFim;
	private String proprietario;
	private String formatoArquivoRelatorio;
	
	private Integer totalSolicitacaoServicoPorProblema;
	
	public Integer getIdProblema() {
		return idProblema;
	}
	public void setIdProblema(Integer idProblema) {
		this.idProblema = idProblema;
	}
	public String getTituloProblema() {
		return tituloProblema;
	}
	public void setTituloProblema(String tituloProblema) {
		this.tituloProblema = tituloProblema;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Integer getIdContrato() {
		return idContrato;
	}
	public void setIdContrato(Integer idContrato) {
		this.idContrato = idContrato;
	}
	public String getContrato() {
		return contrato;
	}
	public void setContrato(String contrato) {
		this.contrato = contrato;
	}
	public Collection<SolicitacaoServicoDTO> getColSolicitacaoServico() {
		return colSolicitacaoServico;
	}
	public void setColSolicitacaoServico(
			Collection<SolicitacaoServicoDTO> colSolicitacaoServico) {
		this.colSolicitacaoServico = colSolicitacaoServico;
	}
	public Date getDataInicio() {
		return dataInicio;
	}
	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}
	public Date getDataFim() {
		return dataFim;
	}
	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}
	public String getProprietario() {
		return proprietario;
	}
	public void setProprietario(String proprietario) {
		this.proprietario = proprietario;
	}
	public String getFormatoArquivoRelatorio() {
		return formatoArquivoRelatorio;
	}
	public void setFormatoArquivoRelatorio(String formatoArquivoRelatorio) {
		this.formatoArquivoRelatorio = formatoArquivoRelatorio;
	}
	
	public Integer getTotalSolicitacaoServicoPorProblema() {
		return totalSolicitacaoServicoPorProblema;
	}
	public void setTotalSolicitacaoServicoPorProblema(Integer totalSolicitacaoServicoPorProblema) {
		this.totalSolicitacaoServicoPorProblema = totalSolicitacaoServicoPorProblema;
	}

}
