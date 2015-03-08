package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class GerenciamentoServicosDTO implements IDto {

	private static final long serialVersionUID = 1L;

	private Integer idFluxo;
	private Integer idTarefa;
	private Integer idSolicitacao;
	private String acaoFluxo;
	private String erroGrid;
	private String numeroContratoSel;
	private String idSolicitacaoSel;
	private String idTipoDemandaServico;
	private String responsavelAtual;
	private String nomeCampoOrdenacao;
	private String ordenacaoAsc;
	private String descricaoSolicitacao;
	private String grupoAtual;
	private String solicitanteUnidade;
	private Integer quantidadeAtrasadas;
	private Integer quantidadeTotal;

	private Integer idContrato;
	private Integer idSolicitante;
	private Integer idResponsavelAtual;
	private Integer idGrupoAtual;
	private Integer idTipo;
	private Integer itensPorPagina;
	private Integer paginaSelecionada;
	private Integer totalPaginas;
	private Integer tipoLista;
	private String palavraChave;
	private String situacao;
	private String tarefaAtual;

	private String descricaoSolicitacaoVisualizar;
	private Integer idSolicitacaoServicoDescricao;

	private String TipoVisualizacao;
	private String situacaoSla;
	private String ordenarPor;
	private String direcaoOrdenacao;

	public String getTipoVisualizacao() {
		return TipoVisualizacao;
	}

	public void setTipoVisualizacao(String tipoVisualizacao) {
		TipoVisualizacao = tipoVisualizacao;
	}

	public String getSituacaoSla() {
		return situacaoSla;
	}

	public void setSituacaoSla(String situacaoSla) {
		this.situacaoSla = situacaoSla;
	}

	public String getOrdenarPor() {
		return ordenarPor;
	}

	public void setOrdenarPor(String ordenarPor) {
		this.ordenarPor = ordenarPor;
	}

	public Integer getIdFluxo() {
		return idFluxo;
	}

	public void setIdFluxo(Integer idFluxo) {
		this.idFluxo = idFluxo;
	}

	public Integer getIdTarefa() {
		return idTarefa;
	}

	public void setIdTarefa(Integer idTarefa) {
		this.idTarefa = idTarefa;
	}

	public String getAcaoFluxo() {
		return acaoFluxo;
	}

	public void setAcaoFluxo(String acaoFluxo) {
		this.acaoFluxo = acaoFluxo;
	}

	public String getNumeroContratoSel() {
		return numeroContratoSel;
	}

	public void setNumeroContratoSel(String numeroContratoSel) {
		this.numeroContratoSel = numeroContratoSel;
	}

	public String getIdSolicitacaoSel() {
		return idSolicitacaoSel;
	}

	public void setIdSolicitacaoSel(String idSolicitacaoSel) {
		this.idSolicitacaoSel = idSolicitacaoSel;
	}

	public Integer getIdSolicitacao() {
		return idSolicitacao;
	}

	public void setIdSolicitacao(Integer idSolicitacao) {
		this.idSolicitacao = idSolicitacao;
	}

	public String getResponsavelAtual() {
		return responsavelAtual;
	}

	public void setResponsavelAtual(String responsavelAtual) {
		this.responsavelAtual = responsavelAtual;
	}

	public String getNomeCampoOrdenacao() {
		return nomeCampoOrdenacao;
	}

	public void setNomeCampoOrdenacao(String nomeCampoOrdenacao) {
		this.nomeCampoOrdenacao = nomeCampoOrdenacao;
	}

	public String getOrdenacaoAsc() {
		return ordenacaoAsc;
	}

	public void setOrdenacaoAsc(String ordenacaoAsc) {
		this.ordenacaoAsc = ordenacaoAsc;
	}

	public String getDescricaoSolicitacao() {
		return descricaoSolicitacao;
	}

	public void setDescricaoSolicitacao(String descricaoSolicitacao) {
		this.descricaoSolicitacao = descricaoSolicitacao;
	}

	public String getErroGrid() {
		return erroGrid;
	}

	public void setErroGrid(String erroGrid) {
		this.erroGrid = erroGrid;
	}

	public String getIdTipoDemandaServico() {
		return idTipoDemandaServico;
	}

	public void setIdTipoDemandaServico(String idTipoDemandaServico) {
		this.idTipoDemandaServico = idTipoDemandaServico;
	}

	public String getGrupoAtual() {
		return grupoAtual;
	}

	public void setGrupoAtual(String grupoAtual) {
		this.grupoAtual = grupoAtual;
	}

	public String getSolicitanteUnidade() {
		return solicitanteUnidade;
	}

	public void setSolicitanteUnidade(String solicitanteUnidade) {
		this.solicitanteUnidade = solicitanteUnidade;
	}

	public Integer getQuantidadeAtrasadas() {
		return quantidadeAtrasadas;
	}

	public void setQuantidadeAtrasadas(Integer quantidadeAtrasadas) {
		this.quantidadeAtrasadas = quantidadeAtrasadas;
	}

	public Integer getQuantidadeTotal() {
		return quantidadeTotal;
	}

	public void setQuantidadeTotal(Integer quantidadeTotal) {
		this.quantidadeTotal = quantidadeTotal;
	}

	public Integer getPaginaSelecionada() {
		return paginaSelecionada;
	}

	public void setPaginaSelecionada(Integer paginaSelecionada) {
		this.paginaSelecionada = paginaSelecionada;
	}

	public Integer getItensPorPagina() {
		return itensPorPagina;
	}

	public void setItensPorPagina(Integer itensPorPagina) {
		this.itensPorPagina = itensPorPagina;
	}

	public Integer getTipoLista() {
		return tipoLista;
	}

	public void setTipoLista(Integer tipoLista) {
		this.tipoLista = tipoLista;
	}

	public Integer getIdResponsavelAtual() {
		return idResponsavelAtual;
	}

	public void setIdResponsavelAtual(Integer idResponsavelAtual) {
		this.idResponsavelAtual = idResponsavelAtual;
	}

	public Integer getIdGrupoAtual() {
		return idGrupoAtual;
	}

	public void setIdGrupoAtual(Integer idGrupoAtual) {
		this.idGrupoAtual = idGrupoAtual;
	}

	public String getPalavraChave() {
		return palavraChave;
	}

	public void setPalavraChave(String palavraChave) {
		this.palavraChave = palavraChave;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public Integer getIdSolicitante() {
		return idSolicitante;
	}

	public void setIdSolicitante(Integer idSolicitante) {
		this.idSolicitante = idSolicitante;
	}

	public Integer getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Integer idContrato) {
		this.idContrato = idContrato;
	}

	public Integer getIdTipo() {
		return idTipo;
	}

	public void setIdTipo(Integer idTipo) {
		this.idTipo = idTipo;
	}

	public Integer getTotalPaginas() {
		return totalPaginas;
	}

	public void setTotalPaginas(Integer totalPaginas) {
		this.totalPaginas = totalPaginas;
	}

	public String getTarefaAtual() {
		return tarefaAtual;
	}

	public void setTarefaAtual(String tarefaAtual) {
		this.tarefaAtual = tarefaAtual;
	}

	public String getDescricaoSolicitacaoVisualizar() {
		return descricaoSolicitacaoVisualizar;
	}

	public void setDescricaoSolicitacaoVisualizar(String descricaoSolicitacaoVisualizar) {
		this.descricaoSolicitacaoVisualizar = descricaoSolicitacaoVisualizar;
	}

	public Integer getIdSolicitacaoServicoDescricao() {
		return idSolicitacaoServicoDescricao;
	}

	public void setIdSolicitacaoServicoDescricao(Integer idSolicitacaoServicoDescricao) {
		this.idSolicitacaoServicoDescricao = idSolicitacaoServicoDescricao;
	}

	public String getDirecaoOrdenacao() {
		return direcaoOrdenacao;
	}

	public void setDirecaoOrdenacao(String direcaoOrdenacao) {
		this.direcaoOrdenacao = direcaoOrdenacao;
	}
	
	

}
