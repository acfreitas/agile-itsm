package br.com.centralit.citcorpore.bean;

import java.sql.Date;

import br.com.citframework.dto.IDto;

public class MonitoramentoAtivosDTO implements IDto {

	private static final long serialVersionUID = 1L;

	private Integer idMonitoramentoAtivos;

	private Integer idTipoItemConfiguracao;

	private String titulo;

	private String descricao;

	private String tipoRegra;

	private String enviarEmail;

	private String criarProblema;

	private String criarIncidente;

	private Date dataInicio;

	private Date dataFim;

	private Integer idCaracteristica;

	private String script;

	private Integer[] usuariosNotificacao;

	private Integer[] gruposNotificacao;
	
	private boolean scriptSuccess;

	public Integer getIdMonitoramentoAtivos() {
		return idMonitoramentoAtivos;
	}

	public void setIdMonitoramentoAtivos(Integer idMonitoramentoAtivos) {
		this.idMonitoramentoAtivos = idMonitoramentoAtivos;
	}

	public Integer getIdTipoItemConfiguracao() {
		return idTipoItemConfiguracao;
	}

	public void setIdTipoItemConfiguracao(Integer idTipoItemConfiguracao) {
		this.idTipoItemConfiguracao = idTipoItemConfiguracao;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getTipoRegra() {
		return tipoRegra;
	}

	public void setTipoRegra(String tipoRegra) {
		this.tipoRegra = tipoRegra;
	}

	public String getEnviarEmail() {
		return enviarEmail;
	}

	public void setEnviarEmail(String enviarEmail) {
		this.enviarEmail = enviarEmail;
	}

	public String getCriarProblema() {
		return criarProblema;
	}

	public void setCriarProblema(String criarProblema) {
		this.criarProblema = criarProblema;
	}

	public String getCriarIncidente() {
		return criarIncidente;
	}

	public void setCriarIncidente(String criarIncidente) {
		this.criarIncidente = criarIncidente;
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

	public Integer getIdCaracteristica() {
		return idCaracteristica;
	}

	public void setIdCaracteristica(Integer idCaracteristica) {
		this.idCaracteristica = idCaracteristica;
	}

	public String getScript() {
		return script;
	}

	public void setScript(String script) {
		this.script = script;
	}

	public Integer[] getUsuariosNotificacao() {
		return usuariosNotificacao;
	}

	public void setUsuariosNotificacao(Integer[] usuariosNotificacao) {
		this.usuariosNotificacao = usuariosNotificacao;
	}

	public Integer[] getGruposNotificacao() {
		return gruposNotificacao;
	}

	public void setGruposNotificacao(Integer[] gruposNotificacao) {
		this.gruposNotificacao = gruposNotificacao;
	}

	public boolean getScriptSuccess() {
		return scriptSuccess;
	}

	public void setScriptSuccess(boolean scriptSuccess) {
		this.scriptSuccess = scriptSuccess;
	}

}
