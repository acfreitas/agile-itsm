package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class TesteOperacaoRestDTO implements IDto {
	private String loginUsuario;
	private String senha;
	
	private String url;
	private String idSessao;
	private String numero;
	private Integer idServico;
	private String nomeServico;
	private String descricao;
	private String descricaoPortal;	
	private String tipo;
	private String loginSolicitante;
	private String impacto;
	private String urgencia;
	private String resultado;
	private String formatoSaida;
	
	private String listaIncidentes;
	private String listaRequisicoes;
	private String listaCompras;
	private String listaViagens;
	private String listaRH;
	
	private Integer tipoListagem;
	private Integer idTarefa;
	private Integer somenteEmAprovacao;
	
	private Integer feedback;
	private Integer idJustificativa;

    public String getLoginUsuario() {
		return loginUsuario;
	}
	public void setLoginUsuario(String loginUsuario) {
		this.loginUsuario = loginUsuario;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getIdSessao() {
		return idSessao;
	}
	public void setIdSessao(String idSessao) {
		this.idSessao = idSessao;
	}
	public Integer getIdServico() {
		return idServico;
	}
	public void setIdServico(Integer idServico) {
		this.idServico = idServico;
	}
	public String getNomeServico() {
		return nomeServico;
	}
	public void setNomeServico(String nomeServico) {
		this.nomeServico = nomeServico;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getLoginSolicitante() {
		return loginSolicitante;
	}
	public void setLoginSolicitante(String loginSolicitante) {
		this.loginSolicitante = loginSolicitante;
	}
	public String getImpacto() {
		return impacto;
	}
	public void setImpacto(String impacto) {
		this.impacto = impacto;
	}
	public String getUrgencia() {
		return urgencia;
	}
	public void setUrgencia(String urgencia) {
		this.urgencia = urgencia;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getResultado() {
		return resultado;
	}
	public void setResultado(String resultado) {
		this.resultado = resultado;
	}
	public String getFormatoSaida() {
		return formatoSaida;
	}
	public void setFormatoSaida(String formatoSaida) {
		this.formatoSaida = formatoSaida;
	}
	public String getListaIncidentes() {
		return listaIncidentes;
	}
	public void setListaIncidentes(String listaIncidentes) {
		this.listaIncidentes = listaIncidentes;
	}
	public String getListaRequisicoes() {
		return listaRequisicoes;
	}
	public void setListaRequisicoes(String listaRequisicoes) {
		this.listaRequisicoes = listaRequisicoes;
	}
	public String getListaCompras() {
		return listaCompras;
	}
	public void setListaCompras(String listaCompras) {
		this.listaCompras = listaCompras;
	}
	public String getListaViagens() {
		return listaViagens;
	}
	public void setListaViagens(String listaViagens) {
		this.listaViagens = listaViagens;
	}
	public String getListaRH() {
		return listaRH;
	}
	public void setListaRH(String listaRH) {
		this.listaRH = listaRH;
	}
	public String getDescricaoPortal() {
		return descricaoPortal;
	}
	public void setDescricaoPortal(String descricaoPortal) {
		this.descricaoPortal = descricaoPortal;
	}
	public Integer getTipoListagem() {
		return tipoListagem;
	}
	public void setTipoListagem(Integer tipoListagem) {
		this.tipoListagem = tipoListagem;
	}
	public Integer getIdTarefa() {
		return idTarefa;
	}
	public void setIdTarefa(Integer idTarefa) {
		this.idTarefa = idTarefa;
	}
	public Integer getFeedback() {
		return feedback;
	}
	public void setFeedback(Integer feedback) {
		this.feedback = feedback;
	}
	public Integer getIdJustificativa() {
		return idJustificativa;
	}
	public void setIdJustificativa(Integer idJustificativa) {
		this.idJustificativa = idJustificativa;
	}
	public Integer getSomenteEmAprovacao() {
		return somenteEmAprovacao;
	}
	public void setSomenteEmAprovacao(Integer somenteEmAprovacao) {
		this.somenteEmAprovacao = somenteEmAprovacao;
	}

}
