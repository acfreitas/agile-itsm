package br.com.centralit.citcorpore.bean;

import java.sql.Date;

import br.com.citframework.dto.IDto;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilFormatacao;
import br.com.citframework.util.UtilHTML;

public class ExecucaoDemandaDTO implements IDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5575586940240747276L;
	private Integer idExecucao;
	private Integer idDemanda;
	private Integer idAtividade;
	private Integer idEmpregadoExecutor;
	private Integer idEmpregadoReceptor;
	private String relato;
	private Double qtdeHoras;
	private String situacao;
	private String grupoExecutor;
	private String detalhamento;
	
	private String nomeFluxo;
	private String nomeEtapa;
	private String nomeAtividade;
	private Date expectativaFim;
	private String prioridade;
	private String descricaoTipoAtividade;
	private String nome;
	private Integer idFluxo;
	private Date terminoPrevisto;
	private Integer idEmpregadoLogado;
	
	private Integer idExecucaoAtribuir;
	private Integer idProjeto;
	private Integer idFluxoSelecionado;
	private String nomeProjeto;
	private Integer idTipoDemanda;
	
	private Date terminoReal;
	
	private String numeroOS;
	private Integer anoOS;
	
	private String objSerializado;
	
	public String getImagem(){
		String retorno = "";
		if (this.idTipoDemanda==null) return "";
		
		retorno =  "<img src=demanda.gif title='Outros' border='0' />";
		
		if (this.idTipoDemanda.intValue()==1) retorno =  "<img src=esquadro.jpg title='Demanda de Projeto' border='0' />";
		if (this.idTipoDemanda.intValue()==2) retorno =  "<img src=ferramenta.gif title='Manutenção Evolutiva' border='0' />";
		if (this.idTipoDemanda.intValue()==3) retorno =  "<img src=joaninha.gif title='Erro' border='0' />";
		if (this.idTipoDemanda.intValue()==4) retorno =  "<img src=comercial.gif title='Atendimento a área comercial' border='0' />";
		if (this.idTipoDemanda.intValue()==5) retorno =  "<img src=apresentacao.jpg title='Apresentação de Solução' border='0' />";
		if (this.idTipoDemanda.intValue()==6) retorno =  "<img src=reuniao.gif title='Reunião com cliente' border='0' />";
		if (this.idTipoDemanda.intValue()==7) retorno =  "<img src=cliente.gif title='Apoio ao Cliente' border='0' />";
		if (this.idTipoDemanda.intValue()==8) retorno =  "<img src=design.gif title='Design' border='0' />";
		if (this.idTipoDemanda.intValue()==9) retorno =  "<img src=ISO.gif title='Demanda ISO 9001' border='0' />";
		if (this.idTipoDemanda.intValue()==10) retorno =  "<img src=demanda.gif title='Demanda Avulsa' border='0' />";
		if (this.idTipoDemanda.intValue()==11) retorno =  "<img src=reuniaointerna.gif title='Reunião Interna' border='0' />";
		
		return retorno;
	}
	public String getGrupoExecutor() {
		return grupoExecutor;
	}
	public void setGrupoExecutor(String grupoExecutor) {
		this.grupoExecutor = grupoExecutor;
	}
	public Integer getIdAtividade() {
		return idAtividade;
	}
	public void setIdAtividade(Integer idAtividade) {
		this.idAtividade = idAtividade;
	}
	public Integer getIdDemanda() {
		return idDemanda;
	}
	public void setIdDemanda(Integer idDemanda) {
		this.idDemanda = idDemanda;
	}
	public Integer getIdEmpregadoExecutor() {
		return idEmpregadoExecutor;
	}
	public void setIdEmpregadoExecutor(Integer idEmpregadoExecutor) {
		this.idEmpregadoExecutor = idEmpregadoExecutor;
	}
	public Integer getIdEmpregadoReceptor() {
		return idEmpregadoReceptor;
	}
	public void setIdEmpregadoReceptor(Integer idEmpregadoReceptor) {
		this.idEmpregadoReceptor = idEmpregadoReceptor;
	}
	public Integer getIdExecucao() {
		return idExecucao;
	}
	public void setIdExecucao(Integer idExecucao) {
		this.idExecucao = idExecucao;
	}
	public String getRelato() {
		return relato;
	}
	public void setRelato(String relato) {
		this.relato = relato;
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
	public String getSituacaoDescHTML() {
		if (this.situacao == null) return "";
		if (this.situacao.equalsIgnoreCase("N")) return UtilHTML.encodeHTML("Não Iniciada");
		if (this.situacao.equalsIgnoreCase("I")) return UtilHTML.encodeHTML("Em Execução");
		if (this.situacao.equalsIgnoreCase("F")) return "Finalizada";
		if (this.situacao.equalsIgnoreCase("C")) return "Paralisada - Aguard. Cliente";
		if (this.situacao.equalsIgnoreCase("P")) return "Paralisada - Interno";
		if (this.situacao.equalsIgnoreCase("T")) return "Transferida";
		return UtilHTML.encodeHTML(situacao);
	}	
	public String getSituacao() {
		return situacao;
	}
	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
	public String getDescricaoTipoAtividade() {
		return descricaoTipoAtividade;
	}
	public void setDescricaoTipoAtividade(String descricaoTipoAtividade) {
		this.descricaoTipoAtividade = descricaoTipoAtividade;
	}
	public String getExpectativaFimStr() {
		if (this.terminoPrevisto!=null) return UtilDatas.dateToSTR(terminoPrevisto);
		return UtilDatas.dateToSTR(expectativaFim);
	}	
	public Date getExpectativaFim() {
		return expectativaFim;
	}
	public void setExpectativaFim(Date expectativaFim) {
		this.expectativaFim = expectativaFim;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getNomeAtividade() {
		return nomeAtividade;
	}
	public String getNomeAtividadeHTML() {
		return UtilHTML.encodeHTML(nomeAtividade);
	}	
	public void setNomeAtividade(String nomeAtividade) {
		this.nomeAtividade = nomeAtividade;
	}
	public String getNomeEtapa() {
		return nomeEtapa;
	}
	public String getNomeEtapaHTML() {
		return UtilHTML.encodeHTML(nomeEtapa);
	}	
	public void setNomeEtapa(String nomeEtapa) {
		this.nomeEtapa = nomeEtapa;
	}
	public String getNomeFluxo() {
		return nomeFluxo;
	}
	public void setNomeFluxo(String nomeFluxo) {
		this.nomeFluxo = nomeFluxo;
	}
	public String getPrioridadeDesc() {
		if (this.prioridade == null) return "";
		if (this.prioridade.equalsIgnoreCase("E")) return "Emergencial";
		if (this.prioridade.equalsIgnoreCase("A")) return "Alta";
		if (this.prioridade.equalsIgnoreCase("M")) return "Média";
		if (this.prioridade.equalsIgnoreCase("B")) return "Baixa";
		if (this.prioridade.equalsIgnoreCase("X")) return "Não Definida";
		return prioridade;
	}
	public String getPrioridadeDescHTML() {
		if (this.prioridade == null) return "";
		if (this.prioridade.equalsIgnoreCase("E")) return "Emergencial";
		if (this.prioridade.equalsIgnoreCase("A")) return "Alta";
		if (this.prioridade.equalsIgnoreCase("M")) return UtilHTML.encodeHTML("Média");
		if (this.prioridade.equalsIgnoreCase("B")) return "Baixa";
		if (this.prioridade.equalsIgnoreCase("X")) return UtilHTML.encodeHTML("Não Definida");
		return UtilHTML.encodeHTML(prioridade);
	}	
	public String getPrioridade() {
		return prioridade;
	}
	public void setPrioridade(String prioridade) {
		this.prioridade = prioridade;
	}
	public String getDetalhamento() {
		return detalhamento;
	}
	public void setDetalhamento(String detalhamento) {
		this.detalhamento = detalhamento;
	}
	public Integer getIdExecucaoAtribuir() {
		return idExecucaoAtribuir;
	}
	public void setIdExecucaoAtribuir(Integer idExecucaoAtribuir) {
		this.idExecucaoAtribuir = idExecucaoAtribuir;
	}
	public Integer getIdFluxo() {
		return idFluxo;
	}
	public void setIdFluxo(Integer idFluxo) {
		this.idFluxo = idFluxo;
	}
	public String getTerminoPrevistoStr() {
		return UtilDatas.dateToSTR(this.terminoPrevisto);
	}	
	public Date getTerminoPrevisto() {
		return terminoPrevisto;
	}
	public void setTerminoPrevisto(Date terminoPrevisto) {
		this.terminoPrevisto = terminoPrevisto;
	}
	public Integer getIdEmpregadoLogado() {
		return idEmpregadoLogado;
	}
	public void setIdEmpregadoLogado(Integer idEmpregadoLogado) {
		this.idEmpregadoLogado = idEmpregadoLogado;
	}
	public Integer getIdProjeto() {
		return idProjeto;
	}
	public void setIdProjeto(Integer idProjeto) {
		this.idProjeto = idProjeto;
	}
	public Integer getIdFluxoSelecionado() {
		return idFluxoSelecionado;
	}
	public void setIdFluxoSelecionado(Integer idFluxoSelecionado) {
		this.idFluxoSelecionado = idFluxoSelecionado;
	}
	public String getNomeProjeto() {
		return nomeProjeto;
	}
	public void setNomeProjeto(String nomeProjeto) {
		this.nomeProjeto = nomeProjeto;
	}
	public Integer getIdTipoDemanda() {
		return idTipoDemanda;
	}
	public void setIdTipoDemanda(Integer idTipoDemanda) {
		this.idTipoDemanda = idTipoDemanda;
	}
	public String getQtdeHorasStr() {
		return UtilFormatacao.formatDouble(qtdeHoras,2);
	}	
	public Double getQtdeHoras() {
		return qtdeHoras;
	}
	public void setQtdeHoras(Double qtdeHoras) {
		this.qtdeHoras = qtdeHoras;
	}
	public Date getTerminoReal() {
		return terminoReal;
	}
	public void setTerminoReal(Date terminoReal) {
		this.terminoReal = terminoReal;
	}
	public String getObjSerializado() {
		return objSerializado;
	}
	public void setObjSerializado(String objSerializado) {
		this.objSerializado = objSerializado;
	}
	public String getNumeroOS() {
		return numeroOS;
	}
	public void setNumeroOS(String numeroOS) {
		this.numeroOS = numeroOS;
	}
	public Integer getAnoOS() {
		return anoOS;
	}
	public void setAnoOS(Integer anoOS) {
		this.anoOS = anoOS;
	}
}
