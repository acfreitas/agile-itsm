package br.com.centralit.citcorpore.bean;

//Criado por Bruno.Aquino

import java.sql.Date;
import java.util.Collection;

import br.com.citframework.dto.IDto;

public class RelatorioKpiProdutividadeDTO implements IDto {

	private static final long serialVersionUID = 1L;
	private Date dataInicio;
	private Date dataFim;
	private String formatoArquivoRelatorio;
	private Integer idContrato;
	private String contrato;
	private String funcionario;
	private String grupo;
	private String listaUsuarios;
	private String listaGrupoUnidade;
	private String checkMostrarRequisicoes;
	private String checkMostrarIncidentes;
	private Collection<SolicitacaoServicoDTO> listaSolicitacoesUsuario;
	private Collection<RelatorioKpiProdutividadeDTO> listaGeral;
	private int qtdeExecutada;
	private int qtdEstourada;
	private int numeroSolicitacao;
	private String NomeServico;
	private double totalGrupoEstouradas;
	private double totalGrupoExecutadas;
	private int totalPorExecutante;
	private int totalPorExecutanteEstouradas;
	private String totalPorExecutanteEstouradasPorcentagem;
	private String totalPorExecutantePorcentagem;
	private String totalPorServicoPorcentagem;
	private String selecionarGrupoUnidade;
	private String situacao;
	
	//relatorio QI3 QI4
	
	private String listaServicosString;
	private String listaCausaString;
	private Collection<EmpregadoDTO> listaEmpregado;
	private Collection<ServicoDTO> listaServicos;
	private Collection<CausaIncidenteDTO> listaCausaIncidentes;
	private Integer qtdeencaminhadas;
	private Integer qtdeexito;
	private Integer idEmpregado;
	private Double porcentagemExecutadaExito;

	public Double getPorcentagemExecutadaExito() {
		return porcentagemExecutadaExito;
	}

	public void setPorcentagemExecutadaExito(Double porcentagemExecutadaExito) {
		this.porcentagemExecutadaExito = porcentagemExecutadaExito;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public int getQtdeExecutada() {
		return qtdeExecutada;
	}

	public void setQtdeExecutada(int qtdeExecutada) {
		this.qtdeExecutada = qtdeExecutada;
	}

	public int getQtdEstourada() {
		return qtdEstourada;
	}

	public void setQtdEstourada(int qtdEstourada) {
		this.qtdEstourada = qtdEstourada;
	}

	public String getListaGrupoUnidade() {
		return listaGrupoUnidade;
	}

	public void setListaGrupoUnidade(String listaGrupoUnidade) {
		this.listaGrupoUnidade = listaGrupoUnidade;
	}

	public String getSelecionarGrupoUnidade() {
		return selecionarGrupoUnidade;
	}

	public void setSelecionarGrupoUnidade(String selecionarGrupoUnidade) {
		this.selecionarGrupoUnidade = selecionarGrupoUnidade;
	}

	public int getTotalPorExecutante() {
		return totalPorExecutante;
	}

	public void setTotalPorExecutante(int totalPorExecutante) {
		this.totalPorExecutante = totalPorExecutante;
	}

	public int getTotalPorExecutanteEstouradas() {
		return totalPorExecutanteEstouradas;
	}

	public void setTotalPorExecutanteEstouradas(int totalPorExecutanteEstouradas) {
		this.totalPorExecutanteEstouradas = totalPorExecutanteEstouradas;
	}

	public String getTotalPorServicoPorcentagem() {
		return totalPorServicoPorcentagem;
	}

	public void setTotalPorServicoPorcentagem(String totalPorServicoPorcentagem) {
		this.totalPorServicoPorcentagem = totalPorServicoPorcentagem;
	}

	public String getTotalPorExecutantePorcentagem() {
		return totalPorExecutantePorcentagem;
	}

	public void setTotalPorExecutantePorcentagem(String totalPorExecutantePorcentagem) {
		this.totalPorExecutantePorcentagem = totalPorExecutantePorcentagem;
	}

	public String getTotalPorExecutanteEstouradasPorcentagem() {
		return totalPorExecutanteEstouradasPorcentagem;
	}

	public void setTotalPorExecutanteEstouradasPorcentagem(String totalPorExecutanteEstouradasPorcentagem) {
		this.totalPorExecutanteEstouradasPorcentagem = totalPorExecutanteEstouradasPorcentagem;
	}

	public double getTotalGrupoEstouradas() {
		return totalGrupoEstouradas;
	}

	public void setTotalGrupoEstouradas(double totalGrupoEstouradas) {
		this.totalGrupoEstouradas = totalGrupoEstouradas;
	}

	public double getTotalGrupoExecutadas() {
		return totalGrupoExecutadas;
	}

	public void setTotalGrupoExecutadas(double totalGrupoExecutadas) {
		this.totalGrupoExecutadas = totalGrupoExecutadas;
	}

	public String getNomeServico() {
		return NomeServico;
	}

	public void setNomeServico(String nomeServico) {
		NomeServico = nomeServico;
	}

	public int getNumeroSolicitacao() {
		return numeroSolicitacao;
	}

	public void setNumeroSolicitacao(int numeroSolicitacao) {
		this.numeroSolicitacao = numeroSolicitacao;
	}

	public Collection<SolicitacaoServicoDTO> getListaSolicitacoesUsuario() {
		return listaSolicitacoesUsuario;
	}

	public void setListaSolicitacoesUsuario(Collection<SolicitacaoServicoDTO> listaSolicitacoesUsuario) {
		this.listaSolicitacoesUsuario = listaSolicitacoesUsuario;
	}

	public Collection<RelatorioKpiProdutividadeDTO> getListaGeral() {
		return listaGeral;
	}

	public void setListaGeral(Collection<RelatorioKpiProdutividadeDTO> listaGeral) {
		this.listaGeral = listaGeral;
	}

	public String getCheckMostrarRequisicoes() {
		return checkMostrarRequisicoes;
	}

	public void setCheckMostrarRequisicoes(String checkMostrarRequisicoes) {
		this.checkMostrarRequisicoes = checkMostrarRequisicoes;
	}

	public String getCheckMostrarIncidentes() {
		return checkMostrarIncidentes;
	}

	public void setCheckMostrarIncidentes(String checkMostrarIncidentes) {
		this.checkMostrarIncidentes = checkMostrarIncidentes;
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

	public String getFormatoArquivoRelatorio() {
		return formatoArquivoRelatorio;
	}

	public void setFormatoArquivoRelatorio(String formatoArquivoRelatorio) {
		this.formatoArquivoRelatorio = formatoArquivoRelatorio;
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

	public String getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(String funcionario) {
		this.funcionario = funcionario;
	}

	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	public String getListaUsuarios() {
		return listaUsuarios;
	}

	public void setListaUsuarios(String listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getListaServicosString() {
		return listaServicosString;
	}

	public void setListaServicosString(String listaServicosString) {
		this.listaServicosString = listaServicosString;
	}


	public Collection<ServicoDTO> getListaServicos() {
		return listaServicos;
	}

	public void setListaServicos(Collection<ServicoDTO> listaServicos) {
		this.listaServicos = listaServicos;
	}

	public Collection<CausaIncidenteDTO> getListaCausaIncidentes() {
		return listaCausaIncidentes;
	}

	public void setListaCausaIncidentes(Collection<CausaIncidenteDTO> listaCausaIncidentes) {
		this.listaCausaIncidentes = listaCausaIncidentes;
	}

	public String getListaCausaString() {
		return listaCausaString;
	}

	public void setListaCausaString(String listaCausaString) {
		this.listaCausaString = listaCausaString;
	}

	public Integer getQtdeencaminhadas() {
		return qtdeencaminhadas;
	}

	public void setQtdeencaminhadas(Integer qtdeencaminhadas) {
		this.qtdeencaminhadas = qtdeencaminhadas;
	}

	public Integer getQtdeexito() {
		return qtdeexito;
	}

	public void setQtdeexito(Integer qtdeexito) {
		this.qtdeexito = qtdeexito;
	}

	public Integer getIdEmpregado() {
		return idEmpregado;
	}

	public void setIdEmpregado(Integer idEmpregado) {
		this.idEmpregado = idEmpregado;
	}

	public Collection<EmpregadoDTO> getListaEmpregado() {
		return listaEmpregado;
	}

	public void setListaEmpregado(Collection<EmpregadoDTO> listaEmpregado) {
		this.listaEmpregado = listaEmpregado;
	}

	

	

	

	
}
