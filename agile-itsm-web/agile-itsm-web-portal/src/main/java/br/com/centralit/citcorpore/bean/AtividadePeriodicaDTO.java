package br.com.centralit.citcorpore.bean;

import java.util.Collection;

import br.com.citframework.dto.IDto;

public class AtividadePeriodicaDTO implements IDto {
	private Integer idAtividadePeriodica;
	private Integer idContrato;
	private Integer idProcedimentoTecnico;
	private Integer idGrupoAtvPeriodica;
	private String tituloAtividade;
	private String descricao;
	private String orientacaoTecnica;
	private String criadoPor;
	private String alteradoPor;
	private java.sql.Date dataCriacao;
	private java.sql.Date dataUltAlteracao;  
	
	private Integer idSolicitacaoServico;
	private java.sql.Date dataInicio;
	private java.sql.Date dataFim;
	private String horaInicio;
	private Integer duracaoEstimada;
	
	private Integer idRequisicaoMudanca;
	private String blackout;
	private String identMudanca;
	private String nomeTipoMudanca;
	
	private Collection colItens;
	private Collection colItensOS;
	
	private Integer idProblema;
	
	private Integer idRequisicaoLiberacao;
	

	public Integer getIdAtividadePeriodica(){
		return this.idAtividadePeriodica;
	}
	public void setIdAtividadePeriodica(Integer parm){
		this.idAtividadePeriodica = parm;
	}

	public Integer getIdContrato(){
		return this.idContrato;
	}
	public void setIdContrato(Integer parm){
		this.idContrato = parm;
	}

	public Integer getIdProcedimentoTecnico(){
		return this.idProcedimentoTecnico;
	}
	public void setIdProcedimentoTecnico(Integer parm){
		this.idProcedimentoTecnico = parm;
	}

	public Integer getIdGrupoAtvPeriodica(){
		return this.idGrupoAtvPeriodica;
	}
	public void setIdGrupoAtvPeriodica(Integer parm){
		this.idGrupoAtvPeriodica = parm;
	}

	public String getDescricao(){
		return this.descricao;
	}
	public void setDescricao(String parm){
		this.descricao = parm;
	}

	public String getOrientacaoTecnica(){
		return this.orientacaoTecnica;
	}
	public void setOrientacaoTecnica(String parm){
		this.orientacaoTecnica = parm;
	}

	public String getCriadoPor(){
		return this.criadoPor;
	}
	public void setCriadoPor(String parm){
		this.criadoPor = parm;
	}

	public String getAlteradoPor(){
		return this.alteradoPor;
	}
	public void setAlteradoPor(String parm){
		this.alteradoPor = parm;
	}

	public java.sql.Date getDataCriacao(){
		return this.dataCriacao;
	}
	public void setDataCriacao(java.sql.Date parm){
		this.dataCriacao = parm;
	}

	public java.sql.Date getDataUltAlteracao(){
		return this.dataUltAlteracao;
	}
	public void setDataUltAlteracao(java.sql.Date parm){
		this.dataUltAlteracao = parm;
	}
	public Collection getColItens() {
		return colItens;
	}
	public void setColItens(Collection colItens) {
		this.colItens = colItens;
	}
	public Collection getColItensOS() {
		return colItensOS;
	}
	public void setColItensOS(Collection colItensOS) {
		this.colItensOS = colItensOS;
	}
	public String getTituloAtividade() {
		return tituloAtividade;
	}
	public void setTituloAtividade(String tituloAtividade) {
		this.tituloAtividade = tituloAtividade;
	}
	public Integer getIdSolicitacaoServico() {
	    return idSolicitacaoServico;
	}
	public void setIdSolicitacaoServico(Integer idSolicitacaoServico) {
	    this.idSolicitacaoServico = idSolicitacaoServico;
	}
	public java.sql.Date getDataInicio() {
	    return dataInicio;
	}
	public void setDataInicio(java.sql.Date dataInicio) {
	    this.dataInicio = dataInicio;
	}
	public String getHoraInicio() {
	    return horaInicio;
	}
	public void setHoraInicio(String horaInicio) {
	    this.horaInicio = horaInicio;
	}
	public Integer getDuracaoEstimada() {
	    return duracaoEstimada;
	}
	public void setDuracaoEstimada(Integer duracaoEstimada) {
	    this.duracaoEstimada = duracaoEstimada;
	}
	public Integer getIdRequisicaoMudanca() {
		return idRequisicaoMudanca;
	}
	public void setIdRequisicaoMudanca(Integer idRequisicaoMudanca) {
		this.idRequisicaoMudanca = idRequisicaoMudanca;
	}
	public String getBlackout() {
		return blackout;
	}
	public void setBlackout(String blackout) {
		this.blackout = blackout;
	}
	public String getIdentMudanca() {
		return identMudanca;
	}
	public void setIdentMudanca(String identMudanca) {
		this.identMudanca = identMudanca;
	}
	public Integer getIdProblema() {
		return idProblema;
	}
	public void setIdProblema(Integer idProblema) {
		this.idProblema = idProblema;
	}
	public String getNomeTipoMudanca() {
		return nomeTipoMudanca;
	}
	public void setNomeTipoMudanca(String nomeTipoMudanca) {
		this.nomeTipoMudanca = nomeTipoMudanca;
	}
	
	/**
	 * @return the idRequisicaoLiberacao
	 */
	public Integer getIdRequisicaoLiberacao() {
		return idRequisicaoLiberacao;
	}
	/**
	 * @param idRequisicaoLiberacao the idRequisicaoLiberacao to set
	 */
	public void setIdRequisicaoLiberacao(Integer idRequisicaoLiberacao) {
		this.idRequisicaoLiberacao = idRequisicaoLiberacao;
	}	
	
	public java.sql.Date getDataFim() {
		return dataFim;
	}
	public void setDataFim(java.sql.Date dataFim) {
		this.dataFim = dataFim;
	}

}
