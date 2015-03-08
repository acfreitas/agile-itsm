package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class ProcessamentoBatchDTO implements IDto {

	private static final long serialVersionUID = 1060402879727020452L;

	private String ano;
	private String conteudo;
	private String descricao;
	private String diaDaSemana;
	private String diaDoMes;
	private String expressaoCRON;
	private String horas;
	private Integer idProcessamentoBatch;
	private String mes;
	private String minutos;
	private String nomeClasseJobService;
	private String segundos;
	private String situacao;
	private String tipo;
	private Integer idConexaoBI;
	private String abriuAgendamentoExcecao;
	
	public String getAno() {
		return ano;
	}

	public String getConteudo() {
		return conteudo;
	}

	public String getDescricao() {
		return descricao;
	}

	public String getDiaDaSemana() {
		return diaDaSemana;
	}

	public String getDiaDoMes() {
		return diaDoMes;
	}

	public String getExpressaoCRON() {
		return expressaoCRON;
	}

	public String getHoras() {
		return horas;
	}

	public Integer getIdProcessamentoBatch() {
		return idProcessamentoBatch;
	}

	public String getMes() {
		return mes;
	}

	public String getMinutos() {
		return minutos;
	}

	public String getNomeClasseJobService() {
		return nomeClasseJobService;
	}

	public String getSegundos() {
		return segundos;
	}

	public String getSituacao() {
		return situacao;
	}

	public String getTipo() {
		return tipo;
	}

	public void setAno(String ano) {
		this.ano = ano;
	}

	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public void setDiaDaSemana(String diaDaSemana) {
		this.diaDaSemana = diaDaSemana;
	}

	public void setDiaDoMes(String diaDoMes) {
		this.diaDoMes = diaDoMes;
	}

	public void setExpressaoCRON(String expressaoCRON) {
		this.expressaoCRON = expressaoCRON;
	}

	public void setHoras(String horas) {
		this.horas = horas;
	}

	public void setIdProcessamentoBatch(Integer idProcessamentoBatch) {
		this.idProcessamentoBatch = idProcessamentoBatch;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}

	public void setMinutos(String minutos) {
		this.minutos = minutos;
	}

	public void setNomeClasseJobService(String nomeClasseJobService) {
		this.nomeClasseJobService = nomeClasseJobService;
	}

	public void setSegundos(String segundos) {
		this.segundos = segundos;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
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
	
	public String getAbriuAgendamentoExcecao() {
		return abriuAgendamentoExcecao;
	}

	public void setAbriuAgendamentoExcecao(String abriuAgendamentoExcecao) {
		this.abriuAgendamentoExcecao = abriuAgendamentoExcecao;
	}
}
