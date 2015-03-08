package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

@SuppressWarnings("serial")
public class PlanoMelhoriaDTO implements IDto {
	private Integer idPlanoMelhoria;
	private Integer idFornecedor;
	private Integer idContrato;
	private String titulo;
	private java.sql.Date dataInicio;
	private java.sql.Date dataFim;
	private String objetivo;
	private String visaoGeral;
	private String escopo;
	private String visao;
	private String missao;
	private String notas;
	private String criadoPor;
	private String modificadoPor;
	private java.sql.Date dataCriacao;
	private java.sql.Date ultModificacao;
	private String situacao;
	
	//Campos de Objetivos
	private Integer idPlanoMelhoriaAux1;
	private Integer idObjetivoPlanoMelhoria;
	private String tituloObjetivo;
	private String detalhamento;
	private String resultadoEsperado;
	private String medicao;
	private String responsavel;	
	
	//Campos de Acoes
	private Integer idAcaoPlanoMelhoria;
	private String tituloAcao;
	private String detalhamentoAcao;
	private java.sql.Date dataConclusao;
	
	//Campos referente ao relatorio 
	
	private Object listAcaoPlanoMelhoria ;
	
	//Campos de Monitoramento
	private Integer idObjetivoMonitoramento;
	private String tituloMonitoramento;
	private String fatorCriticoSucesso;
	private String kpi;
	private String metrica;
	private String relatorios;	
	
	
	

	public Integer getIdPlanoMelhoria(){
		return this.idPlanoMelhoria;
	}
	public void setIdPlanoMelhoria(Integer parm){
		this.idPlanoMelhoria = parm;
	}

	public Integer getIdFornecedor(){
		return this.idFornecedor;
	}
	public void setIdFornecedor(Integer parm){
		this.idFornecedor = parm;
	}

	public Integer getIdContrato(){
		return this.idContrato;
	}
	public void setIdContrato(Integer parm){
		this.idContrato = parm;
	}

	public String getTitulo(){
		return this.titulo;
	}
	public void setTitulo(String parm){
		this.titulo = parm;
	}

	public java.sql.Date getDataInicio(){
		return this.dataInicio;
	}
	public void setDataInicio(java.sql.Date parm){
		this.dataInicio = parm;
	}

	public java.sql.Date getDataFim(){
		return this.dataFim;
	}
	public void setDataFim(java.sql.Date parm){
		this.dataFim = parm;
	}

	public String getObjetivo(){
		return this.objetivo;
	}
	public void setObjetivo(String parm){
		this.objetivo = parm;
	}

	public String getVisaoGeral(){
		return this.visaoGeral;
	}
	public void setVisaoGeral(String parm){
		this.visaoGeral = parm;
	}

	public String getEscopo(){
		return this.escopo;
	}
	public void setEscopo(String parm){
		this.escopo = parm;
	}

	public String getVisao(){
		return this.visao;
	}
	public void setVisao(String parm){
		this.visao = parm;
	}

	public String getMissao(){
		return this.missao;
	}
	public void setMissao(String parm){
		this.missao = parm;
	}

	public String getNotas(){
		return this.notas;
	}
	public void setNotas(String parm){
		this.notas = parm;
	}

	public String getCriadoPor(){
		return this.criadoPor;
	}
	public void setCriadoPor(String parm){
		this.criadoPor = parm;
	}

	public String getModificadoPor(){
		return this.modificadoPor;
	}
	public void setModificadoPor(String parm){
		this.modificadoPor = parm;
	}

	public java.sql.Date getDataCriacao(){
		return this.dataCriacao;
	}
	public void setDataCriacao(java.sql.Date parm){
		this.dataCriacao = parm;
	}

	public java.sql.Date getUltModificacao(){
		return this.ultModificacao;
	}
	public void setUltModificacao(java.sql.Date parm){
		this.ultModificacao = parm;
	}
	public String getSituacao() {
		return situacao;
	}
	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
	public Integer getIdObjetivoPlanoMelhoria() {
		return idObjetivoPlanoMelhoria;
	}
	public void setIdObjetivoPlanoMelhoria(Integer idObjetivoPlanoMelhoria) {
		this.idObjetivoPlanoMelhoria = idObjetivoPlanoMelhoria;
	}
	public String getTituloObjetivo() {
		return tituloObjetivo;
	}
	public void setTituloObjetivo(String tituloObjetivo) {
		this.tituloObjetivo = tituloObjetivo;
	}
	public String getDetalhamento() {
		return detalhamento;
	}
	public void setDetalhamento(String detalhamento) {
		this.detalhamento = detalhamento;
	}
	public String getResultadoEsperado() {
		return resultadoEsperado;
	}
	public void setResultadoEsperado(String resultadoEsperado) {
		this.resultadoEsperado = resultadoEsperado;
	}
	public String getMedicao() {
		return medicao;
	}
	public void setMedicao(String medicao) {
		this.medicao = medicao;
	}
	public String getResponsavel() {
		return responsavel;
	}
	public void setResponsavel(String responsavel) {
		this.responsavel = responsavel;
	}
	public Integer getIdPlanoMelhoriaAux1() {
		return idPlanoMelhoriaAux1;
	}
	public void setIdPlanoMelhoriaAux1(Integer idPlanoMelhoriaAux1) {
		this.idPlanoMelhoriaAux1 = idPlanoMelhoriaAux1;
	}
	public Integer getIdAcaoPlanoMelhoria() {
		return idAcaoPlanoMelhoria;
	}
	public void setIdAcaoPlanoMelhoria(Integer idAcaoPlanoMelhoria) {
		this.idAcaoPlanoMelhoria = idAcaoPlanoMelhoria;
	}
	public String getTituloAcao() {
		return tituloAcao;
	}
	public void setTituloAcao(String tituloAcao) {
		this.tituloAcao = tituloAcao;
	}
	public String getDetalhamentoAcao() {
		return detalhamentoAcao;
	}
	public void setDetalhamentoAcao(String detalhamentoAcao) {
		this.detalhamentoAcao = detalhamentoAcao;
	}
	public java.sql.Date getDataConclusao() {
		return dataConclusao;
	}
	public void setDataConclusao(java.sql.Date dataConclusao) {
		this.dataConclusao = dataConclusao;
	}
	public Integer getIdObjetivoMonitoramento() {
		return idObjetivoMonitoramento;
	}
	public void setIdObjetivoMonitoramento(Integer idObjetivoMonitoramento) {
		this.idObjetivoMonitoramento = idObjetivoMonitoramento;
	}
	public String getTituloMonitoramento() {
		return tituloMonitoramento;
	}
	public void setTituloMonitoramento(String tituloMonitoramento) {
		this.tituloMonitoramento = tituloMonitoramento;
	}
	public String getFatorCriticoSucesso() {
		return fatorCriticoSucesso;
	}
	public void setFatorCriticoSucesso(String fatorCriticoSucesso) {
		this.fatorCriticoSucesso = fatorCriticoSucesso;
	}
	public String getKpi() {
		return kpi;
	}
	public void setKpi(String kpi) {
		this.kpi = kpi;
	}
	public String getMetrica() {
		return metrica;
	}
	public void setMetrica(String metrica) {
		this.metrica = metrica;
	}
	public String getRelatorios() {
		return relatorios;
	}
	public void setRelatorios(String relatorios) {
		this.relatorios = relatorios;
	}
	/**
	 * @return the listAcaoPlanoMelhoria
	 */
	public Object getListAcaoPlanoMelhoria() {
		return listAcaoPlanoMelhoria;
	}
	/**
	 * @param listAcaoPlanoMelhoria the listAcaoPlanoMelhoria to set
	 */
	public void setListAcaoPlanoMelhoria(Object listAcaoPlanoMelhoria) {
		this.listAcaoPlanoMelhoria = listAcaoPlanoMelhoria;
	}
	
}
