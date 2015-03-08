package br.com.centralit.citcorpore.bean;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Collection;

import br.com.citframework.dto.IDto;

public class DelegacaoCentroResultadoDTO implements IDto {
	public static final String NOVAS_EXISTENTES = "E";
	public static final String NOVAS = "N";
	public static final String ESPECIFICAS = "R";

	private Integer idDelegacaoCentroResultado;
	private Integer idResponsavel;
	private Integer idCentroResultado;
	private Integer idEmpregado;
	private Integer idResponsavelRegistro;
	private Integer idResponsavelRevogacao;
	private Timestamp dataHoraRegistro;
	private Date dataInicio;
	private Date dataFim;
	private String abrangencia;
	private String revogada;
	private Timestamp dataHoraRevogacao;
	
	private String nomeResponsavel;
	private String nomeEmpregado;
	private String requisiçoes;
	
	private Integer[] idProcessoNegocio;
	private Collection<ExecucaoSolicitacaoDTO> colInstancias;

	public Integer getIdDelegacaoCentroResultado(){
		return this.idDelegacaoCentroResultado;
	}
	public void setIdDelegacaoCentroResultado(Integer parm){
		this.idDelegacaoCentroResultado = parm;
	}

	public Integer getIdResponsavel(){
		return this.idResponsavel;
	}
	public void setIdResponsavel(Integer parm){
		this.idResponsavel = parm;
	}

	public Integer getIdCentroResultado(){
		return this.idCentroResultado;
	}
	public void setIdCentroResultado(Integer parm){
		this.idCentroResultado = parm;
	}

	public Integer getIdEmpregado(){
		return this.idEmpregado;
	}
	public void setIdEmpregado(Integer parm){
		this.idEmpregado = parm;
	}

	public Date getDataInicio(){
		return this.dataInicio;
	}
	public void setDataInicio(Date parm){
		this.dataInicio = parm;
	}

	public Date getDataFim(){
		return this.dataFim;
	}
	public void setDataFim(Date parm){
		this.dataFim = parm;
	}

	public String getAbrangencia(){
		return this.abrangencia;
	}
	public void setAbrangencia(String parm){
		this.abrangencia = parm;
	}
	public String getRevogada() {
		return revogada;
	}
	public void setRevogada(String revogada) {
		this.revogada = revogada;
	}
	public String getNomeEmpregado() {
		return nomeEmpregado;
	}
	public void setNomeEmpregado(String nomeEmpregado) {
		this.nomeEmpregado = nomeEmpregado;
	}
	public String getRequisiçoes() {
		return requisiçoes;
	}
	public void setRequisiçoes(String requisiçoes) {
		this.requisiçoes = requisiçoes;
	}
	public Integer[] getIdProcessoNegocio() {
		return idProcessoNegocio;
	}
	public void setIdProcessoNegocio(Integer[] idProcessoNegocio) {
		this.idProcessoNegocio = idProcessoNegocio;
	}
	public Collection<ExecucaoSolicitacaoDTO> getColInstancias() {
		return colInstancias;
	}
	public void setColInstancias(Collection<ExecucaoSolicitacaoDTO> colInstancias) {
		this.colInstancias = colInstancias;
	}
	public Integer getIdResponsavelRegistro() {
		return idResponsavelRegistro;
	}
	public void setIdResponsavelRegistro(Integer idResponsavelRegistro) {
		this.idResponsavelRegistro = idResponsavelRegistro;
	}
	public Integer getIdResponsavelRevogacao() {
		return idResponsavelRevogacao;
	}
	public void setIdResponsavelRevogacao(Integer idResponsavelRevogacao) {
		this.idResponsavelRevogacao = idResponsavelRevogacao;
	}
	public Timestamp getDataHoraRegistro() {
		return dataHoraRegistro;
	}
	public void setDataHoraRegistro(Timestamp dataHoraRegistro) {
		this.dataHoraRegistro = dataHoraRegistro;
	}
	public Timestamp getDataHoraRevogacao() {
		return dataHoraRevogacao;
	}
	public void setDataHoraRevogacao(Timestamp dataHoraRevogacao) {
		this.dataHoraRevogacao = dataHoraRevogacao;
	}
	public String getNomeResponsavel() {
		return nomeResponsavel;
	}
	public void setNomeResponsavel(String nomeResponsavel) {
		this.nomeResponsavel = nomeResponsavel;
	}

}
