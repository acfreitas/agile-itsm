package br.com.centralit.citcorpore.bean;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Collection;

import br.com.citframework.dto.IDto;

public class PrestacaoContasViagemDTO implements IDto {
	public static final String APROVADA = "Aprovada";
	public static final String NAO_APROVADA = "Não Aprovada";
	public static final String AGUARDANDO_CONFERENCIA = "Aguardando Conferência";
	public static final String EM_CONFERENCIA = "Em Conferência";
	public static final String EM_CORRECAO = "Em Correção";
	public static final String AGUARDANDO_CORRECAO = "Aguardando Correção";
	
	
	private Integer idPrestacaoContasViagem;
	private Integer idResponsavel;
	private Integer idAprovacao;
	private Integer idSolicitacaoServico;
	private Integer idEmpregado;
	private Timestamp dataHora;
	private Date data;
	private String situacao;
	
	private String listItens;
	private String itensPrestacaoContasViagemSerialize;
	
	private String complemJustificativaAutorizacao;
	private Integer idJustificativaAutorizacao;
	private String aprovado;
	
	private Integer idItemTrabalho;
	private Integer idTarefa;
	private Integer idContrato;
	
	private String corrigir;
	private String integranteSerialize;
	private String correcao;
	private String remarcacao;
	private Integer idRespPrestacaoContas;
	
	private IntegranteViagemDTO integranteViagemDto;
	
	//Define se o Integrante da Viagem é um funcionario(S) ou não(N)
	private String integranteFuncionario;
	
	private Integer idIntegrante;
	
	private String nomeNaoFuncionario;
	
	Collection<ItemPrestacaoContasViagemDTO> listaItemPrestacaoContasViagemDTO;
	
	// Controle prestacao de contas
	private Double valorDiferenca;
	private String valorDiferencaAux;
	private Double totalLancamentos;
	private String totalLancamentosAux;
	private Double totalPrestacaoContas;
	private String totalPrestacaoContasAux;
	private Double valor;
	private String valorAux;
	
	public Collection<ItemPrestacaoContasViagemDTO> getListaItemPrestacaoContasViagemDTO() {
		return listaItemPrestacaoContasViagemDTO;
	}
	public void setListaItemPrestacaoContasViagemDTO(
			Collection<ItemPrestacaoContasViagemDTO> listaItemPrestacaoContasViagemDTO) {
		this.listaItemPrestacaoContasViagemDTO = listaItemPrestacaoContasViagemDTO;
	}
	private static final long serialVersionUID = 1L;

	public Integer getIdResponsavel() {
		return idResponsavel;
	}
	public void setIdResponsavel(Integer idResponsavel) {
		this.idResponsavel = idResponsavel;
	}
	public Integer getIdAprovacao() {
		return idAprovacao;
	}
	public void setIdAprovacao(Integer idAprovacao) {
		this.idAprovacao = idAprovacao;
	}
	public Integer getIdEmpregado() {
		return idEmpregado;
	}
	public void setIdEmpregado(Integer idEmpregado) {
		this.idEmpregado = idEmpregado;
	}
	public Timestamp getDataHora() {
		return dataHora;
	}
	public void setDataHora(Timestamp dataHora) {
		this.dataHora = dataHora;
	}
	public String getSituacao() {
		return situacao;
	}
	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
	public String getListItens() {
		return listItens;
	}
	public void setListItens(String listItens) {
		this.listItens = listItens;
	}
	public Integer getIdPrestacaoContasViagem() {
		return idPrestacaoContasViagem;
	}
	public void setIdPrestacaoContasViagem(Integer idPrestacaoContasViagem) {
		this.idPrestacaoContasViagem = idPrestacaoContasViagem;
	}
	public Integer getIdSolicitacaoServico() {
		return idSolicitacaoServico;
	}
	public void setIdSolicitacaoServico(Integer idSolicitacaoServico) {
		this.idSolicitacaoServico = idSolicitacaoServico;
	}
	public String getItensPrestacaoContasViagemSerialize() {
		return itensPrestacaoContasViagemSerialize;
	}
	public void setItensPrestacaoContasViagemSerialize(
			String itensPrestacaoContasViagemSerialize) {
		this.itensPrestacaoContasViagemSerialize = itensPrestacaoContasViagemSerialize;
	}
	public String getComplemJustificativaAutorizacao() {
		return complemJustificativaAutorizacao;
	}
	public void setComplemJustificativaAutorizacao(
			String complemJustificativaAutorizacao) {
		this.complemJustificativaAutorizacao = complemJustificativaAutorizacao;
	}
	public Integer getIdJustificativaAutorizacao() {
		return idJustificativaAutorizacao;
	}
	public void setIdJustificativaAutorizacao(Integer idJustificativaAutorizacao) {
		this.idJustificativaAutorizacao = idJustificativaAutorizacao;
	}
	public String getAprovado() {
		return aprovado;
	}
	public void setAprovado(String aprovado) {
		this.aprovado = aprovado;
	}
	public Integer getIdItemTrabalho() {
		return idItemTrabalho;
	}
	public void setIdItemTrabalho(Integer idItemTrabalho) {
		this.idItemTrabalho = idItemTrabalho;
	}
	public Integer getIdTarefa() {
		return idTarefa;
	}
	public void setIdTarefa(Integer idTarefa) {
		this.idTarefa = idTarefa;
	}
	public String getCorrigir() {
		return corrigir;
	}
	public void setCorrigir(String corrigir) {
		this.corrigir = corrigir;
	}
	public Integer getIdRespPrestacaoContas() {
		return idRespPrestacaoContas;
	}
	public void setIdRespPrestacaoContas(Integer idRespPrestacaoContas) {
		this.idRespPrestacaoContas = idRespPrestacaoContas;
	}
	public IntegranteViagemDTO getIntegranteViagemDto() {
		return integranteViagemDto;
	}
	public void setIntegranteViagemDto(IntegranteViagemDTO integranteViagemDto) {
		this.integranteViagemDto = integranteViagemDto;
	}
	public String getIntegranteSerialize() {
		return integranteSerialize;
	}
	public void setIntegranteSerialize(String integranteSerialize) {
		this.integranteSerialize = integranteSerialize;
	}
	public String getCorrecao() {
		return correcao;
	}
	public void setCorrecao(String correcao) {
		this.correcao = correcao;
	}
	public String getIntegranteFuncionario() {
		return integranteFuncionario;
	}
	public void setIntegranteFuncionario(String integranteFuncionario) {
		this.integranteFuncionario = integranteFuncionario;
	}
	public String getNomeNaoFuncionario() {
		return nomeNaoFuncionario;
	}
	public void setNomeNaoFuncionario(String nomeNaoFuncionario) {
		this.nomeNaoFuncionario = nomeNaoFuncionario;
	}
	public static String getAprovada() {
		return APROVADA;
	}
	public static String getNaoAprovada() {
		return NAO_APROVADA;
	}
	public static String getAguardandoConferencia() {
		return AGUARDANDO_CONFERENCIA;
	}
	public static String getEmConferencia() {
		return EM_CONFERENCIA;
	}
	public static String getEmCorrecao() {
		return EM_CORRECAO;
	}
	public static String getAguardandoCorrecao() {
		return AGUARDANDO_CORRECAO;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getRemarcacao() {
		return remarcacao;
	}
	public void setRemarcacao(String remarcacao) {
		this.remarcacao = remarcacao;
	}
	public Integer getIdContrato() {
		return idContrato;
	}
	public void setIdContrato(Integer idContrato) {
		this.idContrato = idContrato;
	}
	
	public Double getValorDiferenca() {
		return valorDiferenca;
	}
	public void setValorDiferenca(Double valorDiferenca) {
		this.valorDiferenca = valorDiferenca;
		this.valorDiferencaAux = valorDiferenca.toString();
	}
	public String getValorDiferencaAux() {
		return valorDiferencaAux;
	}
	public void setValorDiferencaAux(String valorDiferencaAux) {
		this.valorDiferencaAux = valorDiferencaAux;
	}
	public Double getTotalLancamentos() {
		return totalLancamentos;
	}
	public void setTotalLancamentos(Double totalLancamentos) {
		this.totalLancamentos = totalLancamentos;
		this.totalLancamentosAux = totalLancamentos.toString();
	}
	public String getTotalLancamentosAux() {
		return totalLancamentosAux;
	}
	public void setTotalLancamentosAux(String totalLancamentosAux) {
		this.totalLancamentosAux = totalLancamentosAux;
	}
	public Double getTotalPrestacaoContas() {
		return totalPrestacaoContas;
	}
	public void setTotalPrestacaoContas(Double totalPrestacaoContas) {
		this.totalPrestacaoContas = totalPrestacaoContas;
		this.totalPrestacaoContasAux = totalPrestacaoContas.toString();
	}
	public String getTotalPrestacaoContasAux() {
		return totalPrestacaoContasAux;
	}
	public void setTotalPrestacaoContasAux(String totalPrestacaoContasAux) {
		this.totalPrestacaoContasAux = totalPrestacaoContasAux;
	}
	public Double getValor() {
		return valor;
	}
	public void setValor(Double valor) {
		this.valor = valor;
		if(valor != null) {
			this.valorAux = valor.toString();
		}
	}
	public String getValorAux() {
		return valorAux;
	}
	public void setValorAux(String valorAux) {
		this.valorAux = valorAux;
	}
	public Integer getIdIntegrante() {
		return idIntegrante;
	}
	public void setIdIntegrante(Integer idIntegrante) {
		this.idIntegrante = idIntegrante;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
}